#  Google Forms Clone - Backend
This is the backend service for Google Forms clone, built using Spring Boot and MongoDB.

## Frontend Repo
Link: https://github.com/xutkarshjain/Google-form
Credits: [Utkarsh Jain](https://github.com/xutkarshjain)

## Features

### Form Management
- Create, update, fetch and delete forms.

### Response Handling
- Submit and fetch responses for forms.

### User Authentication
- Supports Google OAuth2 and JWT-based authentication for secure access.

### User Authorization
#### Users Logged-in via Google
- Have role set ROLE_USER
- Can create, update, delete and view responses of forms created by self.
- Can respond to forms created by any.

#### Guest Users
- Have role set ROLE_GUEST
- All guest users share the same data scope, meaning forms and responses created by one guest user are accessible to all guest users.
- Cannot access or respond to forms created by authenticated users.

## Technologies Used
- Java 17
- Spring Boot
- MongoDB
- Docker

## Swagger link
http://localhost:8080/swagger-ui.html