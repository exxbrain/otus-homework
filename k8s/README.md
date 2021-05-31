# Otus Homework

```shell
minikube config set driver parallels
minikube config set cpus 6
minikube config set memory 8192
minikube config set disk-size 40000MB

kubectl apply -f k8s/namespaces.yaml

minikube start --cni=flannel
eval $(minikube docker-env)
```

* jaeger/README.md
* prometheus/README.md
* istio/README.md
* kiali/README.md
* postgres/README.md
