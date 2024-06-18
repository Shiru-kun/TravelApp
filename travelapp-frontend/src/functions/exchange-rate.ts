import { ExchangeRateDataType } from "../types";
import { get } from "../utils/axios-config";
import { GET_EXCHANGE_RATE_BY_CURRENCY } from "../utils/constants";


export const getExchangeRateByCurrency =async (currencyCode:string) => await get<ExchangeRateDataType>(GET_EXCHANGE_RATE_BY_CURRENCY.concat(currencyCode))
