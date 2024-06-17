import React, { useEffect, useState } from 'react';
import styles from '../styles/pages/home/home.module.scss';
import { GraphData } from '../mocks/population';
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
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
);
const DataGraph = ({ label }: { label: string }) => {
  const [graphData, setGraphData]= useState<any>([])
  const [labels, setLabels]= useState<number[]>([])
  const data = GraphData;
  useEffect(()=>{
      var mappedData = data.data.map(d=>{
         return { year: d.date, value:d.value }
      });
      mappedData = mappedData.sort((a, b) => parseInt(a.year) - parseInt(b.year));
      const _labels: number[] = mappedData.map(d => parseInt(d.year));
      const _data: number[] = mappedData.map(d => d?.value??0);
      setLabels(_labels)
      setGraphData(_data);
  },[])
  const options = {
    responsive: true,
    plugins: {
        legend: {
            position: "top" as const,
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
                text: 'tempo',
                color: 'black'
            }
        },
        y: {
            title: {
                display: true,
                text: 'Valor',
                color: 'black'
            }
        }
    }
};
  return (
    <div className={styles.tabContent}>
    <div className={styles.card}>
    <Line options={options}  data={{labels:labels, datasets: [{
                     label: `Indicator ${label}`,
                     data:graphData ,
                     borderColor: "red",
                     backgroundColor: "white",
                     
                 }]}}/>

    </div>
    </div>
  );
};

export default DataGraph;
