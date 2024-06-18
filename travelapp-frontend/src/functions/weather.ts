import { WeatherDataType } from "../types";
import { get } from "../utils/axios-config";
import {  GET_WEATHER_BY_CITY} from "../utils/constants";


export const getWeatherByCity =async (city:string) => await get<WeatherDataType>(GET_WEATHER_BY_CITY.concat(city))
  
export const convertToCelsius = (value?: string | number) => {
    if (value === undefined || value === null) return 0;
    const _value = typeof value === 'string' ? parseFloat(value) : value;
    if (isNaN(_value)) return 0;
    return (_value as number - 273.15).toFixed(0);
  };
  
    
export const getTimeFromTimestamp = (value?:number) => {
  if (value === undefined || value === null) return 0;
  return new Date(value * 1000).toLocaleTimeString()
   
};