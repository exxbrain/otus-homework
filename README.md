```shell
http https://start.spring.io/starter.zip javaVersion==11 packageName==com.exxbrain.discovery-service \
    artifactId==discovery-service name==eureka-service baseDir==discovery-service bootVersion==2.5.0 \
    type==gradle-project groupId==com.exxbrain \
    dependencies==cloud-eureka-server | tar -xzvf -
    
http https://start.spring.io/starter.zip javaVersion==11 artifactId==api-gateway \
  packageName==com.exxbrain.api-gateway \
  name==api-gateway baseDir==api-gateway bootVersion==2.3.11.RELEASE \
  type==gradle-project groupId==com.exxbrain \
  dependencies==actuator,cloud-eureka,cloud-feign,cloud-gateway,webflux,lombok,prometheus,cloud-hystrix | tar -xzvf -
```