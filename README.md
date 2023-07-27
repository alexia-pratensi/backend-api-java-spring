# API for Rental Platform

This project is a backend API for a rental platform. It provides endpoints for user registration, login, and managing rental properties.

This API can be connected with this angular project (v14) provided by Openclassrooms school : https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring/compare/main...alexia-pratensi:Developpez-le-back-end-en-utilisant-Java-et-Spring:main 


## Features

- User registration with name, email and password.
- User login with email and password.
- Get the current logged-in user's details.
- Post a new message for a rental property.
- Get all rental properties.
- Get details of a specific rental property.
- Create a new rental property.
- Update an existing rental property.

## Technologies Used

- Java 17
- Spring Boot 3
- lombok executable jar file
- Spring Security with JWT (JSON Web Tokens) authentication
- Cloudinary (for file upload)
- Swagger
- MySQL + MySQL Workbench

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- Cloudinary account for file upload (API key, secret, and cloud name)
- Angular CLI 14 and Nodes.js (if you test this API with the frontend app proposed)
- MySQL


#### SQL setup

1. Download MySQL and configure it with port 3306
2. Clone this project in your computer: https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring/compare/main...alexia-pratensi:Developpez-le-back-end-en-utilisant-Java-et-Spring:main 
3. Download MySQL Workbench and select File > Open SQL Script > choose script.sql using this path: [your folder]\Developpez-le-back-end-en-utilisant-Java-et-Spring\ressources\sql\script.sql
4. Click on lightning ⚡ (execute the selected portions of the script…) to generate the schemas
5. On the left sidebar (“navigator”), if you select the “schemas” tab, you will see all the tables generated. If you double click on one of them, its window will open. Then you can check the content recorded with the following code: “SELECT * FROM script.tablename;“ followed by a click on ⚡


### Installation

1. Clone the repository.
2. Import the project into your favorite IDE.
3. Set up the Cloudinary credentials in `CloudinaryConfig.java`.
4. Build the project using Maven.

## Run project

1. Start the application: run the spring boot project
2. Use API clients like the frontend app proposed on top or Postman to interact with the endpoints.

## API Endpoints and documentation

Swagger Documentation : http://localhost:3001/documentation.html

- **POST** `/api/auth/register`
- **POST** `/api/auth/login`
- **GET** `/api/auth/me`

- **POST** `/messages`

- **GET** `/rentals`
- **GET** `/rentals/{id}`
- **POST** `/rentals`
- **PUT** `/rentals/{id}`

## Author
Alexia PRATENSI
