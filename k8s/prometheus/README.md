## Prometheus

https://github.com/helm/charts/tree/master/stable/prometheus-operator

user/password: `admin/admin`

```shell script
helm repo add stable https://kubernetes-charts.storage.googleapis.com
helm repo update

helm install -n monitoring -f k8s/prometheus/operator-values.yaml prometheus \
prometheus-community/kube-prometheus-stack

helm uninstall prometheus

kubectl apply -f k8s/prometheus/monitoring-nodeport.yaml
```

```shell script
kubectl get servicemonitors.monitoring.coreos.com
kubectl port-forward service/prom-prometheus 9090:9090
http://localhost:9090/service-discovery
```

```shell script
while true; do ab -n 50 -c 3 http://192.168.64.2:/users; sleep 3; done 
while true; do ab -n 50 -c 3 http://arch.homework/otusapp/dzakharov/users; sleep 3; done 
```

```shell script
helm install nginx stable/nginx-ingress -f prometheus/nginx-ingress.yaml --atomic
```

RPS:
```
sum(rate(http_server_requests_seconds_count{uri="/users",status=~"2.+"}[2m]))
sum by (uri, method, status) (rate(http_server_requests_seconds_count[2m]))
histogram_quantile(0.95, sum by(le) (rate(http_server_requests_seconds_bucket[2m])))
```