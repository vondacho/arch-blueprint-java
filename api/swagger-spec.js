window.swaggerSpec={
  "openapi" : "3.0.3",
  "info" : {
    "title" : "Blueprint API",
    "description" : "This API allows managing Customer entities.\n",
    "contact" : {
      "name" : "API Support"
    },
    "version" : "1.0.0"
  },
  "servers" : [ {
    "url" : "http://localhost:8080"
  } ],
  "tags" : [ {
    "name" : "customer",
    "description" : "Management of Customer entity store",
    "externalDocs" : {
      "description" : "Pretty API documentation",
      "url" : "https://vondacho.github.io/arch-blueprint-java/api/"
    }
  } ],
  "paths" : {
    "/customers" : {
      "get" : {
        "tags" : [ "customer" ],
        "summary" : "List customers by criteria",
        "description" : "List existing customers given a set of matching filters",
        "operationId" : "listCustomers",
        "parameters" : [ {
          "name" : "limit",
          "in" : "query",
          "description" : "how many items to return at one time (max 100)",
          "required" : false,
          "schema" : {
            "type" : "integer",
            "format" : "int32"
          }
        }, {
          "name" : "filter",
          "in" : "query",
          "description" : "term used as matching criteria for the name attribute",
          "required" : false,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "term",
          "in" : "query",
          "description" : "term used as matching criteria for any attribute",
          "required" : false,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/CustomerOutput"
                  }
                }
              }
            }
          },
          "401" : {
            "$ref" : "#/components/responses/UnauthorizedError"
          },
          "default" : {
            "description" : "Other problem",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          }
        },
        "security" : [ {
          "basicAuth" : [ ]
        } ]
      },
      "post" : {
        "tags" : [ "customer" ],
        "summary" : "Add a new customer to the store",
        "description" : "Add a new customer to the store, a valid response contains a generated unique ID and the creation date\n",
        "operationId" : "addCustomer",
        "requestBody" : {
          "required" : true,
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/CustomerInput"
              }
            }
          }
        },
        "responses" : {
          "201" : {
            "description" : "successful operation",
            "content" : {
              "text/plain" : {
                "schema" : {
                  "type" : "string",
                  "format" : "uuid"
                }
              }
            }
          },
          "400" : {
            "description" : "Bad request",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "401" : {
            "$ref" : "#/components/responses/UnauthorizedError"
          },
          "default" : {
            "description" : "Other problem",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          }
        },
        "security" : [ {
          "basicAuth" : [ ]
        } ]
      }
    },
    "/customers/{id}" : {
      "get" : {
        "tags" : [ "customer" ],
        "summary" : "Find customer by ID",
        "description" : "Returns a single customer",
        "operationId" : "getCustomerById",
        "parameters" : [ {
          "name" : "id",
          "in" : "path",
          "description" : "ID of customer to return",
          "required" : true,
          "schema" : {
            "type" : "string",
            "format" : "uuid"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "successful operation",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/CustomerOutput"
                }
              }
            }
          },
          "400" : {
            "description" : "Bad request",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "404" : {
            "description" : "Customer not found",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "401" : {
            "$ref" : "#/components/responses/UnauthorizedError"
          },
          "default" : {
            "description" : "Other problem",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          }
        },
        "security" : [ {
          "basicAuth" : [ ]
        } ]
      },
      "put" : {
        "tags" : [ "customer" ],
        "summary" : "Update an existing customer",
        "description" : "Modify a single customer",
        "operationId" : "updateCustomer",
        "parameters" : [ {
          "name" : "id",
          "in" : "path",
          "description" : "Customer id to update",
          "required" : true,
          "schema" : {
            "type" : "string",
            "format" : "uuid"
          }
        } ],
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/CustomerInput"
              }
            }
          },
          "required" : true
        },
        "responses" : {
          "204" : {
            "description" : "successful operation"
          },
          "400" : {
            "description" : "Bad request",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "404" : {
            "description" : "Customer not found",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "401" : {
            "$ref" : "#/components/responses/UnauthorizedError"
          },
          "default" : {
            "description" : "Other problem",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          }
        },
        "security" : [ {
          "basicAuth" : [ ]
        } ]
      },
      "delete" : {
        "tags" : [ "customer" ],
        "summary" : "Deletes a customer",
        "description" : "Delete a single customer",
        "operationId" : "deleteCustomer",
        "parameters" : [ {
          "name" : "id",
          "in" : "path",
          "description" : "Customer id to delete",
          "required" : true,
          "schema" : {
            "type" : "string",
            "format" : "uuid"
          }
        } ],
        "responses" : {
          "204" : {
            "description" : "successful operation"
          },
          "400" : {
            "description" : "Customer cannot be deleted",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "404" : {
            "description" : "Customer not found",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          },
          "401" : {
            "$ref" : "#/components/responses/UnauthorizedError"
          },
          "default" : {
            "description" : "Other problem",
            "content" : {
              "application/problem+json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ProblemOutput"
                }
              }
            }
          }
        },
        "security" : [ {
          "basicAuth" : [ ]
        } ]
      }
    }
  },
  "components" : {
    "schemas" : {
      "SearchOutput" : {
        "type" : "object",
        "properties" : {
          "customers" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/CustomerOutput"
            }
          }
        }
      },
      "CustomerInput" : {
        "type" : "object",
        "properties" : {
          "firstName" : {
            "type" : "string"
          },
          "lastName" : {
            "type" : "string"
          }
        },
        "required" : [ "firstName", "lastName" ]
      },
      "CustomerOutput" : {
        "type" : "object",
        "properties" : {
          "id" : {
            "type" : "string",
            "format" : "uuid"
          },
          "firstName" : {
            "type" : "string"
          },
          "lastName" : {
            "type" : "string"
          },
          "fullName" : {
            "type" : "string"
          }
        },
        "required" : [ "id", "firstName", "lastName", "fullName" ]
      },
      "ProblemOutput" : {
        "type" : "object",
        "properties" : {
          "title" : {
            "type" : "string",
            "description" : "The error title.",
            "example" : "Bad Request"
          },
          "status" : {
            "type" : "integer",
            "description" : "The HTTP status.",
            "format" : "int32",
            "example" : 400
          },
          "detail" : {
            "type" : "string",
            "description" : "Detailed information about the invalid request.",
            "example" : "'lastName' attribute is expected."
          }
        },
        "required" : [ "title", "status", "detail" ]
      }
    },
    "responses" : {
      "UnauthorizedError" : {
        "description" : "Authentication information is missing or invalid",
        "headers" : {
          "WWW_Authenticate" : {
            "schema" : {
              "type" : "string"
            }
          }
        }
      }
    },
    "securitySchemes" : {
      "basicAuth" : {
        "type" : "http",
        "scheme" : "basic"
      }
    }
  },
  "security" : [ {
    "basicAuth" : [ ]
  } ]
}