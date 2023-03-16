import {
  Button,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { useSnackbar } from "notistack";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import HttpService from "../../services/HttpService";
import "./pet.scss";

const EditPet = () => {
  const pageTitle = "Edit Pet";
  const { state } = useLocation();
  const defaultValues = {
    id: state.id,
    name: state.name,
    typeId: state.type.id,
    userId: state.user.id,
  };
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState(defaultValues);
  const [types, setTypes] = useState([]);

  useEffect(() => {
    const getTypes = async () => {
      const response = await HttpService.getWithAuth("/types");
      const types = await response.data.content;
      setTypes(types);
    };
    getTypes();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    HttpService.putWithAuth("/pets", formValues)
      .then((response) => {
        enqueueSnackbar("Pet updated successfully", { variant: "success" });
        navigate("/pets");
      })
      .catch((error) => {
        if (error.response?.data?.errors) {
          error.response?.data?.errors.map((e) =>
            enqueueSnackbar(e.field + " " + e.message, { variant: "error" })
          );
        } else if (error.response?.data?.message) {
          enqueueSnackbar(error.response?.data?.message, { variant: "error" });
        } else {
          enqueueSnackbar(error.message, { variant: "error" });
        }
      });
  };

  return (
    <div className="single">
      <Sidebar />
      <div className="singleContainer">
        <Navbar />
        <div className="bottom">
          <h1 className="title">{pageTitle}</h1>
          <form onSubmit={handleSubmit}>
            <Grid
              container
              alignItems="left"
              justify="center"
              direction="column"
              spacing={2}
            >
              <Grid item>
                <TextField
                  sx={{ width: 240 }}
                  autoFocus
                  required
                  id="name"
                  name="name"
                  label="Name"
                  type="text"
                  value={formValues.name}
                  onChange={handleInputChange}
                />
              </Grid>
              <Grid item>
                <FormControl sx={{ width: 240 }}>
                  <InputLabel id="demo-select-small">Type</InputLabel>
                  <Select
                    required
                    name="typeId"
                    label="Type"
                    value={formValues.typeId}
                    onChange={handleInputChange}
                  >
                    <MenuItem value="">
                      <em>------------ none ------------</em>
                    </MenuItem>
                    {types.map((type) => (
                      <MenuItem key={type.id} value={type.id}>
                        {type.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>
            </Grid>
            <Stack spacing={2} sx={{ py: 3, paddingRight: 0 }} direction="row">
              <Button
                sx={{ minWidth: 112 }}
                variant="outlined"
                onClick={() => navigate("/pets")}
              >
                Cancel
              </Button>
              <Button sx={{ minWidth: 112 }} type="submit" variant="contained">
                Save
              </Button>
            </Stack>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditPet;
