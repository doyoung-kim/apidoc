# apidoc

이 프로젝트는 MicroService Architecture 상의 수많은 api document문서들을 aggregation project하여 보여주는 프로젝트 입니다.
service mesh등의 별도 도구를 사용하지 않고 단순히 Spring cloud Gateway와 Springdoc api를 이용하여 구현되어 있습니다.

설정은 다음과 같이 수행합니다. service name과 context path의 name이 정확히 맞아야 api가 연결됩니다. 

```yaml
# application-local.yml
(...)
services:
  target:
  - name: sample-api
    contextPath: sample-api
  - name: sample-api2
    contextPath: sample-api2
(...)
spring:
  application:
    name: apidoc
  cloud:
    gateway:   
      routes:
      - id: sample-api
        uri: http://localhost:8088
        predicates:
          - Path=/sample-api/**
        filters:
          - RewritePath=/sample-api(?<segment>/?.*), /$\{segment}
      - id: sample-api2
        uri: http://localhost:9088
        predicates:
          - Path=/sample-api2/**
        filters:
          - RewritePath=/sample-api2(?<segment>/?.*), /$\{segment}
(...)
```


spring cloud gateway를 이용한 방식이며, 뒷단의 api document 메인클래스에서는 다음과 같이 cors를 허용해 주어야 함

```java
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://front-server.com");
            }
        };
    }
```

# Test

테스트를 위해 sample-api, sample-api2 프로젝트가 필요합니다. sample-api2는 테스트를 위해 sample-api에서 단순히 숫자2만 추가된 프로젝트이며 다음 위치에서 받을 수 있습니다.

[sample-api2.tar.gz](https://github.com/oscka/apidoc/files/9809245/sample-api2.tar.gz)

sample-api (8088), sample-api2(9088), apidoc(8085)에서 각각 구동됩니다.

다음 주소로 접속하여 우측 상단의 select box를 통해 각 서비스의 API를 한 화면에서 접근하고 테스트 할 수 있습니다.

[Swagger UI](http://localhost:8085/webjars/swagger-ui/index.html)
