workspace "ARCH-workspace" "C4 model for Clean architecture education" {
    model {
        impliedRelationships true

        enterprise "Clean Architecture AG" {

            # internal systems
            archSystem = softwareSystem "ARCH system" "" {

                # containers
                blueprintApi = container "Blueprint API" "Provides client management endpoints" "Java/SpringBoot" "api,microservice"
                service = container "Service" "Consumes Blueprint API"

                # infrastructure containers
                databaseServer = container "database-server" "Stores customer information" "PostgreSQL" "infrastructure,database" {
                    blueprintDatabase = component "database-server:blueprint" "Stores clients, organizations and users" "PostgreSQL" "infrastructure,schema"
                }
           }
            # internal persons
        }

        # external systems
        # relationships
    }

    # views
    views {
        systemContext archSystem "arch-context" "The context of the ARCH-system" {
            include *
        }
        container archSystem "arch-containers" "The containers inside ARCH-system" {
            include *
        }

        !include styles.dsl
    }
}
