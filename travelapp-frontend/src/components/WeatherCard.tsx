import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import { WeatherData } from '../types';

const WeatherCard =({ data }: { data: WeatherData }) => {
    const { weather, main, wind, clouds, sys, name } = data;
    const weatherIconUrl = `http://openweathermap.org/img/wn/${weather[0].icon}.png`;

    return (
        <div className={styles.cardsContainer}>
          <div className={styles.card}>
            <div className={styles.cardHeader}>
              <h2>{name}, {sys.country}</h2>
              <img src={weatherIconUrl} alt={weather[0].description} />
            </div>
            <div className={styles.cardContent}>
              <p><strong>Temperature:</strong> {(main.temp - 273.15).toFixed(1)}°C</p>
              <p><strong>Feels Like:</strong> {(main.feels_like - 273.15).toFixed(1)}°C</p>
              <p><strong>Min Temperature:</strong> {(main.temp_min - 273.15).toFixed(1)}°C</p>
              <p><strong>Max Temperature:</strong> {(main.temp_max - 273.15).toFixed(1)}°C</p>
            </div>
          </div>
    
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>Pressure:</strong> {main.pressure} hPa</p>
              <p><strong>Humidity:</strong> {main.humidity}%</p>
              <p><strong>Visibility:</strong> {data.visibility / 1000} km</p>
            </div>
          </div>
    
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>Wind Speed:</strong> {wind.speed} m/s</p>
              <p><strong>Wind Direction:</strong> {wind.deg}°</p>
              <p><strong>Cloudiness:</strong> {clouds.all}%</p>
            </div>
          </div>
    
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>Weather:</strong> {weather[0].description}</p>
              <p><strong>Sunrise:</strong> {new Date(sys.sunrise * 1000).toLocaleTimeString()}</p>
              <p><strong>Sunset:</strong> {new Date(sys.sunset * 1000).toLocaleTimeString()}</p>
            </div>
          </div>
        </div>
      );
};

export default WeatherCard;