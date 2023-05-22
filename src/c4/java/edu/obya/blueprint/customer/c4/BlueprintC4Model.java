package edu.obya.blueprint.customer.c4;

import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.StructurizrAnnotationsComponentFinderStrategy;
import com.structurizr.api.StructurizrClient;
import com.structurizr.api.StructurizrClientException;
import com.structurizr.dsl.StructurizrDslParser;
import com.structurizr.util.WorkspaceUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;

@Slf4j
public class BlueprintC4Model {
    public static void main(String... args) {
        if (args.length >= 1) {
            try {
                val workspace = getWorkspaceFromDslFile(args[0]);

                // find predefined blueprint api container
                val system = workspace.getModel().getSoftwareSystemWithName("blueprint-system");
                assert(system != null);
                log.info("System found.");

                val application = system.getContainerWithName("blueprint-api");
                assert(application != null);
                log.info("Blueprint API C4 container found.");

                // C4 annotated component scanning
                new ComponentFinder(
                        application,
                        "edu.obya.blueprint",
                        new StructurizrAnnotationsComponentFinderStrategy()
                ).findComponents();

                // Create C4 component view
                val componentView = workspace.getViews().createComponentView(
                        application,
                        "blueprint-api-components",
                        "The components inside the Blueprint API container"
                );
                componentView.setExternalSoftwareSystemBoundariesVisible(true);
                componentView.addAllElements();

                WorkspaceUtils.printWorkspaceAsJson(workspace);

                if (args.length > 1) {
                    uploadWorkspace(workspace, Long.valueOf(args[1]));
                }

            } catch (Exception e) {
                log.warn("A problem occurred, the cause is {}", e.getMessage());
            }
        } else throw new IllegalArgumentException("Some arguments are missing, expected ones are: <dslPath> <workspaceId> <apiKey> <apiSecret>");
    }

    private static Workspace getWorkspaceFromDslFile(String dslPath) throws Exception {
        val parser = new StructurizrDslParser();
        parser.parse(new File(dslPath));
        val workspace = parser.getWorkspace();
        log.info("C4 model has been successfully loaded.");
        return workspace;
    }

    private static void uploadWorkspace(Workspace workspace, Long workspaceId) {
        try {
            new StructurizrClient().putWorkspace(workspaceId, workspace);
        } catch (StructurizrClientException e) {
            log.warn("A problem occurred, the cause is {}", e.getMessage());
        }
    }
}
