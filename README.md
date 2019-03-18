Codemotion Rome 2019 Helm demo
==============================

What we will do:

* Build backend and frontend Java applications

* Test them with Docker on developer machine

* Run those images in a Kubernetes cluster with yaml file

* Present Helm and run the same Docker images with Helm package

Repository content
------------------

helm: helm artifacts
java: java applications
yamls: yamls files for Kubernetes

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
docker build -t frontend:1.0.0 .
```

Run frontend docker image and link it to backend
------------------------------------------------

```
FRONTEND=`docker run -d -e BACKEND="http://<backendip>:8080/" frontend:1.0.0`

docker inspect $FRONTEND | grep -i IPAddress
````

Push docker images to a local registry
--------------------------------------

```
docker tag backend:1.0.0 registry.lan/backend:1.0.0
docker push registry.lan/backend:1.0.0

docker tag frontend:1.0.0 registry.lan/frontend:1.0.0
docker push registry.lan/frontend:1.0.0
```

Deploy backend in Kubernetes
----------------------------

```
kubectl apply -f backend_deployment.yaml
kubectl apply -f backend_service.yaml
```

Deploy frontend in Kubernetes
-----------------------------

```
kubectl apply -f frontend_configmap.yaml
kubectl apply -f frontend_deployment.yaml
kubectl apply -f frontend_service.yaml
```


