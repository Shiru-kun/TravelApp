import { WorldBankDataType } from "../types";
import { get } from "../utils/axios-config";
import {  GET_WORLD_BANK_GPD_BY_COUNTRY_CODE, GET_WORLD_BANK_POPULATION_BY_COUNTRY_CODE } from "../utils/constants";

export const getWorldBankGpdByCountryCode =async (countryCode:string) => await get<WorldBankDataType>(GET_WORLD_BANK_GPD_BY_COUNTRY_CODE.concat(countryCode))

export const getWorldBankPopulationByCountryCode =async (countryCode:string) => await get<WorldBankDataType>(GET_WORLD_BANK_POPULATION_BY_COUNTRY_CODE.concat(countryCode))
