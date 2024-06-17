import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import { WeatherDataType } from '../types';
import { convertToCelsius } from '../functions/weather';
import { useTranslation } from 'react-i18next';

const WeatherCard =({ data }: { data?: WeatherDataType }) => {
    const { weather, main, wind, clouds, sys, name } = data ||{};
    const weatherIconUrl = `http://openweathermap.org/img/wn/${weather?.[0]?.icon}.png`;
    const { t } = useTranslation();

    return (
        <div className={styles.cardsContainer}>
          <div className={styles.card}>
            <div className={styles.cardHeader}>
              <h2>{convertToCelsius(main?.temp)} °C</h2>
              <img src={weatherIconUrl} loading="lazy" alt={weather?.[0]?.description} />
            </div>
            <div className={styles.cardContent}>
             <p><strong>{t('Location')}:</strong>  {name??""}, {sys?.country} </p>
              <p><strong>{t('FeelsLike')}:</strong> {convertToCelsius(main?.feels_like)}°C</p>
              <p><strong> {t('Min')}:</strong> {convertToCelsius(main?.temp_min)}°C</p>
              <p><strong>{t('Max')}:</strong> {convertToCelsius(main?.temp_max)}°C</p>
            </div>
          </div>
    
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>{t('Pressure')}:</strong> {main?.pressure} hPa</p>
              <p><strong>{t('Humidity')}:</strong> {main?.humidity}%</p>
              <p><strong>{t('Visibility')}:</strong> {data?.visibility??0 / 1000} km</p>
            <p><strong>{t('Sunrise')}:</strong> {sys && sys.sunrise ? new Date(sys.sunrise * 1000).toLocaleTimeString() : 'N/A'}</p>
            <p><strong>{t('Sunset')}:</strong> {sys && sys.sunset ? new Date(sys.sunset * 1000).toLocaleTimeString() : 'N/A'}</p>

            </div>
          </div>
          <div className={styles.card}>
            <div className={styles.cardContent}>
              <p><strong>{t('WindSpeed')}:</strong> {wind?.speed} m/s</p>
              <p><strong>{t('WindDirection')}:</strong> {wind?.deg}°</p>
              <p><strong>{t('Cloudiness')}:</strong> {clouds?.all}%</p>
              <p><strong>{t('Weather')}:</strong> {weather?.[0]?.description}</p>
       
            </div>
          </div>
    
        
        </div>
      );
};

export default WeatherCard;