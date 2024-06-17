import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import { WeatherDataType } from '../types';
import { convertToCelsius } from '../functions/weather';

const WeatherCard =({ data }: { data?: WeatherDataType }) => {
    const { weather, main, wind, clouds, sys, name } = data ||{};
    const weatherIconUrl = `http://openweathermap.org/img/wn/${weather?.[0]?.icon}.png`;

    return (
        <div className={styles.cardsContainer}>
          <div className={styles.card}>
            <div className={styles.cardHeader}>
              <h2>{convertToCelsius(main?.temp)} °C</h2>
              <img src={weatherIconUrl} loading="lazy" alt={weather?.[0]?.description} />
            </div>
            <div className={styles.cardContent}>
             <p><strong>Location:</strong>  {name??""}, {sys?.country} </p>
              <p><strong>Feels Like:</strong> {convertToCelsius(main?.feels_like)}°C</p>
              <p><strong>Min:</strong> {convertToCelsius(main?.temp_min)}°C</p>
              <p><strong>Max:</strong> {convertToCelsius(main?.temp_max)}°C</p>
            </div>
          </div>
    
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>Pressure:</strong> {main?.pressure} hPa</p>
              <p><strong>Humidity:</strong> {main?.humidity}%</p>
              <p><strong>Visibility:</strong> {data?.visibility??0 / 1000} km</p>
            <p><strong>Sunrise:</strong> {sys && sys.sunrise ? new Date(sys.sunrise * 1000).toLocaleTimeString() : 'N/A'}</p>
            <p><strong>Sunset:</strong> {sys && sys.sunset ? new Date(sys.sunset * 1000).toLocaleTimeString() : 'N/A'}</p>

            </div>
          </div>
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>Wind Speed:</strong> {wind?.speed} m/s</p>
              <p><strong>Wind Direction:</strong> {wind?.deg}°</p>
              <p><strong>Cloudiness:</strong> {clouds?.all}%</p>
              <p><strong>Weather:</strong> {weather?.[0]?.description}</p>
       
            </div>
          </div>
    
        
        </div>
      );
};

export default WeatherCard;