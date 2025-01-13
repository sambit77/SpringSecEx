### This application demonstrates spring security

#### Branches

##### 1. Default-Spring-Security :- Intro to Spring Security (No Customizations)
##### 2. Custom-Spring-Security / Main:- Spring Security with Users stored in DB
##### 3. Jwt-Spring-Security :- Spring Security with JWT (Support for /login on top of #2)
##### 4. OAuth-Spring-Security :- Spring Security with support for OAuth login (Google & Github)

### Steps to run this app :-

1. Clone this repo and switch to "Main" branch
2. Ensure docker desktop is running and then run root/docker-compose.yml file
3. Run the SpringSecExApplication
4. Import postman collection in to postman and explore DB-Users APIs
5. A default user ("admin","admin") is automatically created in db
6. Hit `localhost:8080/register`  with basic authentication ("admin","admin") to register a new user
   `{
   "id": 10,
   "password": "bit",
   "username": "sam"
   }`
7. Response of the above endpoint is the created user in the DB with Bcrypt encoded password (Strength=12)
#### Branch:- Default-Spring-Security
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

#### Branch:- Custom-Spring-Security
1. SecurityConfig class -> SecurityFilterChain bean is customized here
2. UserDetailsService bean can be customized to use InMemory username and password pairs.
3. AuthenticationProvider bean can be used to authenticate users form users database

##### Steps to authenticate users from a database
1. Spin up a postgres db in docker by running docker-compose.yml file
2. Create Spring JPA connection to the database (Users,UserRepository)
3. Steps to configure SpringSecurity to use this database :-
4. Modify AuthenticationProvider bean to provide your own UserDetailsService i.e MyUserDetailsService
5. You can set your PasswordEncoder in this AuthenticationProvider as well. (BCryptEncoder is used in this project)
6. In MyUSerDetailsService class override loadUserByUsername to load the user from your own database (UserRepository)
7. If user is found return an object of UserPrincipal(Child class that implements UserDetails)
8. UsersDataInitializer :- This class is used to create a default user in the database.
9. BCryptEncoder :- Instead of storing password directly in database it can be encoded and accordingly step 5 need to be implemented.

#### Branch:- JWT-Spring-Security

There are 2 major steps involved in JWT (Json Web Token) based authentication
1. Upon successful login (using username and password) issue a JWT to the user
2. On subsequent request to the server pass this JWT as bearer token in header which will be used in authenticating the request

#### Steps to run
1. Run docker -> Spin up a postgres db in docker by running docker-compose.yml file -> Run the spring application
2. Refer to "JWT-Example" postman collection
3. `localhost:8080/register` :- Hit this url (no auth required) to register an user
4. `localhost:8080/login-user` :- Hit this url with the registered user in the body to get the JWT token
5. `localhost:8080`:- The JWT token can be used to access other closed endpoints with Authtype = bearer token

##### ControlFlow: Issue JWT token to valid users `localhost:8080/login-user`

1. /register and /login-user endpoints are open url and no authentication is required to access (SecurityConfig.java line 37)
2. create a /login-user postmapping to accept an username and password which will return JWT upon successful authentication
3. Get hold of AuthenticationManager bean in SecurityConfig class and in USerService class use the authenticationManager object to verify the user
4. If authentication is successful use JwtService class to generate a JWT token based on username
5. JWT Token consists of subject which is username , issuedAt, expiryAt and it is signed with a key type HmacSHA256
6. After building this JWTToken return it as a response

##### ControlFlow: Validate a JWT token for other endpoints `localhost:8080`

1. Hit `localhost:8080` or any other closed endpoint with the issued JWT token
2. In the request set Authorization type = Bearer token and value = issued JWTToken
3. Before this request goes through USernamePasswordAuthentictaionFilter -> It should go through your own custom filter (JwtFilter) (SecurityConfig -> Line 42)
4. Create a custom filter JwtFilter by extending to OncePerRequestFilter and implement the doFilterInternal method
5. In doFilterInternal method , extract the authorization header -> bearer token and validate this token by extracting username , timestamp etc
6. If validation is successful create an usernamepasswordauthenticationtoken and set it in security context
7. Forward the request to other filters


#### Branch:- OAuth-Spring-Security
1. SecurityConfig class -> SecurityFilterChain bean is customized here to use OAuth2
2. Mention your OAuth servers (Google & Github) in application properties
   ```shell
   spring.security.oauth2.client.registration.google.client-id=
   spring.security.oauth2.client.registration.google.client-secret=
   
   spring.security.oauth2.client.registration.github.client-id=
   spring.security.oauth2.client.registration.github.client-secret=
   ``` 
3. Client-Id and secrets can be generated using a developer account (Google and Github)
   Google cloud console -> API & Services -> Credentials -> OAuth2 client
   Github -> Settings -> Developer Settings -> OAuth Apps

4. Hit `localhost:8080` to access the application with oauth2 login support