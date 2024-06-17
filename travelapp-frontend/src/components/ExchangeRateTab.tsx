import styles from '../styles/pages/home/home.module.scss';
import { useExchangeRateQuery } from '../services/exchange-rate-service';
import { getCurrencyByCountryCode } from '../services/worldbank-service';

const ExchangeRateTab = ({countryCode }: {countryCode:string }) => {
  const _countryCode = getCurrencyByCountryCode(countryCode);
  
  const sendRequest:boolean = _countryCode?true:false;
  const {data} = useExchangeRateQuery(_countryCode??countryCode,sendRequest);
  return (
    <div className={styles.tabContent}>
    <div className={styles.card}>
      <h2>Exchange Rate</h2>
      {data?.success  ? (
        <p> {data?.base} = {data?.rates["MZN"]} {data?.rates[0]}</p>
      ) : (
        <div className={`${styles.blurText} ${styles.tabContent}`} style={{margin:10}}> <div className='loader'/> </div>
      )}
    </div>
    </div>
  );
};

export default ExchangeRateTab;
