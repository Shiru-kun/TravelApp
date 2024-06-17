import { useQuery } from "react-query";
import { getWorldBankGpdByCountryCode, getWorldBankPopulationByCountryCode } from "../functions/worldbank";
import { COUNTRY_ABBREVIATION } from "../mocks/country_by_abbreviation";
import { COUNTRY_CURRENCY_CODE } from "../mocks/country_by_currency_code";

export const useWorldBankPopulationQuery = (countryCode: string, isEnabled: boolean) =>
  useQuery(
    ["WorldBankPopulation"],
    () => getWorldBankPopulationByCountryCode(countryCode),
    {
      enabled: isEnabled
    }
  );

export const useWorldBankGpdQuery = (countryCode: string, isEnabled: boolean) =>
  useQuery(
    ["WorldBankGpd"],
    () => getWorldBankGpdByCountryCode(countryCode),
    {
      enabled: isEnabled
    }
  );

  export const getCurrencyByCountryCode = (countryCode:string)=>{
        const country = COUNTRY_ABBREVIATION.find(ca=> ca.abbreviation.includes(countryCode))
      if(!country) return;
      const countryCurrency = COUNTRY_CURRENCY_CODE.find(cc=> cc.country.includes(country.country))
      if(countryCurrency) return countryCurrency.currency_code;
  }