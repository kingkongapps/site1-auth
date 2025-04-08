## Springboot + Keycloak 연동
### Springboot 설정(1)
1. 개요
* Springboot 기본 프로젝트에 아래 oauth2 dependency 를 추가하면
* 인증 통제가 Keycloak 으로 넘어간다.
2. springboot dependency 추가
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```
3. Spring Security Config 설정
* dependency 추가로 전체가 통제 되지만
* Spring Security 에서 아래와 같이 
  * 정적 파일이나
  * Root(/), Home 및 Index 파일에 대해서는 허용하고
  * 기타 모든 요청에 대해서는 인증을 요구하도록 한다.
```java
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index.html").permitAll()
                        .requestMatchers("/img/**", "/js/**", "/css/**", "/fonts/**").permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 로그인 처리 (Keycloak 로그인 페이지로 리다이렉션)
                .oauth2Login(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2.successHandler(successHandler()))
                // 리소스 서버로서 JWT 토큰 검증
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(keycloakLogoutHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
```

---

### Keycloak 설정
1. 개요 (용어 정리)
> Realm : 영역, 도메인 - SSO를 구현할 가상의 영역
> 
> Client : Application 또는 Web Site를 말하며, 같은 Realm(영역)에 속해있는 Application들은 SSO가 동작한다.
> * 여러 Appl중에 한곳에서만 로그인을 해도 다른 Site에서는 로그인 없이 사용가능
> * 여러 Appl중에 한곳에서만 로그아웃을 해도 모두 로그아웃이 수행 됨

2.Keycloak 설정
* admin 으로 로그인 한 후,
* 새로운 Realm 생성 (Create Realm)
* 생성한 Realm 선택
* Realm(영역) 안에 Client 등록
```shell
  - ClientID : SSO_TEST
  - Name 등록 : SSO_TEST
  - Home URL : http://localhost:8080/
  - Valid redirect URL : http://localhost:8080/*
  - Web origins : http://localhost:8080/
  - Client Authentication : ON
```
* client 등록 후 
  * client-id와 client-secret 값을 springboot application.properties 에 추가하여 
  * Application과 Keycloak의 연동을 마무리 한다.

---

### Keycloak 설정(2)
* application.properties 설정
```properties
#keycloak
spring.security.oauth2.client.registration.keycloak.client-id=sso-test-site1
spring.security.oauth2.client.registration.keycloak.client-secret=pqOY83Zl41Zes5PywlLYfdP47DN7gngE
spring.security.oauth2.client.registration.keycloak.provider=keycloak
spring.security.oauth2.client.registration.keycloak.scope=openid,profile,email
spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.keycloak.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}
spring.security.oauth2.client.provider.keycloak.issuer-uri=https://www.keycloak-url.com:9090/realms/SSO_TEST
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://www.keycloak-url.com:9090/realms/SSO_TEST
```
