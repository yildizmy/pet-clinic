import { Button, Grid, Stack, TextField } from "@mui/material";
import { useSnackbar } from "notistack";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import HttpService from "../../services/HttpService";
import "./profile.scss";

const EditProfile = () => {
  const pageTitle = "Edit Profile";
  const { state } = useLocation();
  const defaultValues = {
    id: state.id,
    firstName: state.firstName,
    lastName: state.lastName,
  };
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState(defaultValues);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    HttpService.putWithAuth("/users/profile", formValues)
      .then((response) => {
        enqueueSnackbar("User updated successfully", { variant: "success" });
        navigate("/profile");
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
            <Stack spacing={2} sx={{ py: 3, paddingRight: 0 }} direction="row">
              <Button
                sx={{ minWidth: 112 }}
                variant="outlined"
                onClick={() => navigate("/profile")}
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

export default EditProfile;
