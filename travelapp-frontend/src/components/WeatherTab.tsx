import React, { useEffect } from 'react';
import styles from '../styles/pages/home/home.module.scss';
import WeatherCard from './WeatherCard';
import { MapContainer, Marker, Popup, TileLayer} from 'react-leaflet'
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
       <div id="map">
        <MapContainer  center={[data?.coord?.lat??0, data?.coord?.lon??0]} zoom={5} scrollWheelZoom={false}>
    <Marker position={[data?.coord?.lat??0, data?.coord?.lon??0]}>
      <Popup>
        A pretty CSS3 popup. <br /> Easily customizable.
      </Popup>
    </Marker>
      </MapContainer>
       </div>
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