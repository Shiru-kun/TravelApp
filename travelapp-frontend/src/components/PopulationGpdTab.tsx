import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import DataGraph from './DataGraph';

const PopulationGpdTab = () => {
  return (
    <div className={styles.container}>
        <DataGraph label='GPD' color='red' />
        <DataGraph label='Population' color='blue'/>
    </div>
  );
};

export default PopulationGpdTab;
