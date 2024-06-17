import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import DataGraph from './DataGraph';
import { useWorldBankGpdQuery } from '../services/worldbank-service';

const PopulationGpdTab = ({countryCode}:{countryCode:string}) => {
  const sendRequest:boolean = countryCode?true:false;
  const queryDataGpd = useWorldBankGpdQuery(countryCode,sendRequest);
  const queryDataPopulation = useWorldBankGpdQuery(countryCode,sendRequest);
  return (
    <div className={styles.container}>
        <DataGraph data={queryDataGpd?.data} label='GPD' color='red' />
        <DataGraph  data={queryDataPopulation?.data} label='Population' color='blue'/>
    </div>
  );
};

export default PopulationGpdTab;
