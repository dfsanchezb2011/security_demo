# Spring Boot Security Demo Project
**Created by DFSanchezB**

This project is intended to configure a Rest service with a basic security authentication.

### Step 1.
The project is created by using Spring Initializr with some basic dependencies like Spring Web and Lombok.

### Step 2.
It's created a new Controller class named HomeController. This is able to receive a GET HTTP method and response with a 
successful connection message.

### Step 3.
New Maven dependency added for secure the Rest API. `spring-boot-starter-security`  
The SecurityConfig class is created to add the basic configurations of the RestAPI.  
The SecurityFilterChain is able to avoid any request that doesn't comply with authentication policies.  
The UserDetailsService bean is used to create a user in memory.  

On the other hand, the AuthController class is created to test the security filter.

In order to test this step, it's possible to use Postman to send a GET request to `/auth` with basic authentication 
using username and password credentials.

### Step 4
It is created a class called TokenUtils that is in charge to generate a token.
It would be ideal that the static variables `ACCESS_TOKEN_SECRET` and `ACCESS_TOKEN_VALIDITY_SECONDS` must be declared 
in the application properties file.
For this exercise, the Bearer token is injected inside the header Authorization tag and inside the body response. 

Then, the AuthCredentials class is created as the model that the customer must send when wants to authenticate 
in the Rest service.

The ApiUserDetailService is created to run a query against the DB to get the customer information by the username typed.
This use the ApiUserRepository that uses JPA to connect to the database.
As Spring hase some predefined classes in its security core, the interface UserDetailsService is implemented for this 
class.

The ApiUserDetails class is created to receive a user, and as this implements the class UserDetails, some specific 
methods are override to define the status or details of the user. 

The JWTAuthenticationFilter is created to authenticate the username based on the credentials received.

The JWTAuthorizationFilter class is able to intercept the request made by the customer and validate if that request is 
authorized.  
In this case, it is assumed that the customer has already been authenticated and is using its respective JWToken.

Then, the JWTAuthorizationFilter is injected in the SecurityConfig class and used to set the authentication manager and
setting the path when it is logged.  
Basically, this class and the method `doFilterInternal` can be used to validate the request received, like headers,
body, roles, permissions, etc., to know if it is an authorized request and continue with the request.  
In this case, it is being captured the `Authorization` value from the header, validates if it starts with `Bearer ` and
then get the token to validates its authenticity.

Now, we proceed to modify the `SecurityConfig` class to inject the `UserDetailsService` and `JWTAuthorizationFilter` classes.  
The `filterChain` method is modified by instantiating a JWTAuthenticationFilter, setting the authenticationManager 
received in the function, and setting the path `/login` as the authentication endpoint.  
The httpSecurity (securityFilterChain) is modified as well by adding `.addFilter(jwtAuthenticationFilter)` and defining 
the chain's order.

> **Note**
> To know how the password has been encoded, this is the code used:  
> `new BCryptPasswordEncoder().encode("HereThePassword")`

To test the application is working, run the following steps:
1. Call the POST `/login` endpoint sending the body with a JSON object including email and password.
    ```
    POST /login HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    Content-Length: 64
    
    {
      "email": "dsanchez@gmail.com",
      "password": "admin123"
    }
    ```

2. Run the GET `/auth` endpoint by using Bearer authentication with the JWT received in the step No. 1
    ```
    GET /auth HTTP/1.1
    Host: localhost:8080
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiZGZzYW5jaGV6YiIsInN1YiI6ImRzYW5jaGV6QGdtYWlsLmNvbSIsImlhdCI6MTY5MjM5OTk2MywiZXhwIjoxNjkyNDAwMjYzfQ.IoD2IBImuDBgMAMouMbGG9n_CqRHoy2d9b8me0HgBHI
    ```
