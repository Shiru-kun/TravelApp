import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import { ExchangeRateDataType} from '../types';

const ExchangeRateTab = ({ data }: { data: ExchangeRateDataType }) => {
  return (
    <div className={styles.tabContent}>
    <div className={styles.card}>
      <h2>Exchange Rate</h2>
      {data.success  ? (
        <p> {data?.base} = {data?.rates["MZN"]} {data?.rates[0]}</p>
      ) : (
        <div className={styles.blurText}>Loading exchange rate...</div>
      )}
    </div>
    </div>
  );
};

export default ExchangeRateTab;
