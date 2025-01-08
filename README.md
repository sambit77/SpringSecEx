### This application demonstrates spring security 

1. Spring Security dependency by default gives login and logout template pages 
2. It generates session id for each user
3. Default username is user and password is auto generated on console.(For Dev only)
4. Global username and password can be configured in application.properties file
    ```
    spring.security.user.name=sambit
    spring.security.user.password=123456
   ```
5. In case of POST, PUT, DELETE along with basic authentication parmas (username and password),
   spring security expects a CSRF token (X-CSRF-TOKEN) in the request header else it will throw unauthorized error
6. Use `localhost:8080/csrf-token` url to get the csrf token
7. Refer to postman collection for all the endpoints.