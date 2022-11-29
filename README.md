# Spring Boot GraphQL MongoDB Tutorial

### Steps to start the application

* `mvn package` - produces artifact and build a docker image

* `docker-compose up` - starts mongodb, and the api service


Hello and welcome!
Your mission, should you choose to accept it, is to build a service that allows users to
register and login then create a tournament or join other tournaments. This service must
be built with Springboot with Kotlin or Java, with a MongoDB database. Please keep the
tournaments simple, as they only need to minimally resemble a full tournament such as
the ones in https://communitygaming.io/

Service Requirements:
* A guest must be able to register to the platform as a user
* A user must be able to create a tournament
* A user must be able to edit their tournament details
* A user must be able to join other tournaments

Note: Please utilize JWT tokens for user session management
Note: Please use GraphQL for service endpoints

User interface:
* You may develop a user interface for this but it’s fine if the user interactions are
all coming from something like Insomnia or Postman.
Testing:
* Please write a functional or integration test to make sure code is behaving
properly.
Note: Please follow Spring best practices and use Spring Data API