import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

interface CategoryBrandPieChartProps {
  month: string;
  year: string;
  category: string;
}

interface PieChartData {
  name: string;
  y: number;
}

const CategoryBrandPieChart: React.FC<CategoryBrandPieChartProps> = ({ month, year, category }) => {
  const [graphData, setGraphData] = useState<PieChartData[]>([]);

  useEffect(() => {
    axios.get('/category/brand/piechart', {
      params: { month, year, category },
    })
    .then(response => {
      const responseData = response.data.data as any[];
      const mappedData: PieChartData[] = responseData.map(item => ({
        name: item.brand,
        y: item.percentage,
      }));
      setGraphData(mappedData);
    })
    .catch(error => {
      console.error('Error fetching category brand percentage data:', error);
    });
  }, [month, year, category]);

  const options = {
    chart: {
      type: 'pie',
    },
    labels: graphData.map(item => item.name),
  };

  const series = graphData.map(item => item.y);

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100%">
      <ReactApexChart
        options={options}
        series={series}
        type="pie"
        width={356}
        height={240}
      />
    </Box>
  );
};

export default CategoryBrandPieChart;
