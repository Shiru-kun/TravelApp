### Backend API Documentation

This document outlines the API endpoints provided by the **TravelApp** backend application.

---

#### Authentication Controller

**Base Path:** `/api/auth`

- **POST `/signup`**
  - **Description:** Endpoint for user registration.
  - **Request Body:** `UserAccountDto`
    ```json
    {
      "fullname": "string",
      "email": "string",
      "password": "string"
    }
    ```
  - **Response:** `UserAccount`
    ```json
    {
      "fullname": "string",
      "email": "string"
    }
    ```

- **POST `/login`**
  - **Description:** Endpoint for user login.
  - **Request Body:** `UserAccountDto`
    ```json
    {
      "email": "string",
      "password": "string"
    }
    ```
  - **Response:** `LoginResponse`
    ```json
    {
      "token": "string",
      "expiration": "string"
    }
    ```

---

#### Exchange Rate Controller

**Base Path:** `/api/1.0/exchange-rate`

- **GET `/{symbol}`**
  - **Description:** Get exchange rate data for a given symbol.
  - **Path Parameter:** `symbol` (Optional)
  - **Response:** `ExchangeRateResponse`
    ```json
    {
      "success": true,
      "base": "string",
      "date": "string",
      "rates": {
        "string": 0
      }
    }
    ```

- **GET `/` or `/`
  - **Description:** Get default exchange rate data.
  - **Response:** Same as `GET /{symbol}`

---

#### Weather Controller

**Base Path:** `/api/1.0/weather`

- **GET `/{city}`**
  - **Description:** Get weather data for a given city.
  - **Path Parameter:** `city` (Optional)
  - **Response:** `WeatherData`
    ```json
    {
      "temperature": 0,
      "description": "string",
      "city": "string"
    }
    ```

- **GET `/` or `/`
  - **Description:** Get default weather data.
  - **Response:** Same as `GET /{city}`

---

#### World Bank Controller

**Base Path:** `/api/1.0/world-bank`

- **GET `/gpd/{country}`**
  - **Description:** Get World Bank GDP data for a given country over the past 10 years.
  - **Path Parameter:** `country` (Optional)
  - **Response:** `WorldBankResponse`
    ```json
    {
      "country": "string",
      "indicator": "GDP",
      "data": {
        "year": "value"
      }
    }
    ```

- **GET `/population/{country}`**
  - **Description:** Get World Bank population data for a given country over the past 10 years.
  - **Path Parameter:** `country` (Optional)
  - **Response:** `WorldBankResponse`
    ```json
    {
      "country": "string",
      "indicator": "Population",
      "data": {
        "year": "value"
      }
    }
    ```

---

### Technical Details

- **Server Port:** `8080`
- **Server Context Path:** `/api`
- **Database Configuration:**
  - **Username:** `postgres`
  - **Password:** `root`
  - **URL:** `jdbc:postgresql://localhost:5432/vodafone_travel_assistant?currentSchema=vodafone_travel_assistant`
- **Swagger UI Path:** `/api-docs`

---

### Dependencies

- **Spring Boot:** `3.3.0`
- **Spring Boot Starter Web**
- **Spring Boot Starter Data JDBC**
- **Spring Boot Starter Data JPA**
- **Spring Cloud Starter OpenFeign**
- **Spring Boot Starter Cache**
- **Spring Boot Starter Security**
- **Spring Boot DevTools**
- **Spring Boot Test**
- **Spring Security Test**
- **Lombok**
- **JUnit Jupiter**
- **Mockito Core**
- **H2 Database**
- **SpringDoc OpenAPI Starter WebMvc UI**

---

This documentation provides an overview of the API endpoints available in the **TravelApp** backend application, along with technical details and dependencies used. Adjustments may be required based on specific deployment configurations and environment setups.