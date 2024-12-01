import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

interface TimeRangeGraphProps {
  startMonth: string;
  endMonth: string;
  year: string;
  subcategory: string;
  category: string;
}

interface TimeRangeDataItem {
  x: string;
  y: number;
}

const SubcategoryProductTimeRangeGraph: React.FC<TimeRangeGraphProps> = ({ startMonth, endMonth, year, subcategory, category }) => {
  const [graphData, setGraphData] = useState<TimeRangeDataItem[]>([]);

  useEffect(() => {
    axios.get('/subcategory/product/timerange', {
      params: { startMonth, endMonth, year, subcategory, category },
    })
    .then(response => {
      const responseData = response.data.data as any[];
      const mappedData: TimeRangeDataItem[] = responseData.map(item => ({
        x: item.month,
        y: item.quantity,
      }));
      setGraphData(mappedData);
    })
    .catch(error => {
      console.error('Error fetching subcategory product time range data:', error);
    });
  }, [startMonth, endMonth, year, subcategory, category]);

  const options = {
    chart: {
      type: 'line',
      zoom: {
        enabled: false,
      },
    },
    xaxis: {
      categories: graphData.map(item => item.x),
    },
  };

  const series = [{
    name: 'Quantity Sold',
    data: graphData.map(item => item.y),
  }];

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100%">
      <ReactApexChart
        options={options}
        series={series}
        type="line"
        width={356}
        height={240}
      />
    </Box>
  );
};

export default SubcategoryProductTimeRangeGraph;
