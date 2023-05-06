package edu.obya.blueprint.customer;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

public class CustomerArchitectureTest {

    final String ROOT_PACKAGE = "edu.obya.blueprint";
    final String ALL_FEATURE_PACKAGES = ROOT_PACKAGE + "..";
    final String CUSTOMER_FEATURE_PACKAGE = ROOT_PACKAGE + ".customer";

    /**
     * https://www.archunit.org/userguide/html/000_Index.html#_slices
     */
    @Test
    public void features_are_isolated_from_each_other() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(ALL_FEATURE_PACKAGES);
        SlicesRuleDefinition.slices().assignedFrom(new SliceAssignment() {
            @Override
            public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
                if (javaClass.getName().contains(".customer.")) {
                    return SliceIdentifier.of("customer");
                }
                return SliceIdentifier.ignore();
            }
            @Override
            public String getDescription() {
                return "only feature packages";
            }
        }).should().notDependOnEachOther().check(importedClasses);
    }

    /**
     * https://www.archunit.org/userguide/html/000_Index.html#_onion_architecture
     */
    @Test
    public void dependencies_are_oriented_to_the_center() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(ALL_FEATURE_PACKAGES);
        onionArchitecture()
                .domainModels(CUSTOMER_FEATURE_PACKAGE + ".domain.model..")
                .domainServices(CUSTOMER_FEATURE_PACKAGE + ".domain.service..")
                .applicationServices(
                        CUSTOMER_FEATURE_PACKAGE + ".application..",
                        CUSTOMER_FEATURE_PACKAGE + ".config..",
                        ROOT_PACKAGE + ".config.."
                )
                .adapter("rest", CUSTOMER_FEATURE_PACKAGE + ".adapter.rest..")
                .adapter("jpa", CUSTOMER_FEATURE_PACKAGE + ".adapter.jpa..")
                .check(importedClasses);
    }

    @Test
    public void output_ports_are_defined_as_interfaces() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(ALL_FEATURE_PACKAGES);
        classes()
                .that().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces()
                .check(importedClasses);
        classes()
                .that().haveSimpleNameEndingWith("Client")
                .should().beInterfaces()
                .allowEmptyShould(true)
                .check(importedClasses);
    }

    /**
     * https://www.archunit.org/userguide/html/000_Index.html#_composing_class_rules
     */
    @Test
    public void repository_adapters_are_named_and_located_correctly() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(ALL_FEATURE_PACKAGES);
        importedClasses
                .stream()
                .filter(clazz -> clazz.getName().endsWith("Repository"))
                .collect(Collectors.toSet())
                .forEach(clazz ->
                    classes()
                            .that().implement(clazz.getName())
                            .should().haveSimpleNameEndingWith("Adapter")
                            .andShould().resideInAnyPackage(CUSTOMER_FEATURE_PACKAGE + ".adapter.jpa..")
                            .allowEmptyShould(true)
                            .check(importedClasses)
                );
    }

    /**
     * https://www.archunit.org/userguide/html/000_Index.html#_composing_class_rules
     */
    @Test
    public void client_adapters_are_named_and_located_correctly() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(ALL_FEATURE_PACKAGES);
        importedClasses
                .stream()
                .filter(clazz -> clazz.getName().endsWith("Client"))
                .collect(Collectors.toSet())
                .forEach(clazz ->
                        classes()
                                .that().implement(clazz.getName())
                                .should().haveSimpleNameEndingWith("Adapter")
                                .andShould().resideInAnyPackage(CUSTOMER_FEATURE_PACKAGE + ".adapter.client..")
                                .allowEmptyShould(true)
                                .check(importedClasses)
                );
    }
}
