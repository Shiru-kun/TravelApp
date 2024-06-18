import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import DataGraph from './DataGraph';
import { useWorldBankGpdQuery, useWorldBankPopulationQuery } from '../services/worldbank-service';
import { useTranslation } from 'react-i18next';

type Props = {
  countryCode:string
}; 
const PopulationGpdTab = ({countryCode}:Props) => {
  const sendRequest:boolean = countryCode?true:false;
  const queryDataGpd = useWorldBankGpdQuery(countryCode,sendRequest);
  const queryDataPopulation = useWorldBankPopulationQuery(countryCode,sendRequest);
  const { t } = useTranslation();

  return (
    <div className={styles.container}>
        <DataGraph data={queryDataGpd?.data} label={t('GPD')} color='red' />
        <DataGraph  data={queryDataPopulation?.data} label={t('Population')} color='blue'/>
    </div>
  );
};

export default PopulationGpdTab;
