<div align="center">

![evora-logo-banner.jpg](docs%2Fimages%2Fevora.jpg)

  <img src="https://img.shields.io/badge/Status-In%20Progress-yellow?style=for-the-badge&logo=headspace&logoColor=orange&color=yellow" alt="repo-status" />
  <a href="/README-pt-br.md">
    <img src="https://img.shields.io/badge/README-PT--BR-009C3B?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyBmaWxsPSIjZmZmIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxNjAiIGhlaWdodD0iMTAwIiB2aWV3Qm94PSIwIDAgMzIgMjAiPjxwYXRoIGZpbGw9IiMwMDljM2IiIGQ9Ik0wIDBoMzJ2MjBIMHoiLz48cGF0aCBkPSJNMTYgM0wzMCAxMCAxNiAxNyAyIDEwem0wIDVhNSA1IDAgMCAxIDAtMTAgNSA1IDAgMCAxIDAgMTB6IiBmaWxsPSIjZGRkIi8+PHBhdGggZD0iTTE2IDZhNCA0IDAgMCAxIDQgNCA0IDQgMCAwIDEtOCAwIDQgNCAwIDAgMSA0LTR6bS0yLjI1IDQuNjZhNS41IDUuNSAwIDAgMSA0LjUtMi4yOCA1LjUgNS41IDAgMCAxLTQuNSAyLjI4WiIgZmlsbD0iIzAwMCIvPjwvc3ZnPg==" alt="evora readme portuguese" />
  </a>
</div>


<div align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ" />
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="POSTGRESQL" />
  <img src="https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white" alt="POSTGRESQL" />
</div>

</br>

# Evora

Evora is an event management system built with a microservices architecture, featuring an API Gateway, messaging, integration with the Asaas payment API, and automated email notifications for participating users.

## Features
- Create accounts of type `PARTICIPANT` or `CREATOR`;
- Users of type `CREATOR` can create and manage events;
- Users of type `PARTICIPANT` can purchase tickets for available events. Upon confirming the purchase, they receive the ticket via email;
- A user of type `CREATOR` cannot purchase tickets for other events, and vice versa;
- Creators can withdraw the money from sold tickets after the event has taken place.

</br>

# Technologies Used
* **Java 17**: A high-level, object-oriented programming language widely used for building server-side applications, web services, and Android applications.

* **Spring Boot 3.5.5**: A framework that simplifies the development of Java applications by providing built-in features for dependency injection, configuration, and microservices support.

* **Spring Security**: A powerful and customizable authentication and access control framework for Java applications.

* **JWT (JSON Web Token)**: Open standard that allows secure transmission of authentication information.

* **Spring Cloud API Gateway**: Serves as the central entry point, handling request routing, authentication, and traffic control between microservices.

* **Spring Cloud Netflix Eureka**: Enables automatic registration and discovery of microservices, facilitating dynamic scaling and seamless communication.

* **Spring Cloud OpenFeign**: A declarative HTTP client for Java, simplifying REST API integration by allowing developers to define API clients with minimal boilerplate code.

* **RabbitMQ**: An open-source software that acts as a message broker, or middleman, between producers and consumers of messages.

* **Jakarta Bean Validation**: A standard framework for declaring and validating constraints on Java objects using annotations, commonly used to enforce business rules and input validation in a clean and declarative way.

* **JUnit**: A widely used testing framework for Java, providing annotations and assertions to write and run unit tests efficiently.

* **JPA**: The Java Persistence API, a specification that provides object-relational mapping (ORM) to manage relational data in Java applications.

* **MapStruct 1.6.2**: A Java mapping framework that simplifies object-to-object mapping, reducing boilerplate code and improving maintainability.

* **Flyway**: A database migration tool that ensures version control and consistency across database schema changes.

* **Lombok 1.18.28**: A Java library that reduces boilerplate code by generating common methods like getters, setters, constructors, and more through annotations.

* **Postman**: A tool used for API testing and development, enabling users to send HTTP requests, inspect responses, and automate API tests.

## Databases and other technologies

* **MongoDB**: A NoSQL database designed for high performance, scalability, and flexibility, storing data in JSON-like documents.

* **PostgreSQL**: A powerful open-source relational database management system, known for its reliability, extensibility, and SQL compliance.

* **Docker**: A platform that allows developers to automate the deployment of applications inside lightweight containers, ensuring consistency across different environments and simplifying the setup process.

</br>

# Requirements
To run the project on your machine, the following tools must be installed and configured beforehand:

- Docker
- Git

</br>

# Installation guide
Follow the steps below to download, configure, and run the project in your environment:

1. **Create an account on Asaas**
- For the project's payment system to work, you need to have an active Asaas account and generate your `API key`.
- Create [here](https://www.asaas.com/login/auth).

2. **Clone the repository**
```bash
git clone https://github.com/ABeatrizSC/evora.git
 ```

3. **Navigate to the project directory**

```bash
cd evora
 ```

4. **Add your JWT KEY and Asaas API KEY**
- Create a .env file at the root of the project, add your JWT KEY to be used for signing the token and your Asaas API_KEY (Step 1). Example:

```.env
JWT_KEY=YOUR_KEY_HERE
API_KEY=YOUR_KEY_HERE
```

5. **Build and initialize the docker container**

 ```bash
docker-compose up --build
 ```

</br>

# Endpoints
## 1. USER-SERVICE
- Authentication and user management microservice.

### **POST** `/api/v1/auth/register`
- Creates a new user.

#### Request Body
- `RegisterRequestDto`
```json
{
    "name": "Marcelo Almeida",
    "email": "marcelo@email.com",
    "password": "marc123",
    "role": "PARTICIPANT", //PARTICIPANT or CREATOR
    "document": "24971563792", // if user role == participant
    "mobilePhone": "4799376637", // if user role == participant
}
```

#### Success Response Body
- `204 CREATED`
---

### **POST** `/api/v1/auth/login`
- Logs in an existing user.

#### Request Body
- `LoginRequestDto`

```json
{
    "email": "marcelo@email.com",
    "password": "marc123"
}
```

#### Success Response Body
- `LoginResponseDto`: JWT bearer token.
```json
{
    "token": "ey..."
}
```
---

### **PUT** `/api/v1/users`
- Updates the authenticated user.

#### Headers
- `Authorization: Bearer <jwt_token>`

#### Request Body
- `UpdateUserRequestDto`

```json
{
    "nameUpdated": "Marcelo Almeida updated",
    "emailUpdated": "marceloupdated@email.com",
    "currentPassword": "marc123",
    "password": "marc124",
    "documentUpdated": "59654828090", // if user role == participant
    "mobilePhoneUpdated": "4799376637" // if user role == participant
}
```

#### Success Response Body
- `UserResponseDto`
```json
{
    "name": "Marcelo Almeida updated",
    "email": "marceloupdated@email.com",
    "role": "PARTICIPANT",
    "customerId": "cus.." // if user role == participant
}
```
---

### **DELETE** `/api/v1/users`
- Deletes the authenticated user.

#### Headers
- `Authorization: Bearer <jwt_token>`

#### Request Body
- None.

#### Success Response Body
- `200 OK`
---

### **GET** `/api/v1/users`
- Returns account details of the authenticated user.

#### Headers
- `Authorization: Bearer <jwt_token>`

#### Request Body
- None.

#### Success Response Body
- `UserResponseDto`
```json
{
    "name": "Marcelo Almeida updated",
    "email": "marceloupdated@email.com",
    "role": "marc123",
    "customerId": "cus.." // if user role == participant
}
```
---

</br>

## Error Messages

All error responses follow the format below:

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "Message explaining the error that occurred."
}
```

| Field     | Type     | Description                                                              |
|-----------|----------|--------------------------------------------------------------------------|
| `status`  | Integer  | HTTP status code of the error.                                           |
| `error`   | String   | Corresponding `HttpStatus` constant name.                                |
| `message` | String   | Descriptive error message, possibly coming from a custom exception.      |

---

</br>

# Contact
* GitHub: [ABeatrizSC](https://github.com/ABeatrizSC)
* Linkedin: [Ana Beatriz Santucci Carmoni](www.linkedin.com/in/ana-carmoni)
* Email: [anabscarmoni@gmail.com](mailto:anabscarmoni@gmail.com)
