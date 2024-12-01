import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ReactApexChart from 'react-apexcharts';
import axios from 'axios';

interface SubcategoryProductGraphProps {
  month: string;
  year: string;
  subcategory: string;
  category: string;
}

interface GraphDataItem {
  x: string;
  y: number;
}

const SubcategoryProductGraph: React.FC<SubcategoryProductGraphProps> = ({ month, year, subcategory, category }) => {
  const [graphData, setGraphData] = useState<GraphDataItem[]>([]);

  useEffect(() => {
    axios.get('/subcategory/product', {
      params: { month, year, subcategory, category },
    })
    .then(response => {
      const responseData = response.data.data as any[];
      const mappedData: GraphDataItem[] = responseData.map(item => ({
        x: item.month,
        y: item.quantity,
      }));
      setGraphData(mappedData);
    })
    .catch(error => {
      console.error('Error fetching subcategory product data:', error);
    });
  }, [month, year, subcategory, category]);

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

export default SubcategoryProductGraph;
