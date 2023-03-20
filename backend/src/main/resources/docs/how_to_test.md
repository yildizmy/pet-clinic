## How to test?

### Open API (Swagger) UI

Open API (Swagger) Documentation UI for the endpoints, visit http://localhost:8080/swagger-ui/index.html after running the app.

<br/>

### Postman Collection

The [Postman Collection](postman/pet_clinic.postman_collection.json) shared in the resources can be modified and used
for testing the endpoints.

<br/>

### API Endpoints

> **Note** <br/>
> All URIs are relative to *http://localhost:8080/api/v1*

<br/>

| Class            | Method                                          | HTTP request   | Description                                           |
|------------------|-------------------------------------------------|----------------|-------------------------------------------------------|
| *AuthController* | [**login**](http://localhost:8080/api/v1/auth)  | **POST** /auth | Authenticates users by their credentials              |
| *AuthController* | [**signup**](http://localhost:8080/api/v1/auth) | **POST** /auth | Registers users using their credentials and user info |

<br/>
<br/>

| Class           | Method                                                            | HTTP request         | Description                                          |
|-----------------|-------------------------------------------------------------------|----------------------|------------------------------------------------------|
| *PetController* | [**findById**](http://localhost:8080/api/v1/pets/{id})            | **GET** /pets/{id}   | Retrieves a single pet by the given id               |
| *PetController* | [**findAll**](http://localhost:8080/api/v1/pets)                  | **GET** /pets?page=0&size=10&sort=id,asc        | Retrieves all pets based on the given parameters     |
| *PetController* | [**findAllByUserId**](http://localhost:8080/api/v1/pets/{userId}) | **GET** /pets/{userId} | Retrieves all pets based on the given userId         |
| *PetController* | [**findAllByType**](http://localhost:8080/api/v1/pets/types)      | **GET** /pets/types  | Retrieves counts of all pets by selected type        |
| *PetController* | [**create**](http://localhost:8080/api/v1/pets)                   | **POST** /pets       | Creates a new pet using the given request parameters |
| *PetController* | [**update**](http://localhost:8080/api/v1/pets)                   | **PUT** /pets        | Updates pet using the given request parameters       |
| *PetController* | [**deleteById**](http://localhost:8080/api/v1/pets/{id})          | **DELETE** /pets     | Deletes pet by id                                    |

<br/>
<br/>

| Class            | Method                                                    | HTTP request        | Description                                           |
|------------------|-----------------------------------------------------------|---------------------|-------------------------------------------------------|
| *TypeController* | [**findById**](http://localhost:8080/api/v1/types/{id})   | **GET** /types/{id} | Retrieves a single type by the given id               |
| *TypeController* | [**findAll**](http://localhost:8080/api/v1/types)         | **GET** /types?page=0&size=10&sort=id,asc | Retrieves all types based on the given parameters     |
| *TypeController* | [**create**](http://localhost:8080/api/v1/types)          | **POST** /types     | Creates a new type using the given request parameters |
| *TypeController* | [**update**](http://localhost:8080/api/v1/types)          | **PUT** /types      | Updates type using the given request parameters       |
| *TypeController* | [**deleteById**](http://localhost:8080/api/v1/types/{id}) | **DELETE** /types   | Deletes type by id                                    |

<br/>
<br/>

| Class            | Method                                                           | HTTP request          | Description                                                         |
|------------------|------------------------------------------------------------------|-----------------------|---------------------------------------------------------------------|
| *UserController* | [**findById**](http://localhost:8080/api/v1/users/{id})          | **GET** /users/{id}   | Retrieves a single user by the given id                             |
| *UserController* | [**findAll**](http://localhost:8080/api/v1/users)         | **GET** /users?page=0&size=10&sort=id,asc | Retrieves all users based on the given parameters     |
| *UserController* | [**create**](http://localhost:8080/api/v1/users)                 | **POST** /users       | Creates a new user using the given request parameters               |
| *UserController* | [**update**](http://localhost:8080/api/v1/users)                 | **PUT** /users        | Updates user using the given request parameters                     |
| *UserController* | [**updateFullName**](http://localhost:8080/api/v1/users/profile) | **PUT** /users/profile | Updates user profile by Full Name (First Name and Last Name fields) |
| *UserController* | [**deleteById**](http://localhost:8080/api/v1/users/{id})        | **DELETE** /users     | Deletes user by id                                                  |

<br/>

### Unit & Integration Tests
Unit and Integration Tests are provided for services and controllers in the corresponding packages.

<br/>
<br/>