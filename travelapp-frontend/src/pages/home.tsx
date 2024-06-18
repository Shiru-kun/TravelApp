import { ChangeEvent, useEffect, useState } from "react";
import Layout from "./layout";
import styles from '../styles/pages/home/home.module.scss';
import WeatherTab from "../components/WeatherTab";
import PopulationGpdTab from "../components/PopulationGpdTab";
import ExchangeRateTab from "../components/ExchangeRateTab";
import {  useNavigate } from 'react-router-dom'
import { AUTH_LOCALSTORAGE } from "../utils/constants";
import { useTranslation } from "react-i18next";

const Home=()=> {

    const [selectedTab, setSelectedTab] = useState('weather');
    const [searchTerm, setSearchTerm] = useState<string>('Maputo');
    const [countryCode, setCountryCode] = useState<string>('MZ');
    const navigate = useNavigate()
    const { t } = useTranslation();

    let userLogged:any = localStorage.getItem(AUTH_LOCALSTORAGE);
    if(userLogged){
      userLogged =JSON.parse(userLogged);
    }
  const handleChange =(event: ChangeEvent<HTMLInputElement>): void=>  {
    setSearchTerm(prev=>event.target.value)
  }
  const login = ()=>{
    navigate("login");
  }
  const getGreeting = () => {
    const hour = new Date().getHours();
    if (hour < 12) return t('GoodMorning');
    if (hour < 18) return t('GoodAfternoon');
    return t('GoodNight');
  };
  const logout =(e:any)=>{
    localStorage.clear();
    login();
  }

    return (
    <Layout>
   <div className={styles.container}>
        <h1 className={styles.title}>{t('TravelAppAssistant')}</h1>
        <div className={styles.auth}>
          {userLogged ? (
            <div style={{padding:5, margin:5}}>
              <p>{`${getGreeting()}, ${userLogged?.userAccountDto?.fullname}`}</p>
              <a onClick={logout}>{t('SignOut')}</a>
            </div>
          ) : (
            <a onClick={login}>{t('GoToLogin')}</a>
          )}
        </div>
        <input
          type="text"
          placeholder={t('SearchForCity')}
          value={searchTerm}
          disabled={selectedTab!=="weather"}
          onChange={handleChange}
          className={styles.search}
        />
        <div className={styles.tabs}>
          <button className={selectedTab === 'weather' ? styles.active : ''} onClick={() => setSelectedTab('weather')}>
           {t('Weather')}
          </button>
          <button disabled={!searchTerm} className={selectedTab === 'exchangerate' ? styles.active : ''} onClick={() => setSelectedTab('exchangerate')}>
          {t('ExchangeRate')}
          </button>
          <button disabled={!searchTerm} className={selectedTab === 'gpdpopulation' ? styles.active : ''} onClick={() => setSelectedTab('gpdpopulation')}>
          {t('PopulationAndGpd')}
          </button>
        </div>
        <div className={styles.content}>
          {selectedTab === 'weather' && <WeatherTab searchTerm={searchTerm} setCountryCode={setCountryCode}/>}
          {selectedTab === 'exchangerate' && <ExchangeRateTab countryCode={countryCode} />}
          {selectedTab === 'gpdpopulation' && <PopulationGpdTab countryCode={countryCode}/>}
        </div>
      </div>
            
        </Layout>    
    )
  }
  export default Home;
