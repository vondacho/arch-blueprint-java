workspace "Obya" "A set of system architectures represented with the C4 model" {

    model {
        impliedRelationships true

        enterprise "Obya" {

            # internal systems
            system = softwareSystem "blueprint-system" "A small system with one service and one database" "blueprint" {

                # containers
                api = container "blueprint-api" "Provides customer management endpoint" "Java/SpringBoot" "api,microservice"

                # infrastructure containers
                dbServer = container "database-server" "Manages database instances" "PostgreSQL" "infrastructure,database" {
                    dbInstance = component "database" "Stores customer data" "PostgreSQL" "infrastructure,schema"
                }
           }
            # internal persons
            user = person "User" "API client"
        }

        # external systems
        # relationships
    }

    # views
    views {
        systemContext system "blueprint-context" "The context of the blueprint system" {
            include *
        }
        container system "blueprint-containers" "The containers inside the blueprint system" {
            include *
        }

        !include styles.dsl
    }
}
