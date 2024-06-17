import { ChangeEvent, useEffect, useState } from "react";
import Layout from "./layout";
import styles from '../styles/pages/home/home.module.scss';
import WeatherTab from "../components/WeatherTab";
import PopulationGpdTab from "../components/PopulationGpdTab";
import ExchangeRateTab from "../components/ExchangeRateTab";
import { ExchangeRateDataType } from "../types";
import { Authprovider, useAuth } from "./authentication/Authprovider";
import { useLocation, useNavigate } from 'react-router-dom'

export default function Home() {
  
    const [activeTab, setActiveTab] = useState('weather');
    const [searchTerm, setSearchTerm] = useState<string>('Maputo');
    const [countryCode, setCountryCode] = useState<string>('MZ');
    const [debouncedSearchTerm, setDebouncedSearchTerm] = useState<string>(searchTerm);
    const { isAuthenticated, user, logout } = useAuth() || {};
    const navigate = useNavigate()

  const handleChange =(event: ChangeEvent<HTMLInputElement>): void=>  {
    const _searchTerm =event.target.value;    
    setSearchTerm(_searchTerm)
  }
  const login = ()=>{

    navigate("login");
  }
  const getGreeting = () => {
    const hour = new Date().getHours();
    if (hour < 12) return "Good Morning";
    if (hour < 18) return "Good Afternoon";
    return "Good Night";
  };
  useEffect(() => {
    const handler = setTimeout(() => {
        setDebouncedSearchTerm(searchTerm);
    }, 700);

    return () => {
        clearTimeout(handler);
    };
}, [searchTerm]);
    return (
      <Authprovider>
    <Layout>
   <div className={styles.container}>
        <h1 className={styles.title}>Travel app assistant</h1>
        <div className={styles.auth}>
          {isAuthenticated ? (
            <div>
              <p>{`${getGreeting()}, ${user?.name}`}</p>
              <button onClick={logout}>Sair</button>
            </div>
          ) : (
            <a onClick={login}>Go to login</a>
          )}
        </div>
        <input
          type="text"
          placeholder="Search for a city..."
          value={searchTerm}
          onChange={handleChange}
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
          {activeTab === 'weather' && <WeatherTab searchTerm={searchTerm} setCountryCode={setCountryCode}/>}
          {activeTab === 'exchangerate' && <ExchangeRateTab countryCode={countryCode} />}
          {activeTab === 'gpdpopulation' && <PopulationGpdTab countryCode={countryCode}/>}
        </div>
      </div>
            
        </Layout>
      </Authprovider>
    
    )
  }