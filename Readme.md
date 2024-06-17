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
- **Swagger UI endpoint:** `http://localhost:8080/swagger-ui/index.html#/`

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

### TravelApp Assistant Frontend Documentation

#### Table of Contents

1. [Introduction](#introduction)
2. [Installation](#installation)
3. [Project Structure](#project-structure)
4. [Configuration](#configuration)
5. [Running the Application](#running-the-application)
6. [Main Components](#main-components)
    - [Layout](#layout)
    - [Home](#home)
    - [Login](#login)
    - [Signup](#signup)
7. [Authentication](#authentication)
8. [Internationalization](#internationalization)
9. [Dependencies](#dependencies)

### Introduction

The "TravelApp Assistant" project is a frontend application developed in React with TypeScript. This application provides information on weather, exchange rates, and population and GDP data. It supports user authentication and is configured to support multiple languages using i18next.

### Installation

To install the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/Shiru-kun/TravelApp.git
   ```
2. Navigate to the project directory:
   ```bash
   cd travelapp-frontend
   ```
3. Install the dependencies:
   ```bash
   npm install
   ```

### Project Structure

The project structure is organized as follows:

```
travelapp-frontend/
├── public/
│   └── index.html
├── src/
│   ├── assets/
├── |── components/
├── |── functions/
├── |── mocks/
├── |── services/
├── |── translations/
│   ├── pages/
│   │   ├── authentication/
│   │   │   ├── Authprovider.tsx
│   │   │   ├── Login.tsx
│   │   │   ├── Signup.tsx
│   │   ├── Layout.tsx
│   │   ├── Home.tsx
│   ├── styles/
│   │   ├── pages/
│   │   │   ├── home/
│   │   │   ├── authentication/
│   ├── utils/
│   │   ├── axios-config.ts
│   │   ├── constants.ts
│   ├── i18n.ts
│   ├── index.tsx
│   ├── App.tsx
├── package.json
├── tsconfig.json
```

### Running the Application

To run the application locally, use the following command:

```bash
npm start
```

The application will be available at `http://localhost:3000`.

### Main Components

#### Layout

The `Layout` component is responsible for rendering the basic structure of the application, including the main styling.

```tsx
import { ReactNode } from 'react';
import styles from '../styles/pages/home/home.module.scss';

type Props = {
  children: ReactNode;
};

export default function Layout({ children }: Props) {
  return (
    <div className={styles.layout}>
      {children}
    </div>
  ); 
}
```

#### Home

The `Home` component manages the different tabs and the user's authentication state.

```tsx
import { ChangeEvent, useEffect, useState } from "react";
import Layout from "./layout";
import styles from '../styles/pages/home/home.module.scss';
import WeatherTab from "../components/WeatherTab";
import PopulationGpdTab from "../components/PopulationGpdTab";
import ExchangeRateTab from "../components/ExchangeRateTab";
import { Authprovider, useAuth } from "./authentication/Authprovider";
import { useNavigate } from 'react-router-dom';
import { AUTH_LOCALSTORAGE } from "../utils/constants";
import { useTranslation } from "react-i18next";

export default function Home() {
    ...
}
```

#### Login

The `Login` component allows users to authenticate into the application.

```tsx
import React, { useState } from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';
import { post } from '../../utils/axios-config';
import { AUTH_LOCALSTORAGE, LANGUAGES, LOGIN } from '../../utils/constants';
import { useAuth } from './Authprovider';
import toast, { Toaster } from 'react-hot-toast';
import { useTranslation } from 'react-i18next';

const Login = () => {
    ...
}
```

#### Signup

The `Signup` component allows users to register into the application.

```tsx
import React, { useState } from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';
import { post } from '../../utils/axios-config';
import { LANGUAGES, SIGN_UP } from '../../utils/constants';
import toast, { Toaster } from 'react-hot-toast';
import { useTranslation } from 'react-i18next';

export default function Signup() {
    ...
}
```

### Authentication

Authentication is managed by the `Authprovider` context, which provides methods for login and logout and maintains the user's authentication state.

```tsx
import { createContext, useContext, useState, ReactNode } from 'react';

interface AuthContextType {
  isAuthenticated: boolean;
  user: { name: string } | null;
  login: (name: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const Authprovider = ({ children }: { children: ReactNode }) => {
    ...
};

export const useAuth = () => {
    ...
};
```

### Internationalization

The application uses i18next for multilingual support. The i18next configuration is located in the `i18n.ts` file.

```tsx
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import en from './translations/en.ts';
import pt from './translations/pt.ts';

const resources = {
  en,pt
};

i18n
  .use(initReactI18next)
  .init({
    resources,
    lng: 'en', // default language
    fallbackLng: 'en',
    interpolation: {
      escapeValue: false
    }
  });

export default i18n;
```

### Testing

The tests are configured using Jest and React Testing Library. Test files are located in the `__tests__` folder.

#### Unit and Integration Tests

Example test for the `Layout` component:

```tsx
// __tests__/Layout.test.tsx
import React from 'react';
import { render } from '@testing-library/react';
import Layout from '../components/Layout';

test('renders children correctly', () => {
  const { getByText } = render(<Layout><div>Test Child</div></Layout>);
  expect(getByText('Test Child')).toBeInTheDocument();
});
```

### Available Scripts

- `npm start`: Starts the application in development mode.
- `npm build`: Builds the application for production.
- `npm test`: Runs tests using Jest.

### Dependencies

- **React**: Main library for building the UI.
- **React Router**: Routing management.
- **i18next**: Internationalization.
- **Axios**: HTTP requests.
- **Chartsjs**: Display graphical data info about population and gpd.
- **Sass**: Styling.
- **Toaster**: Styled notifications toasts.


### Conclusion

This documentation provides an overview of how to set up, run, and test the "TravelApp Assistant" application. For more details on each component and functionality, refer to the source files and comments within the code.