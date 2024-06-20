import styles from '../styles/pages/home/home.module.scss';
import { useExchangeRateQuery } from '../services/exchange-rate-service';
import { getCurrencyByCountryCode } from '../services/worldbank-service';
import { useTranslation } from 'react-i18next';

type Props = { countryCode: string };
const ExchangeRateTab = ({ countryCode }: Props) => {
  const _countryCode = getCurrencyByCountryCode(countryCode);
  const { t } = useTranslation();

  const sendRequest: boolean = _countryCode ? true : false;
  const { data } = useExchangeRateQuery(_countryCode ?? countryCode, sendRequest);
  return (
    <div className={styles.tabContent}>
      <div className={styles.cardsContainer}>

        <div className={styles.card}>
          <h2>{t('ExchangeRate')}</h2>
          {data?.success ? (
            <span> 1 {data?.base} =  {_countryCode} {data?.rates?.[`${_countryCode}`]?.toFixed(2)}</span>
          ) : (
            <div className={`${styles.blurText} ${styles.tabContent}`} style={{ margin: 10 }}> </div>
          )}
        </div>
      </div>
    </div>

  );
};

export default ExchangeRateTab;
