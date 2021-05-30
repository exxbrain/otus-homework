```shell
helm install postgres bitnami/postgresql -f k8s/postgres/values.yaml -n postgres
helm show values prometheus-community/prometheus-postgres-exporter
helm install prometheus-exporter -f k8s/postgres/exporter-values.yaml prometheus-community/prometheus-postgres-exporter -n postgres
kubectl port-forward service/prometheus-exporter-prometheus-postgres-exporter 8080:80
helm upgrade postgres bitnami/postgresql -f k8s/postgres/values.yaml -n postgres

helm uninstall postgres -n postgres
helm uninstall prometheus-exporter -n postgres
```

```shell
kubectl port-forward --namespace postgres svc/postgres 5435:5432 &
PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres -d postgres -p 5432
```