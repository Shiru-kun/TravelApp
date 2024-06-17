import { ChangeEvent, useEffect, useState } from "react";
import Layout from "./layout";
import styles from '../styles/pages/home/home.module.scss';
import WeatherTab from "../components/WeatherTab";
import PopulationGpdTab from "../components/PopulationGpdTab";
import ExchangeRateTab from "../components/ExchangeRateTab";
import {  useNavigate } from 'react-router-dom'
import { AUTH_LOCALSTORAGE } from "../utils/constants";
import { useTranslation } from "react-i18next";

export default function Home() {

    const [activeTab, setActiveTab] = useState('weather');
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
  const _logout =(e:any)=>{
    localStorage.clear();
    window.location.reload();
  }

    return (
    <Layout>
   <div className={styles.container}>
        <h1 className={styles.title}>{t('TravelAppAssistant')}</h1>
        <div className={styles.auth}>
          {userLogged ? (
            <div style={{padding:5, margin:5}}>
              <p>{`${getGreeting()}, ${userLogged?.userAccountDto?.fullname}`}</p>
              <a onClick={_logout}>{t('SignOut')}</a>
            </div>
          ) : (
            <a onClick={login}>{t('GoToLogin')}</a>
          )}
        </div>
        <input
          type="text"
          placeholder={t('SearchForCity')}
          value={searchTerm}
          disabled={activeTab!=="weather"}
          onChange={handleChange}
          className={styles.search}
        />
        <div className={styles.tabs}>
          <button className={activeTab === 'weather' ? styles.active : ''} onClick={() => setActiveTab('weather')}>
           {t('Weather')}
          </button>
          <button disabled={!searchTerm} className={activeTab === 'exchangerate' ? styles.active : ''} onClick={() => setActiveTab('exchangerate')}>
          {t('ExchangeRate')}
          </button>
          <button disabled={!searchTerm} className={activeTab === 'gpdpopulation' ? styles.active : ''} onClick={() => setActiveTab('gpdpopulation')}>
          {t('PopulationAndGpd')}
          </button>
        </div>
        <div className={styles.content}>
          {activeTab === 'weather' && <WeatherTab searchTerm={searchTerm} setCountryCode={setCountryCode}/>}
          {activeTab === 'exchangerate' && <ExchangeRateTab countryCode={countryCode} />}
          {activeTab === 'gpdpopulation' && <PopulationGpdTab countryCode={countryCode}/>}
        </div>
      </div>
            
        </Layout>    
    )
  }