# Organization Service

This project is the **Organization Service** component of the **Voters Voice Amendment (VVA)** system. It provides functionality for managing organizations that are related to initiatives. Organizations can support or oppose initiatives and provide relevant information about their involvement.

## Features

- **Organization Management**:
    - Create, update, and delete organizations.
    - Retrieve details of specific organizations.
    - List all organizations.

- **Initiative Association**:
    - Organizations can be associated with specific initiatives (either as supporters or opponents).

## Technologies Used

- **Spring Boot** 3.3.4 (Java 23)
    - **Spring Web**: For building RESTful APIs.
    - **Spring Data JPA**: For interacting with the PostgreSQL database.
    - **PostgreSQL**: As the relational database to store organization data.

## Project Setup

### Prerequisites

Before running this project, ensure you have the following installed:

- **Java 23**
- **Maven**
- **PostgreSQL** (If running locally)

### Build and Run the Project

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/organizationservice.git
    cd organizationservice
    ```

2. **Build the project**:
    ```bash
    mvn clean package
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

The application will start on [http://localhost:8080](http://localhost:8080) by default.

### Dockerization

This service is containerized using Docker. You can build and run the Docker container as follows:

1. **Build the Docker image**:
    ```bash
    docker build -t organizationservice .
    ```

2. **Run the Docker container**:
    ```bash
    docker run -p 8080:8080 organizationservice
    ```

### Database Configuration

The project is configured to use **PostgreSQL**. You can set the database credentials in the `application.properties` or use environment variables to override them.

**Example properties**:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/organization_service
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
