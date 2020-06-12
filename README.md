# rsocket-rpc-spring-boot-sample
sample spring boot project for rpc communication


#How to run apps
##With Idea
1. Build
```
gradle clean build
```
2. Start the docker compose for other services (zipkin,prometheus,grafana)
```docker
docker-compose -f <docker-compose-external-yml-in-repo> up -d
``` 
3. start server ( right click main class and run)
4. start client (right click client main class and run)
5. test it with curl
```
curl localhost:8080/sample/hello
```

##With docker build

1. Build and generate docker images

```groovy
 GPR_API_KEY=$GIT_REPO_TOKEN gradle clean build jibDockerBuild
```

2. run docker-compose all in one
```docker
docker-compose -f <docker-compose-yml-in-repo> up -d
```

#Monitoring apps
* Tracing Server (Zipkin) - http://localhost:9411/zipkin/
* Grafana Dashboards - http://localhost:3000
* Prometheus - http://localhost:9091 


