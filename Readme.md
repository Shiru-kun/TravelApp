# Travel App Assistant Documentation

## Description
This app is a vodafone app challenge for travel assistant, the user inserts an city and it should 
display the weather, exchange the rate, gpd and population data. 


## Stack
- Java 17
- Spring boot
- React jS 
- Postgresql 16
- h2 database

### Backend dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Cloud Starter OpenFeign
- Spring Boot Starter Cache
- Spring Boot Starter Security
- Spring Boot DevTools
- Spring Boot Test
- Spring Security Test
- Lombok
- JUnit Jupiter
- Mockito Core
- H2 Database
- SpringDoc OpenAPI Starter WebMvc UI

#### Aplication.yml
###### Description 
  - Contains the database source, application port where the app(backend) runs, the external endpoints , jwt secret, expiration time, rate limit max requests, and api docs url. 

#### Configure and run application
    Note:  Ensure that you have a database created with the name vodafone_travel_assistant or else and running on port that matches the application.yml

##### Technical Details
- Server Port: `8080`
- Database Configuration:
  - Username: `postgres`
  - Password: `root`
  - URL: `jdbc:postgresql://localhost:5432/vodafone_travel_assistant?currentSchema=vodafone_travel_assistant`
- Swagger UI Path: `/api-docs`
- Swagger UI endpoint: `http://localhost:8080/swagger-ui/index.html#/`
-- Base URL: `http://localhost:8080/`

### Frontend dependencies
- React Router
- i18next
- Axios
- Chartsjs
- Sass
- Toaster

#### Configure and run application
- Install node version v16.20.2 and on the frontend path ./travelapp-frontend/
  ```
    npm install
  ```
  ```
    npm start
  ```
Will be running by default on `http://localhost:3000`.

-  Note : under travelapp-frontend\src\utils\constants.ts you'll found the base url for the backend.


## Important if you cloned the dockerized version

- Run the commands start.sh on a windows machine, ensure you have docker installed on your computer 

  ```
      docker-compose down

      docker build -t travel-app:latest ./TravelApp

      docker build -t travel-app-frontend:latest ./travelapp-frontend

      docker-compose up --build --force-recreate --remove-orphans
  ```

  #### Running ports 

  ```
    Frontend: http://localhost:9090/#/
    Backend: http://localhost:8080
  ```

  You can change on the app on docker-compose the desired ports :
  ``` 
    postgres-db:
      image: postgres:16
      ports:
        - "5432:5432"
      environment:
        - POSTGRES_DB=YOUR_DATABASE_NAME
        - POSTGRES_USER=YOUR_DB_USERNAME
        - POSTGRES_PASSWORD=YOUR_DB_PASSWORD
    spring-app:
      image: travel-app:latest
      ports:
        - "8080:8080"
      environment:
        - SPRING_DB_PASSWORD=YOUR_DB_PASSWORD
        - SPRING_DB_USERNAME=YOUR_DB_USERNAME
        - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/YOUR_DATABASE_NAME
      depends_on:
        - postgres-db
    travel-app-frontend:
      build:
        context: ./travelapp-frontend
        dockerfile: Dockerfile
      ports:
        - "9090:80"
      depends_on:
        - spring-app
    ```
  - Note: Change the REACT_APP_API_BASE_URL=http://localhost:8080 on /travel_frontend/env for your specific port.
