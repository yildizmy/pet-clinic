import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";
import { useSnackbar } from "notistack";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Datatable from "../../components/datatable/Datatable";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import AuthService from "../../services/AuthService";
import HttpService from "../../services/HttpService";
import { petColumns } from "./fields";

const ListPet = () => {
  const pageTitle = "My Pets";
  const navigate = useNavigate();
  const [id, setId] = useState();
  const [data, setData] = useState([]);
  const { enqueueSnackbar } = useSnackbar();
  const [open, setOpen] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth("/pets/users/" + userId)
      .then((response) => {
        setData(response.data);
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
  };

  const handleEdit = (params) => {
    navigate("/pets/edit", { state: params.row });
  };

  const handleClickOpen = (id) => {
    setId(id);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = () => {
    HttpService.deleteWithAuth("/pets/" + id)
      .then((res) => {
        fetchData();
        setOpen(false);
        enqueueSnackbar("Pet is deleted successfully", { variant: "success" });
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

  const actionColumn = [
    {
      field: "action",
      headerName: "Action",
      headerAlign: "left",
      align: "left",
      width: 150,
      // flex: 0,

      renderCell: (params) => {
        return (
          <div className="cellAction">
            <div className="editButton" onClick={() => handleEdit(params)}>
              Edit
            </div>
            <div
              className="deleteButton"
              onClick={() => handleClickOpen(params.row.id)}
            >
              Delete
            </div>
          </div>
        );
      },
    },
  ];

  return (
    <div className="list">
      <Sidebar />
      <div className="listContainer">
        <Navbar />
        <Datatable
          title={pageTitle}
          userColumns={petColumns}
          actionColumn={actionColumn}
          data={data}
          to={"/pets/new"}
        />
      </div>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{"Delete pet?"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure to delete record?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleDelete} autoFocus>
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default ListPet;
