# fraud-detection-module

This project exemplifies the usage of <a href="https://spring.io/projects/spring-boot" target="_blank">Spring Boot</a> with <a href="https://tenor.com/view/rickroll-roll-rick-never-gonna-give-you-up-never-gonna-gif-22954713" target="_blank">Java</a> and <a href="https://gradle.org/" target="_blank">Gradle</a>. The implementations is a RESTful that represents a user - transaction model. The business purpose is to log transactions marking them depending of it's risk level.

Additionally this module uses <a href="https://h2database.com/html/main.html" target="_blank">H2</a> to easy handle the data on in-memory database. 

There are to endpoints to consider /users and /transactions. For users you can create, edit and delete. For transactions you can create (initiate a transaction), change it's status dfrom the initial `IN_PROGRESS` to `COMPLETED` or cancel it (from `IN_PROGRESS` to `CANCELLED`). Four risk levels are considered:  `LOW, MEDIUM, HIGH and CRITICAL`.
