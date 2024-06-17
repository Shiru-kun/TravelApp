import React, { useEffect } from 'react';
import styles from '../styles/pages/home/home.module.scss';
import WeatherCard from './WeatherCard';
import { MapContainer, TileLayer,Marker } from 'react-leaflet'
import { useWeatherQuery } from '../services/weather-service';
import { QueryStatus } from 'react-query';
import { useTranslation } from 'react-i18next';

const WeatherTab = ({searchTerm,setCountryCode}:{searchTerm:string, setCountryCode: React.Dispatch<React.SetStateAction<string>>}) => {
  const sendRequest:boolean = searchTerm?true:false;
  const { t } = useTranslation();

  const {data,status} = useWeatherQuery(searchTerm,sendRequest);
  useEffect(()=>{
    if (status.includes("success")) {
      if (data?.sys?.country) {
        setCountryCode(data?.sys?.country)
      }else{
        setCountryCode("")
      }
    }
   
  },[])
  
  const renderByStatus: {
    [Key in QueryStatus]: JSX.Element;
  } = {
    error: (
     <> <span>{t('OpsWeEncounteredAnErrorTryAgain')}</span></>
    ),
    loading: (
      <> <div className={styles.cardsContainer}>
      <div className={`${styles.card} ${styles.blurText}`}></div>
      <div className={`${styles.card} ${styles.blurText}`}></div>
      <div className={`${styles.card} ${styles.blurText}`}></div>
    </div></>
    ),
    success: (
      <>  {data?<>
      <WeatherCard data={data} />
      <MapContainer  center={[data?.coord?.lat, data?.coord?.lon]} zoom={13} scrollWheelZoom={false}>
      <TileLayer
      attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
    />
          <Marker position={[data?.coord?.lat, data?.coord?.lon]}>
          </Marker>
      </MapContainer>
      </>:<>
      <span className={styles.active}>{t('NoDataFound')} {searchTerm}</span>
      </>}</>
    ),
    idle: (
      <><div className='loader'></div></>
    ),
  };
  return (
    <div className={styles.tabContent}>
        {renderByStatus[status]}
    </div>
  );
};

export default WeatherTab;