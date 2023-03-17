import {
  Button,
  Checkbox,
  FormControlLabel,
  Grid,
  Stack,
  TextField,
} from "@mui/material";
import { useSnackbar } from "notistack";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import HttpService from "../../services/HttpService";
import "./user.scss";

const EditUser = () => {
  const pageTitle = "Edit User";
  const { state } = useLocation();

  const [isAdmin, setIsAdmin] = useState(
    state.roles.find((r) => r.type === "ROLE_ADMIN") !== undefined
  );

  const defaultValues = {
    id: state.id,
    firstName: state.firstName,
    lastName: state.lastName,
    username: state.username,
    password: state.password,
    roles: state.roles.map((r) => r.type),
  };
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState(defaultValues);
  const { enqueueSnackbar } = useSnackbar();

  const handleCheckboxChange = (e) => {
    const { name, checked } = e.target;
    setIsAdmin(checked);
    setFormValues({
      ...formValues,
      [name]: checked ? ["ROLE_ADMIN"] : [], // as we do not change the "ROLE_USER", we just pass the "ROLE_ADMIN"
    });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    HttpService.putWithAuth("/users", formValues)
      .then((response) => {
        enqueueSnackbar("User updated successfully", { variant: "success" });
        navigate("/users");
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
                  required
                  autoFocus
                  id="firstName"
                  name="firstName"
                  label="First Name"
                  type="text"
                  value={formValues.firstName}
                  onChange={handleInputChange}
                />
              </Grid>
              <Grid item>
                <TextField
                  sx={{ width: 240 }}
                  required
                  id="lastName"
                  name="lastName"
                  label="Last Name"
                  type="text"
                  value={formValues.lastName}
                  onChange={handleInputChange}
                />
              </Grid>
            </Grid>
            <Grid item>
              <FormControlLabel
                label="Role Admin?"
                control={
                  <Checkbox
                    name="roles"
                    label="Roles"
                    value={isAdmin}
                    checked={isAdmin}
                    onChange={handleCheckboxChange}
                  />
                }
              />
            </Grid>
            <Stack spacing={2} sx={{ py: 3, paddingRight: 0 }} direction="row">
              <Button
                sx={{ minWidth: 112 }}
                variant="outlined"
                onClick={() => navigate("/users")}
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

export default EditUser;
