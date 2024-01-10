# Online Book Store
The purpose of this API is to provide access to data, functionality, and services that enhance customer experience and drive business growth.
## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Challenges and Solutions](#challenges-and-solutions)
- [Summary](#summary)

## Introduction
Greetings and welcome to the Online Book Store project! This is a resilient and secure Java Spring Boot application meticulously crafted to offer an outstanding online shopping experience for avid readers. Incorporating an array of features and cutting-edge technologies, our project aims to deliver a flawless and secure e-commerce platform for users. In the following sections, we'll acquaint you with the essential aspects and functionalities of our application.

## Features

1. **Shopping Cart:** Our Online Book Store allows users to add books to their shopping carts. You can easily browse through our extensive collection of books, select your favorites, and add them to your cart. The cart is fully functional, enabling users to view, edit, and finalize their purchases before proceeding to checkout.

2. **Order Management:** Once you've filled your shopping cart with the books you desire, our application provides a streamlined order management system. You can review your orders and add them.

3. **Book Management with Pagination and Sorting:** We understand the importance of efficient book discovery. Our application offers a sophisticated search functionality that allows users to find all books by categories, author, title, or by id. With pagination support, you can explore a vast catalog of books without overwhelming your search results.

4. **Security and JWT Tokens for User Auth:** Security is paramount in our application. We've implemented Spring Security to safeguard your data and transactions. JWT (JSON Web Tokens) are used for secure authentication and authorization, ensuring that only authorized users can access sensitive functionalities.

## Technologies Used

Our application leverages several cutting-edge technologies, including:

- **Spring Data JPA:** For efficient data access and persistence.
- **Spring Security:** To secure the application and user data.
- **MapStruct:** For simplified and efficient mapping between DTOs and entities.
- **Docker:** Containerization for easy deployment and scalability.
- **OpenAPI:** For documenting our RESTful APIs, providing clear and detailed information about available resources, request payloads, and response formats.
- **Liquibase:** Used to manage and version the database schema, allowing for easy database schema changes and ensuring data schema consistency across different environments.

## Api Functionalities

In our Online Book Store project, we've diligently followed REST (Representational State Transfer) principles in designing our controllers. Controllers are essential components responsible for handling HTTP requests and responses.

- **BookController:** receive and handle requests for adding, updating, getting and searching books.
- **CategoryController:** receive and handle requests for adding, updating, getting categories and getting all books by category.
- **AuthenticationController:** receive and handle requests for register and login user (with email and password or jwt token).
- **ShoppingCartController:** receive and handle requests for adding, deleting and updating books to shopping cart, also getting user's shopping cart.
- **OrderController:** receive and handle requests for creating and getting order, also updating status of order.

In detail order about functions of controllers, you can read at [Features](#features).

## Prerequisites

List the prerequisites required to run your application. Include things like:

- Java 17 [Download Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Docker [Download Docker](https://www.docker.com/)

## Installation

1. Clone repository:
   - Open IDEA > File > New Project from Version Control and insert link: [https://github.com/ponomvrenko/online-book-store](https://github.com/ponomvrenko/online-book-store).
   - Or clone from the console with the command: `git clone https://github.com/ponomvrenko/online-book-store`
2. Build project and download dependencies for Maven with command: `mvn clean install`
3. Docker Compose your project: `docker compose build` and `docker compose up`

## Usage

If you want to test the API without installation, go to the [Swagger UI*(here will be link after HW with AWS)](...), where you can interact with all endpoints thanks to OpenAPI.

- Create your own user: Use endpoint `auth/register` to register a user and stick to these rules:
  1. `firstName` length between 1 and 255
  2. `lastName` length between 1 and 255
  3. `email` should be valid
  4. `password` minimum length is 4
  5. `shippingAddress` is optional at this time

- For access to all endpoints, use admin credentials in `auth/login`:
  - Username: `alice@gmail.com`
  - Password: `securePassword123`

## Contributing

We welcome contributions from the community to enhance the Online Book Store project. Whether you want to fix a bug, improve an existing feature, or propose a new feature, your contributions are valuable to us. You can follow the installation guide and create a Pull Request to the project. If you want to contact for more information and cooperation in development: [akame.dante13@gmail.com](mailto:akame.dante13@gmail.com)

## Challenges and Solutions in Building Our Online Book Store

Building the Online Book Store project was a fulfilling endeavor, marked by various challenges. In this article, we'll briefly explore the hurdles we encountered during development and how we addressed them to create a robust e-commerce platform.

### Challenge 1: Data Modeling and Build Different Views from Our Domain Models

**Issue:** Designing a flexible data model for books, users, orders, and categories was complex and showing the user only necessary data.

**Solution:** We used Spring Data JPA and Liquibase to create an adaptable schema and pattern Dto.

### Challenge 2: Security

**Issue:** Ensuring data security and user authentication was paramount.

**Solution:** Spring Security and JWT tokens were implemented for robust protection.

### Challenge 3: Exception Handling

**Issue:** Handling errors and exceptions systematically was crucial.

**Solution:** We developed a global handler and custom exceptions for better error reporting.

## Summary

In summary, our project overcame these challenges through adaptable data modeling, strong security measures, systematic error handling. It stands as a testament to our commitment to best practices in software development, offering users a secure and enjoyable shopping experience while adhering to REST principles.