```shell
istioctl operator init --watchedNamespaces istio-system --operatorNamespace istio-operator
kubectl apply -f k8s/istio/istio.yaml
kubectl apply -f k8s/istio/disable-mtls.yaml
```

```shell
kubectl logs PODNAME -c istio-proxy -n postgres
```