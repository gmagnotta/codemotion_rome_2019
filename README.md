Codemotion Rome 2019 Helm demo
==============================

What we will do:

* Build backend and frontend Java applications

* Test them with Docker on developer machine

* Run those images in a Kubernetes cluster with yaml files

* Present Helm and run the same Docker images with Helm packages

Repository layout
------------------

* helm: helm artifacts
* java: java applications
* yamls: yamls files for Kubernetes

Build Java applications
-----------------------

Build java applications with maven

```
cd java
for i in backend backend_2 frontend; do (cd $i; mvn clean package); done
```

Create Docker image for backend
-------------------------------
```
cd java/backend
docker build -t backend:1.0.0 .
```


Run backend in Docker and extract its ip address
------------------------------------------------

```
BACKEND=`docker run -d backend:1.0.0`

docker inspect $BACKEND | grep -i IPAddress
```

Create Docker image for frontend
--------------------------------

```
cd java/frontend
docker build -t frontend:1.0.0 .
```

Run frontend docker image and link it to backend
------------------------------------------------

```
FRONTEND=`docker run -d -e BACKEND="http://<backendip>:8080/" frontend:1.0.0`

docker inspect $FRONTEND | grep -i IPAddress
````

Create updated Docker image for backend
-------------------------------
```
cd java/backend_2
docker build -t backend:1.0.1 .
```

Push docker images to a local registry
--------------------------------------

```
docker tag backend:1.0.0 registry.lan/backend:1.0.0
docker push registry.lan/backend:1.0.0

docker tag frontend:1.0.0 registry.lan/frontend:1.0.0
docker push registry.lan/frontend:1.0.0

docker tag backend:1.0.1 registry.lan/backend:1.0.1
docker push registry.lan/backend:1.0.1
```

Deploy backend in Kubernetes
----------------------------

```
cd yamls/backend
kubectl apply -f backend_deployment.yaml
kubectl apply -f backend_service.yaml
```

Deploy frontend in Kubernetes
-----------------------------

```
cd yamls/fronted
kubectl apply -f frontend_configmap.yaml
kubectl apply -f frontend_deployment.yaml
kubectl apply -f frontend_service.yaml
```

Deploy updated backend in Kubernetes
------------------------------------

```
cd yamls/backend_2
kubectl apply -f backend_deployment.yaml
```


Build backend Helm chart
------------------------

```
cd helm
helm package backend
```

Push Helm chart to chartmuseum
------------------------------

Depends on your environment


Install Helm chart
------------------

```
helm install chartmuseum/backend
```

Build frontend Helm chart
-------------------------

```
cd helm
helm package frontend
```

Push Helm chart to chartmuseum
------------------------------

Depends on your environment


Install Helm chart
------------------

```
helm install --set config.backend=http://<backendservice:80>/ chartmuseum/frontend
```

Build updated backend Helm chart
------------------------

```
cd helm
helm package backend
```

Push Helm chart to chartmuseum
------------------------------

Depends on your environment


Install Helm chart
------------------

```
helm upgrade <releasename> chartmuseum/backend
```
