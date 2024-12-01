import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

interface CategoryProductPieChartProps {
  month: string;
  year: string;
  category: string;
}

const CategoryProductPieChart: React.FC<CategoryProductPieChartProps> = ({ month, year, category }) => {
  const [graphData, setGraphData] = useState<PieChartData[]>([]);

  useEffect(() => {
    axios.get('/category/product/piechart', {
      params: { month, year, category },
    })
    .then(response => {
      const responseData = response.data.data as any[];
      const mappedData: PieChartData[] = responseData.map(item => ({
        name: item.category,
        y: item.percentage,
      }));
      setGraphData(mappedData);
    })
    .catch(error => {
      console.error('Error fetching category product percentage data:', error);
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

export default CategoryProductPieChart;
