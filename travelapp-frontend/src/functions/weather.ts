import { WeatherDataType } from "../types";
import { get } from "../utils/axios-config";
import {  GET_WEATHER_BY_CITY} from "../utils/constants";


export const getWeatherByCity =async (city:string) => await get<WeatherDataType>(GET_WEATHER_BY_CITY+city)
  
export const convertToCelsius = (value?: string | number) => {
    if (value === undefined || value === null) return 0;
    const numericValue = typeof value === 'string' ? parseFloat(value) : value;
    if (isNaN(numericValue)) return 0;
    return (numericValue as number - 273.15).toFixed(0);
  };
  