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