# Spring Security Demo application by Using JWT

## Description
Welcome aboard to this project that is intended to apply security filters to a REST API created with 
**Spring Framework** and **Spring Security**.

## Table of Contents


## Requisites
Below are listed all the necessary things to run this project properly.
- Java 8 
- MySQL 8.0 or greater. 
- RSA Key Generator: [Online Tool](https://acte.ltd/utils/openssl) or openssl on macOS terminal

## Instructions to Install and Run the Project
1. Clone the repository and install Maven dependencies
2. Open the script `db_creation_script.sql` and execute it by using MySQL. This will create the database, table, and 
insert a set of credentials you can use later on to test this API.
3. Generate the public and private RSA keys in the `src/main/resources/certs`.
   - In you are using macOS you can follow these steps:
     1. `openssl genrsa -out keypair.pem 2048`
     2. `openssl rsa -in keypair.pem -pubout -out public.pem`
     3. `openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem`
     4. Delete the keypair.pem file.
   - If you are using Windows you can follow these steps:
     1. Open this [Online Tool](https://acte.ltd/utils/openssl)
     2. Select the key length 2048 and generate the keys with pkcs8 format.
     3. Save the keys named `private.pem` and `public.pem`
4. Modify the `appplication.yml` file by:
   - replacing the value `HERE_YOUR_PASSWORD` in the `rsa.secret-key` tag. Keep in mind that this password must be long 
   enough and safe enough.
   - selecting the key-system you want you use. For that purpose, change the `key-system` value for either `rsa_key` 
   or `secret_key`. This value will make the application use the private key or the secret key to encode the JWT token.
5. Open any HTTP Client service like Postman or SoapUI or test the connection by terminal.
6. Run or Debug the application and test the App is up and running connecting to endpoint `GET /home` with the command  
    ```
    curl --location 'http://localhost:8080/home'
    ```  
    You should receive the response  
    ```
    {
      "apiCode":0,
      "apiMessage":"Success",
      "apiData":"API is up and running."
    }
    ```
7. Test the Login endpoint `POST /login` with the command  
   ```
   curl --location 'http://localhost:8080/login' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "email": admin@domain.com",
   "password": "admin"
   }'
   ```
   You should receive a response like
   ```
   {
     "apiCode": 0,
     "apiMessage": "Success",
     "apiData": "Bearer [here_the_token]"
   }
   ```
8. Test the JWT token by calling the endpoint `GET /auth` with the command
   ```
   curl --location 'http://localhost:8080/auth' \
   --header 'Authorization: Bearer [here_the_token]'
   ```
   You should receive a response like
   ```
   {
     "apiCode": 0,
     "apiMessage": "Success",
     "apiData": "Connection authenticated"
   }
   ```

## Credits
Code created by DFSanchezB.  
You can reach me at dfsanchezb2011@gmail.com 
