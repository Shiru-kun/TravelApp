
export type WeatherDataType = {
    coord: {
      lon: number;
      lat: number;
    };
    weather: Array<{
      id: number;
      main: string;
      description: string;
      icon: string;
    }>;
    main: {
      temp: number;
      feels_like: number;
      temp_min: number;
      temp_max: number;
      pressure: number;
      humidity: number;
      sea_level: number;
      grnd_level: number;
    };
    visibility: number;
    wind: {
      speed: number;
      deg: number;
      gust: number;
    };
    clouds: {
      all: number;
    };
    sys: {
      country: string;
      sunrise: number;
      sunset: number;
    };
    name: string;
  };
  export type ExchangeRateDataType = {
    success: boolean;
    timestamp: number;
    base: string;
    date: string;
    rates: Record<string, number>;
  };
  export type WorldBankDataType = {
    metadata: {
      page: number;
      pages: number;
      perPage: number;
      total: number;
      sourceid: string;
      lastupdated: string;
    };
    data: {
      indicator: {
        id: string;
        value: string;
      };
      country: {
        id: string;
        value: string;
      };
      countryiso3code: string;
      date: string;
      value: number | null;
      unit: string;
      obsStatus: any; 
      decimal: number;
    }[];
  };
  
  