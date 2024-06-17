import { ChangeEvent, useEffect, useState } from "react";
import Layout from "./layout";
import styles from '../styles/pages/home/home.module.scss';
import WeatherTab from "../components/WeatherTab";
import PopulationGpdTab from "../components/PopulationGpdTab";
import ExchangeRateTab from "../components/ExchangeRateTab";
import { Authprovider, useAuth } from "./authentication/Authprovider";
import {  useNavigate } from 'react-router-dom'
import { AUTH_LOCALSTORAGE } from "../utils/constants";

export default function Home() {
  
    const [activeTab, setActiveTab] = useState('weather');
    const [searchTerm, setSearchTerm] = useState<string>('Maputo');
    const [countryCode, setCountryCode] = useState<string>('MZ');
    const [debouncedSearchTerm, setDebouncedSearchTerm] = useState<string>(searchTerm);
    const { isAuthenticated, user, logout } = useAuth() || {};
    const navigate = useNavigate()
    let userLogged:any = localStorage.getItem(AUTH_LOCALSTORAGE);
    if(userLogged){
      userLogged =JSON.parse(userLogged);
    }
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
  const _logout =(e:any)=>{
    if(logout){
      logout();
    }
    localStorage.clear();
    window.location.reload();
  }
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
          {userLogged ? (
            <div style={{padding:5, margin:5}}>
              <p>{`${getGreeting()}, ${userLogged?.userAccountDto?.fullname}`}</p>
              <a onClick={_logout}>sign out</a>
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