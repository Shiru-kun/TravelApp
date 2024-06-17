import { useState } from "react";
import Layout from "./layout";
import styles from '../styles/pages/home/home.module.scss';
import WeatherTab from "../components/WeatherTab";
import PopulationGpdTab from "../components/PopulationGpdTab";
import ExchangeRateTab from "../components/ExchangeRateTab";
import { ExchangeRateDataType } from "../types";

export default function Home() {
    const [activeTab, setActiveTab] = useState('weather');
    const [searchTerm, setSearchTerm] = useState('');
    const exchangerate:ExchangeRateDataType = {
        "success": true,
        "timestamp": 1718574003,
        "base": "EUR",
        "date": "2024-06-16",
        "rates": {
            "MZN": 68.196682
        }
    }
    return (
        
        <Layout>
   <div className={styles.container}>
        <h1 className={styles.title}>Travel app assistant</h1>
        <input
          type="text"
          placeholder="Search for a city..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className={styles.search}
        />
        <div className={styles.tabs}>
          <button className={activeTab === 'weather' ? styles.active : ''} onClick={() => setActiveTab('weather')}>
            Weather
          </button>
          <button className={activeTab === 'exchangerate' ? styles.active : ''} onClick={() => setActiveTab('exchangerate')}>
          Exchange rate 
          </button>
          <button className={activeTab === 'gpdpopulation' ? styles.active : ''} onClick={() => setActiveTab('gpdpopulation')}>
          Population and Gpd
          </button>
        </div>
        <div className={styles.content}>
          {activeTab === 'weather' && <WeatherTab />}
          {activeTab === 'exchangerate' && <ExchangeRateTab data={exchangerate} />}
          {activeTab === 'gpdpopulation' && <PopulationGpdTab />}
        </div>
      </div>
            
        </Layout>
    )
  }