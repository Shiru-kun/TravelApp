export const BASE_URL = "http://localhost:8080";
export const AUTH_LOCALSTORAGE = "AUTH_USER";
export const API_VERSION_1_0 = "1.0";

export const LOGIN = `/auth/login`;
export const SIGN_UP = `/auth/signup`;

export const GET_WEATHER_BY_CITY = `/${API_VERSION_1_0}/weather/`;
export const GET_EXCHANGE_RATE_BY_CURRENCY = `/${API_VERSION_1_0}/exchange-rate/`;
export const GET_WORLD_BANK_GPD_BY_COUNTRY_CODE = `/${API_VERSION_1_0}/world-bank/gpd/`;
export const GET_WORLD_BANK_POPULATION_BY_COUNTRY_CODE = `/${API_VERSION_1_0}/world-bank/population/`;


export const LANGUAGES = [
    { label: "English", code: "en" },
    { label: "Portuguese", code: "pt" }
  ];