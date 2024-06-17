import React, { useEffect } from 'react';
import styles from '../styles/pages/home/home.module.scss';
import WeatherCard from './WeatherCard';
import { MapContainer, TileLayer,Marker } from 'react-leaflet'
import { useWeatherQuery } from '../services/weather-service';

const WeatherTab = ({searchTerm,setCountryCode}:{searchTerm:string, setCountryCode: React.Dispatch<React.SetStateAction<string>>}) => {
  const sendRequest:boolean = searchTerm?true:false;
  const {data} = useWeatherQuery(searchTerm,sendRequest);
  useEffect(()=>{
    if(data?.sys?.country){
      setCountryCode(data?.sys?.country)
    }
  },[])
  
  return (
    <div className={styles.tabContent}>
      {data?<>
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
      <div className={styles.cardsContainer}>
        <div className={`${styles.card} ${styles.blurText}`}></div>
        <div className={`${styles.card} ${styles.blurText}`}></div>
        <div className={`${styles.card} ${styles.blurText}`}></div>
      </div>

      </>}
   
    </div>
  );
};

export default WeatherTab;