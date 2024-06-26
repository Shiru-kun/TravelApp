import { useQuery } from "react-query";
import { getExchangeRateByCurrency } from "../functions/exchange-rate";

export const useExchangeRateQuery= (currencyCode:string,isEnabled:boolean)=>
    useQuery(
        ["ExchangeRate",currencyCode],
        () => getExchangeRateByCurrency(currencyCode),
        {
          enabled:isEnabled
        }
      );