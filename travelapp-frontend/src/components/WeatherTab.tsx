import React from 'react';
import styles from '../styles/pages/home/home.module.scss';
import WeatherCard from './WeatherCard';

const WeatherTab = () => {
  const weatherData = {
    coord: {
      lon: 32.5675,
      lat: -25.9662
    },
    weather: [
      {
        id: 801,
        main: "Clouds",
        description: "few clouds",
        icon: "02n"
      }
    ],
    main: {
      temp: 290.31,
      feels_like: 290.38,
      temp_min: 290.31,
      temp_max: 290.31,
      pressure: 1024,
      humidity: 88,
      sea_level: 0,
      grnd_level: 0
    },
    visibility: 10000,
    wind: {
      speed: 0.0,
      deg: 0,
      gust: 0.0
    },
    clouds: {
      all: 20
    },
    sys: {
      country: "MZ",
      sunrise: 1718512499,
      sunset: 1718550363
    },
    name: "Maputo"
  };
  return (
    <div className={styles.tabContent}>
       <WeatherCard data={weatherData} />
    </div>
  );
};

export default WeatherTab;