# eBazzar Backend

eBazzar Backend is the server-side component of the eBazzar application, built using Spring Boot. It provides RESTful APIs to manage users, products, and orders for the eBazzar platform.

## Features

- **User Authentication**: Secure login and registration using JWT tokens.
- **Product Management**: API to add, update, delete, and fetch products.
- **Order Management**: Endpoints to place, view, and manage orders.
- **Admin Panel**: Admin APIs to manage users, products, and orders.

## Technologies Used

- **Spring Boot**: Backend framework for building REST APIs.
- **Spring Security**: For authentication and authorization with JWT.
- **Spring Data JPA**: For interaction with the database.
- **MySQL**: Relational database for production (Dockerized for easy setup).
- **Docker**: For containerizing MySQL database.
- **Maven**: Build tool for managing dependencies.

## Setup and Installation

Follow the instructions below to set up the backend locally with Dockerized MySQL.

### Prerequisites

- JDK 11 or later
- Docker and Docker Compose
- Maven

### Steps to Run the Backend

1. **Clone the repository**:
   ```bash
   git clone https://github.com/sajid-777/eBazzar.git
