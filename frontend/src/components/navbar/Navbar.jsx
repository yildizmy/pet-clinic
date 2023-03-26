import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import { Tooltip } from "@mui/material";
import { useContext } from "react";
import { DarkModeContext } from "../../context/darkModeContext";
import AuthService from "../../services/AuthService";
import "./navbar.scss";

const Navbar = () => {
  const { dispatch } = useContext(DarkModeContext);
  const username = AuthService.getCurrentUser()?.username;

  return (
    <div className="navbar-wrapper">
      <div className="navbar">
        <div className="wrapper">
          <div className="search">
            <input type="text" placeholder="Search..." />
            <SearchOutlinedIcon />
          </div>
          <div className="items">
            <div className="item">
              <DarkModeOutlinedIcon
                className="icon"
                onClick={() => dispatch({ type: "TOGGLE" })}
              />
            </div>
            <div className="item">
              <Tooltip title={username} placement="bottom-end">
                <img
                  src={`${process.env.PUBLIC_URL}/avatar.png`}
                  alt="avatar"
                  className="itemImg"
                  width={30}
                />
              </Tooltip>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
