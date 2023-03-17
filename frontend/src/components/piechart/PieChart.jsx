import React from "react";
import { Chart } from "react-google-charts";

const PieChart = ({ data, options, width, height }) => {
  return (
    <Chart
      chartType="PieChart"
      data={data}
      options={options}
      width={width}
      height={height}
    />
  );
};

export default PieChart;
