import { useQuery } from "react-query";
import { getWeatherByCity } from "../functions/weather";

export const useWeatherQuery= (city:string,isEnabled:boolean)=>
    useQuery(
        ["weather"],
        () => getWeatherByCity(city),
        {
          enabled:isEnabled
        }
      );