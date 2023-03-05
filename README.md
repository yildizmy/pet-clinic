
> **Note** This is the initial API implementation of the Fullstack application. Frontend part, documentation and the 
> configuration for Docker, etc. will be provided in the following days. For this reason, it is recommended to check the 
> repository after initial release (when it is ready, "DRAFT" will be removed from the title). 


## Pet Clinic (D R A F T)
Fullstack (Spring Boot & React) app for managing vet and pet data by using Spring Security, DataJPA, Hibernate, PostgreSQL, etc.


### Description
This application allows users to register account and login to the system using their credentials. Users can add any amount of their pets 
by providing name and type data.

Application users can also edit/delete their pets and own fullname attributes. There is also Admin type user who has the full observation regarding Users and their pets.
Admin user has also a statistic page with data regarding how many selected types of pets the clinic has registered, access to user’s data and have ability to change any user
attribute, except username.

> **Note** A default administration account is created via migration script with the following credentials. All new users can just create their own
account with default “user” rights.

```
username: johndoe
password: j@hNd@E
```


### Entities

The information about the domain model is shown on [Entity Relationship Diagram](backend/src/main/resources/docs/er_diagram.md) section.


### Getting Started

In order to run and test the application, see details on [How to run?](backend/src/main/resources/docs/how_to_run.md) and [How to test?](backend/src/main/resources/docs/how_to_test.md) sections.

For API documentation of the application, visit the following pages during debugging: <br/>

[Swagger](http://localhost:8080/swagger-ui/index.html)<br/>
[Javadoc](backend/src/main/resources/javadoc/index.html)


### Dependencies

* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Hibernate
* Project Lombok
* SpringFox
* Flyway
* PostgreSQL


### Documentation
[Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)<br/>
[Spring Data JPA Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)<br/>
[Spring Security](https://docs.spring.io/spring-security/reference/index.html)<br/>
[JUnit 5 User Guide](https://junit.org/junit5/docs/snapshot/user-guide/)<br/>


### Version History

* 0.1 Initial Release


<br/>
<br/>