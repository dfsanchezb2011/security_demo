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