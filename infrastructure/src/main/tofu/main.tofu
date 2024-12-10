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
}

variable "backend_version" {
    description = "The tag of the backend docker image to pull"
    type = string
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
                    image = "localhost:5000/rogosienski/online-bank-backend:${var.backend_version}"
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
