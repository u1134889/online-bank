terraform {
    required_providers { 
        kubernetes = {
            source = "hashicorp/kubernetes"
            version = "2.34.0"
        }
    }
}

provider "kubernetes" {
    config_path = "~/.kube/config"
}

locals {
    app_name = "online-bank"
    parent_pom_path = abspath("${path.module}/../../../../pom.xml")
    backend_version = data.external.backend_version.result.backend_version
}

data "external" "backend_version" {
    program = ["pwsh", "-c", "$(mvn help:evaluate '-Dexpression=project.version' -q -DforceStdout -f=${local.parent_pom_path}) | % { [PSCustomObject]@{backend_version = $_} } | ConvertTo-Json -Compress"]
}

resource "kubernetes_namespace" "online_bank" {
    metadata {
        name = "${local.app_name}-namespace"
    }
}

resource "kubernetes_deployment" "online_bank" {
    metadata {
        name = "${local.app_name}-deployment"
        namespace = kubernetes_namespace.online_bank.metadata[0].name
    }
    spec {
        replicas = 1
        selector {
            match_labels = {
                app = local.app_name
            }
        }
        template {
            metadata {
                labels = {
                    app = local.app_name
                }
            }
            spec {
                container {
                    name = "${local.app_name}-container"
                    image = "localhost:5000/rogosienski/online-bank-backend:${local.backend_version}"
                    port {
                        container_port = 8080
                    }
                }
            }
        }
    }
}

resource "kubernetes_service" "online_bank" {
    metadata {
        name = "${local.app_name}-deployment"
        namespace = kubernetes_namespace.online_bank.metadata[0].name
    }
    spec {
        selector = {
            app = local.app_name
        }
        port {
            port = 8081
            target_port = 8080
        }
        type = "NodePort"
    }
}
