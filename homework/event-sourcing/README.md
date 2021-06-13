```shell
helm repo add exxbrain https://exxbrain.github.io/helm-charts
helm repo update
kubectl create namespace dzakharov
helm install app exxbrain/event-sourcing -n=dzakharov
newman run event-sourcing.postman_collection.json
```