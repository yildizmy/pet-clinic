import {
  Box,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import Checkbox from "@mui/material/Checkbox";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import ListItemText from "@mui/material/ListItemText";
import MenuItem from "@mui/material/MenuItem";
import OutlinedInput from "@mui/material/OutlinedInput";
import Select from "@mui/material/Select";
import { useSnackbar } from "notistack";
import { useEffect, useState } from "react";
import Navbar from "../../components/navbar/Navbar";
import PieChart from "../../components/piechart/PieChart";
import Sidebar from "../../components/sidebar/Sidebar";
import HttpService from "../../services/HttpService";
import "./statistic.scss";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

const pieoptions = {
  title: "How many selected types of pets my clinic has registered?",
  is3D: true,
  height: 275,
  titleTextStyle: {
    fontSize: 16,
  },
};

const Statistic = () => {
  const pageTitle = "Statistics";
  const [data, setData] = useState();
  const [types, setTypes] = useState([]);
  const [values, setValues] = useState([]);
  const [chartData, setChartData] = useState([["Type", "Numbers per type"]]);
  const { enqueueSnackbar } = useSnackbar();

  useEffect(() => {
    const getTypes = async () => {
      const response = await HttpService.getWithAuth("/types");
      const types = await response.data.content;
      setTypes(types);
    };
    getTypes();
  }, []);

  useEffect(() => {
    const body = {
      ids:
        values.map((v) => {
          return v.id;
        }) || [],
    };

    HttpService.postWithAuth("/pets/types", body)
      .then((response) => {
        setData(response.data);
        setChartData([...chartData, ...Object.entries(response.data)]);
      })
      .catch((error) => {
        if (error.response.status === 404) {
          setData([]);
        } else if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) =>
            enqueueSnackbar(e.field + " " + e.message, { variant: "error" })
          );
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: "error" });
        } else {
          enqueueSnackbar(error.message, { variant: "error" });
        }
      });
  }, [values]);

  const handleChange = (event) => {
    setValues(event?.target?.value || []);
    setChartData([["Type", "Numbers per type"]]);
  };

  const renderValue = (selected) =>
    selected?.map(({ name }) => name)?.join(", ");

  const isChecked = (selectedId) => {
    return !!values?.find(({ id }) => id === selectedId);
  };

  return (
    <div className="single">
      <Sidebar />
      <div className="singleContainer">
        <Navbar />
        <div className="bottom">
          <h1 className="title">{pageTitle}</h1>
          <FormControl sx={{ ml: 0, mb: 2, width: 300 }}>
            <InputLabel id="demo-multiple-checkbox-label">Pet Type</InputLabel>
            <Select
              labelId="demo-multiple-checkbox-label"
              id="demo-multiple-checkbox"
              multiple
              value={values}
              onChange={handleChange}
              input={<OutlinedInput label="Tag" />}
              renderValue={renderValue}
              MenuProps={MenuProps}
            >
              {types.map((type) => (
                <MenuItem key={type.id} value={type}>
                  <Checkbox checked={isChecked(type.id)} />
                  <ListItemText primary={type.name} />
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Grid container spacing={2}>
            <Grid item sm={6}>
              <Box textAlign="left">
                <TableContainer component={Paper} className="table">
                  <Table sx={{ width: "100%" }} aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell className="tableCell">Type</TableCell>
                        <TableCell className="tableCell">Number</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {data &&
                        Object.entries(data).map(([k, v]) => (
                          <TableRow key={k}>
                            <TableCell className="tableCell">{k}</TableCell>
                            <TableCell className="tableCell">{v}</TableCell>
                          </TableRow>
                        ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </Box>
            </Grid>
            <Grid item sm={6}>
              <Box textAlign="center">
                <Grid container>
                  <Grid item={true}>
                  {chartData && <PieChart
                      data={chartData}
                      options={pieoptions}
                      height={"100%"}
                    />}
                  </Grid>
                </Grid>
              </Box>
            </Grid>
          </Grid>
        </div>
      </div>
    </div>
  );
};

export default Statistic;
