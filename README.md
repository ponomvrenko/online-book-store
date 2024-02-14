# Online Book Store📚
_The purpose of this API is to provide access to data, functionality, and services that enhance customer experience and drive business growth._
## 📃Table of Contents

- **[Introduction](#Introduction)**
- **[️Feature](#feature)**
- **[Technologies Used](#technologies-used)**
- **[Api functionalities](#api-functionalities)**
- **[Prerequisites](#Prerequisites)**
- **[Installation](#installation)**
- **[Usage](#usage)**
- **[Contributing](#contributing)**
- **[Challenges and Solutions](#challenges-and-solutions-in-building-our-online-book-store)**
- **[Possible improvements](#possible-improvements)**
- **[Summary](#summary)**

## ✍️Introduction
Greetings and welcome to the Online Book Store project! This is a resilient and secure Java Spring Boot application meticulously crafted to offer an outstanding online shopping experience for avid readers. Incorporating an array of features and cutting-edge technologies, our project aims to deliver a flawless and secure e-commerce platform for users. In the following sections, we'll acquaint you with the essential aspects and functionalities of our application.



## 🎖️Features

1. **Shopping Cart:** Our Online Book Store allows users to add books to their shopping carts. You can easily browse through our extensive collection of books, select your favorites, and add them to your cart. The cart is fully functional, enabling users to view, edit, and finalize their purchases before proceeding to checkout.


2. **Order Management:** Once you've filled your shopping cart with the books you desire, our application provides a streamlined order management system. You can review your orders and add them.


3. **Book Management with Pagination and Sorting:** We understand the importance of efficient book discovery. Our application offers a sophisticated search functionality that allows users to find all books by categories, author, title, or by id. With pagination support, you can explore a vast catalog of books without overwhelming your search results.


4. **Security and JWT Tokens for User Auth:** Security is paramount in our application. We've implemented Spring Security to safeguard your data and transactions. JWT (JSON Web Tokens) are used for secure authentication and authorization, ensuring that only authorized users can access sensitive functionalities.

---

## 💻Technologies Used

*Our application leverages several cutting-edge technologies, including:*

***📌Core Spring Boot Dependencies:***

**Spring Boot Starter:** Provides essential dependencies and auto-configuration for a Spring Boot application.

**Spring Boot Starter Data JPA:** Simplifies database access using the Java Persistence API (JPA).

**Spring Boot Starter Web:** Includes the necessary dependencies for building web applications and RESTful APIs.


***📌Database Related:***

**H2 Database:** An in-memory database for development and testing purposes.

**MySQL:** Enables connectivity between the Spring application and MySQL database.


***📌Testing:***

**JUnit:** A widely used testing framework for writing and running unit tests.

**Mockito:** The framework allows the creation of test double objects in automated unit tests.

**HSQLDB (for testing):** An in-memory database used for testing data access logic.

**Spring Boot Starter Test:** Provides testing support for Spring Boot applications.


***📌Lombok:***

**Project Lombok:** Reduces boilerplate code by providing annotations to generate common methods.


***📌Mapping:***

**MapStruct:** Simplifies the mapping between DTOs (Data Transfer Objects) and entities.


***📌Database Migration:***

**Liquibase Core:** Manages database schema version control and migration.

**Liquibase Maven Plugin:** Integrates Liquibase into the Maven build process for database migrations.


***📌Validation:***

Spring Boot Starter Validation: Includes Java Bean Validation with Hibernate Validator for data validation.

***📌API Documentation:***

Springdoc OpenAPI Starter WebMVC UI: Generates and serves API documentation using the OpenAPI specification.

***📌Security:***

**Spring Boot Starter Security:** Provides security features for the Spring Boot application.

**JSON Web Token (JWT):** Used for authorization and security, enabling stateless authentication.

***📌Docker:***

**Spring Boot Docker Compose:** Simplifies the deployment of the application in Docker containers.

---

## 🛜Api Functionalities

In our Online Book Store project, we've diligently followed REST (Representational State Transfer) principles in designing our controllers. Controllers are essential components responsible for handling HTTP requests and responses.

**BookController:** receive and handle requests for adding, updating, getting and searching books.
- `GET: /api/books` - The endpoint for review all the books with User authority.
- `GET: /api/books/{id}` - The endpoint for searching a specific book with user authority.
- `POST: /api/books` - The endpoint for creating a new book with Admin authority.
- `PUT: /api/books/{id}` - The endpoint for updating information about our book with Admin authority
- `DELETE: /api/books/{id}` - The endpoint for deleting books with Admin authority.
- `GET: /api/books/search` - The endpoint for searching books by parameters with User authority.

**CategoryController:** receive and handle requests for adding, updating, getting categories and getting all books by category.
+ `GET: /api/categories` - The endpoint to look at all categories with User authority.
+ `GET: /api/categories/{id}` - The endpoint for searching a specific category with User authority.z
+ `POST: /api/categories` - The endpoint for creating a new category with Admin & User authority.
+ `PUT: /api/categories/{id}` - The endpoint for updating information about specific category with Admin authority.
+ `DELETE: /api/categories/{id}` - The endpoint for deleting categories with Admin authority.
+ `GET: /api/categories/{id}/books` - The endpoint for looking at books with specific category with User authority.

**AuthenticationController:** receive and handle requests for register and login user (with email and password or jwt token).
+ `POST: /api/auth/registration` - The endpoint for user registration.
+ `POST: /api/auth/login` - The endpoint for user login.

**ShoppingCartController:** receive and handle requests for adding, deleting and updating books to shopping cart, also getting user's shopping cart. All endpoints are available with User authority.
+ `GET: /api/cart` - The endpoint for getting all items in your shopping cart.
+ `POST: /api/cart` - The endpoint for adding items in your shopping cart.
+ `PUT: /api/cart/cart-items/{itemId}` - The endpoint for updating items quantity.
+ `DELETE: /api/cart/cart-items/{itemId}` - The endpoint for deleting items from your shopping cart.

**OrderController:** receive and handle requests for creating and getting order, also updating status of order.
+ `GET: /api/orders` - The endpoint for getting orders history with User authority.
+ `POST: /api/orders` - The endpoint for placing orders with User authority.
+ `PATCH: /api/orders/{id}` - endpoint for updating orders status with Admin authority.
+ `GET: /api/orders/{orderId}/items` - endpoint for getting order items from specific order with User authority.
+ `GET: /api/orders/{orderId}/items/{itemId}` - endpoint for getting specific item from certain order with User authority.

In detail order about functions of controllers, you can read at [Features](#features).

## ☕Prerequisites

List the prerequisites required to run your application. Include things like:

- Java 17 [Download Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Docker [Download Docker](https://www.docker.com/)

## ⬇️Installation

1. Clone repository:
   - `Open IDEA` → `File` → `New Project from Version Control` and insert link: [https://github.com/ponomvrenko/online-book-store](https://github.com/ponomvrenko/online-book-store).
   - Or clone from the console with the command: `git clone https://github.com/ponomvrenko/online-book-store`
2. Build project and download dependencies for Maven with command: `mvn clean install`
3. Docker Compose your project: `docker compose build` and `docker compose up`

## 🗂️Usage

If you want to test the API without installation, go to the [Swagger UI](http://ec2-54-198-171-11.compute-1.amazonaws.com/api/swagger-ui/index.html#/), where you can interact with all endpoints thanks to OpenAPI.

- Create your own user: Use endpoint `auth/register` to register a user and stick to these rules:
  1. `firstName` length between 1 and 255
  2. `lastName` length between 1 and 255
  3. `email` should be valid
  4. `password` minimum length is 4
  5. `shippingAddress` is optional at this time

## 🫂Contributing

We welcome contributions from the community to enhance the Online Book Store project. Whether you want to fix a bug, improve an existing feature, or propose a new feature, your contributions are valuable to us. You can follow the installation guide and create a Pull Request to the project. If you want to contact for more information and cooperation in development: [danil.ponomvrenko@gmail.com](mailto:danil.ponomvrenko@gmail.com)

## 🦾Challenges and Solutions in Building Our Online Book Store

*Building the Online Book Store project was a fulfilling endeavor, marked by various challenges. In this article, we'll briefly explore the hurdles we encountered during development and how we addressed them to create a robust e-commerce platform.*

### 🔸Challenge 1: Data Modeling and Build Different Views from Our Domain Models

**Issue:** Designing a flexible data model for books, users, orders, and categories was complex and showing the user only necessary data.

**Solution:** We used Spring Data JPA and Liquibase to create an adaptable schema and pattern Dto.

### 🔸Challenge 2: Security

**Issue:** Ensuring data security and user authentication was paramount.

**Solution:** Spring Security and JWT tokens were implemented for robust protection.

### 🔸Challenge 3: Exception Handling

**Issue:** Systematic handling of errors and exceptions was crucial because they need to be handled at the application-wide level, not just on a single controller.

**Solution:** We developed a global handler and custom exceptions for better error reporting.

### 🔸Challenge 4: Complex Configuration
**Issue:** Spring Security can be intricate to configure, particularly when dealing with custom authentication and authorization requirements.

**Solution:** The Spring Security configuration was documented comprehensively, providing thorough explanations for each aspect. This documentation offers clarity for both contributors and users on the setup of authentication and authorization.

### 🔸Challenge 5: Token-Based Authentication
**Issue:** The integration of JWT (JSON Web Tokens) for token-based authentication posed challenges related to token creation, validation, and secure storage.

**Solution:** JWT was adopted as the authentication mechanism, adhering to industry best practices. Established libraries were utilized, and security guidelines for token creation, validation, and storage were followed.

### 🔸Challenge 6: User Management
**Issue:** The efficient management of user roles, permissions, and access control within the API needed a structured approach.

**Solution:** A custom `UserDetailsService` was implemented to manage user roles, permissions, and access control. This customization allowed for user management tailored to specific requirements while maintaining security.


## 💎Possible improvements

1. **Enhanced User Profiles:**
    - Consider implementing user profiles that allow users to customize and manage their preferences, track order history, and save favorite books.
    - Integrate a feature for users to update their personal information, including shipping addresses and contact details.


2. **Advanced Search Filters:**
    - Expand the search functionality by adding advanced filters, such as price range, publication date, and user ratings, to provide users with more precise book search results.


3. **Recommendation Engine:**
    - Introduce a recommendation system that suggests books based on users' past purchases, browsing history, or preferences. This can enhance the personalized shopping experience.


4. **Responsive Design:**
    - Optimize the application's user interface for different devices and screen sizes, ensuring a seamless experience on both desktop and mobile platforms.


5. **Internationalization and Localization:**
    - Implement support for multiple languages to make the platform accessible to a broader audience. This can involve translating the user interface and providing localized content.


6. **Payment Gateway Integration:**
    - Integrate popular and secure payment gateways to offer users a variety of payment options, enhancing the convenience of the checkout process.


7. **Real-time Notifications:**
    - Implement a notification system to update users on order status, promotions, and new arrivals. Real-time notifications can improve user engagement and keep customers informed.


8. **Social Media Integration:**
    - Allow users to share their favorite books, reviews, or purchases on social media platforms. This can increase the visibility of the online bookstore and attract new customers.


9. **Analytics and Reporting:**
    - Incorporate analytics tools to track user behavior, popular book categories, and other relevant metrics. This data can be used to make informed decisions for future improvements.


10. **Progressive Web App (PWA):**
    - Develop the application as a Progressive Web App to enable offline access, push notifications, and an app-like experience for users, especially on mobile devices.


11. **Automated Testing Suite:**
    - Strengthen the testing infrastructure by developing an extensive automated testing suite. This ensures the reliability and stability of the application, especially with future updates and feature additions.


12. **Community Engagement:**
    - Foster a community around the Online Book Store project by creating forums, discussion groups, or a feedback system. Engaging with users and contributors can provide valuable insights for continuous improvement.


Implementing these possible improvements can contribute to the long-term success and growth of the Online Book Store, providing an even more robust and user-friendly experience.

## 🏁Summary

In summary, our project overcame these challenges through adaptable data modeling, strong security measures, systematic error handling. It stands as a testament to our commitment to best practices in software development, offering users a secure and enjoyable shopping experience while adhering to REST principles.