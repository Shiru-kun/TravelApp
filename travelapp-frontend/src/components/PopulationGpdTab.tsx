import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import DataGraph from './DataGraph';

const PopulationGpdTab = () => {
  return (
    <div className={styles.tabContent}>
      <div className={styles.cardsContainer}>
        <DataGraph label='GPD' />
        <DataGraph label='Population' />
      </div>
    </div>
  );
};

export default PopulationGpdTab;
