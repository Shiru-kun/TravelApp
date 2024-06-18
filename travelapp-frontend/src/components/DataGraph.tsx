import styles from '../styles/pages/home/home.module.scss';
import { Line } from 'react-chartjs-2';
import {
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  Title,
  Tooltip,
} from "chart.js"
import { useTranslation } from 'react-i18next';
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);
let graphData: any[] = []
let labels: number[] = []

type Props = {
  data?: any,
  label: string,
  color?: string
};
const DataGraph = ({ label, color, data }: Props) => {
  const { t } = useTranslation();

  try {
    var mappedData = data?.data?.map((d: { date: any; value: any; }) => {
      return { year: d.date, value: d.value }
    });
    mappedData = mappedData?.sort((a: { year: string; }, b: { year: string; }) => parseInt(a.year) - parseInt(b.year));
    labels = mappedData?.map((d: { year: string; }) => parseInt(d.year));
    graphData = mappedData?.map((d: { value: any; }) => d?.value ?? 0)

  } catch (ex) {
    console.log({ ex })
  }

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "bottom" as const,
      },
      title: {
        display: true,
        text: label,
      },
    },
    scales: {
      x: {
        title: {
          display: true,
          text: t('Time'),
          color: 'black'
        }
      },
      y: {
        title: {
          display: true,
          text: t('Value'),
          color: 'black'
        }
      }
    }
  };
  return (
    <div className={styles.tabcontent}>
      <div style={{ width: '100%', height: '300px' }}>
        {graphData && graphData.length > 0 ?
          <>
            <Line options={options} data={{
              labels: labels, datasets: [{
                label: `${t('Indicator')} ${label}`,
                data: graphData,
                borderColor: color ?? "Red",
                backgroundColor: "white",
              }]
            }} />
          </>
          : <>  <div className={`${styles.blurText}`} style={{ width: '100%', height: '300px' }}> </div>
          </>}

      </div>
    </div>



  );
};

export default DataGraph;