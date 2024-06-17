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
const DataGraph = ({ label, color,data }: {data?:any, label: string,color?:string }) => {
  const [graphData, setGraphData]= useState<any[]>([])
  const [labels, setLabels]= useState<number[]>([])
  useEffect(()=>{
    try{
      var mappedData = data?.data?.map((d: { date: any; value: any; })=>{
        return { year: d.date, value:d.value }
     });
     mappedData = mappedData.sort((a: { year: string; }, b: { year: string; }) => parseInt(a.year) - parseInt(b.year));
     const _labels: number[] = mappedData.map((d: { year: string; }) => parseInt(d.year));
     const _data: number[] = mappedData.map((d: { value: any; }) => d?.value??0);
     setLabels(_labels)
     setGraphData(_data);

    }catch(ex){
        //TODO HANDLE
    }
     
  },[])
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
                text: 'time',
                color: 'black'
            }
        },
        y: {
            title: {
                display: true,
                text: 'value',
                color: 'black'
            }
        }
    }
};
  return ( 
    <div className={styles.tabcontent}>
      <div style={{ width: '100%', height: '300px' }}>
        {graphData && graphData.length>0?
        <>
         <Line options={options} data={{
          labels: labels, datasets: [{
            label: `Indicator ${label}`,
            data: graphData,
            borderColor: color ?? "Red",
            backgroundColor: "white",
          }]
        }} />
        </>
        :<>  <div className={`${styles.blurText}`} style={{ width: '100%', height: '300px' }}> <div className='loader'/> </div>
</>}
       
      </div></div>

   

  );
};

export default DataGraph;