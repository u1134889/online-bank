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

resource "kubernetes_service" "registry" {
    metadata {
        name = "registry"
        namespace = "kube-system"
    }
    spec {
        type = "LoadBalancer"
        port {
            port = 54118
            target_port = 5000
        }
        selector = {
            app = "registry"
        }
    }
}

resource "kubernetes_deployment" "registry" {
    metadata {
        name = "registry"
        namespace = "kube-system"
    }
    spec {
        replicas = 1
        selector {
            match_labels = {
                app = "registry"
            }
        }
        template {
            metadata {
                labels = {
                    app = "registry"
                }
            }
            spec {
                container {
                    name = "registry"
                    image = "registry:2"

                    port {
                        container_port = 5000
                    }
                }
            }
        }
    }
}

# locals {
#     app_name = "online-bank"
# }

# resource "kubernetes_namespace" "online_bank" {
#     metadata {
#         name = "${local.app_name}-namespace"
#     }
# }

# resource "kubernetes_deployment" "online_bank" {
    
# }
