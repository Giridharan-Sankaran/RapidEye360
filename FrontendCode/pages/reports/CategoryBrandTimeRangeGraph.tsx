import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

interface BrandTimeRangeGraphProps {
  startMonth: string;
  endMonth: string;
  year: string;
  category: string;
  brand: string;
}

const CategoryBrandTimeRangeGraph: React.FC<BrandTimeRangeGraphProps> = ({ startMonth, endMonth, year, category, brand }) => {
  const [graphData, setGraphData] = useState<TimeRangeDataItem[]>([]);

  useEffect(() => {
    axios.get('/category/brand/timerange', {
      params: { startMonth, endMonth, year, category, brand },
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
      console.error('Error fetching category brand time range data:', error);
    });
  }, [startMonth, endMonth, year, category, brand]);

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

export default CategoryBrandTimeRangeGraph;
