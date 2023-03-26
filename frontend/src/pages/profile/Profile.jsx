import { useSnackbar } from "notistack";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import AuthService from "../../services/AuthService";
import HttpService from "../../services/HttpService";
import "./profile.scss";

const Profile = () => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const [user, setUser] = useState("currentUser");

  useEffect(() => {
    const userId = AuthService.getCurrentUser()?.id;
    HttpService.getWithAuth("/users/" + userId)
      .then((response) => {
        setUser(response.data);
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
  }, []);

  const handleEdit = () => {
    navigate("/profile/edit", { state: user });
  };

  return (
    <div className="single">
      <Sidebar />
      <div className="singleContainer">
        <Navbar />
        <div className="top">
          <div className="left">
            <div className="editButton" onClick={() => handleEdit()}>
              Edit
            </div>
            <h1 className="title">Profile</h1>
            <div className="item">
              <img
                src={`${process.env.PUBLIC_URL}/profile.png`}
                alt="user"
                className="itemImg"
              />
              <div className="details">
                <h1 className="itemTitle">{user.fullName}</h1>
                <div className="detailItem">
                  <span className="itemKey">Username:</span>
                  <span className="itemValue">{user.username}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
