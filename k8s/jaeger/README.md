```shell
helm install -n jaeger-operator -f k8s/jaeger/values.yaml \
jaeger-operator jaegertracing/jaeger-operator

kubectl apply -f k8s/jaeger/jaeger.yaml
```
