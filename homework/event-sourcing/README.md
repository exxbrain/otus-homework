# Event sourcing

Работа выполнена с использованием Axon Framework https://axoniq.io.
Хранилищем событий является база данных postgres. Используется та же, что и для хранения представлений.

Ниже представлена схема работы

![Event Sourcing](event-sourcing.png)

```shell
helm repo add exxbrain https://exxbrain.github.io/helm-charts
helm repo update
kubectl create namespace dzakharov
helm install app exxbrain/event-sourcing -n=dzakharov
newman run event-sourcing.postman_collection.json
```