# Cake Manager (Waracle Interview Exercise)

## Prerequisites
1. Java 11
2. Gradle
3. Docker (required only when deploying to docker)


## Build and Run

```sh
./gradlew clean bootrun
```

or to run using docker-compose

```sh
./gradlew clean bootjar
docker-compose build
docker-compose up
```

## Accessing the Application
### Single Sign On With GitHub

The application can be accessed using the following URL,

``http://localhost:8282/``

The browser will automatically redirect to Github sign-in page. 

All endpoints are protected. There are altogether two rest endpoints and a web page.

#### Endpoint
1. GET http://localhost:8282/cakes - This will return the list of cakes
2. POST http://localhost:8282/cake - save a new cake

#### Web page
1. GET http://localhost:8282/ or http://localhost:8282/index.html  -  It consists of a simple table with the cake data, 
   and a form to add new cakes.

The webpage uses the same rest endpoint to communicate to the backend.

## Initial Data Load
The application will pull the initial data from the following URL during the startup,
``https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c\cake.json``

This URL is manned by the following application properties,
```yaml
cake:
  load:
    server: https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c
    context-path: /cake.json
```

