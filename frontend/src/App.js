import { Fragment, useContext } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { DarkModeContext } from "./context/darkModeContext";
import Login from "./pages/auth/Login";
import Signup from "./pages/auth/Signup";
import Unauthorized from "./pages/auth/Unauthorized";
import Home from "./pages/home/Home";
import EditPet from "./pages/pet/EditPet";
import ListPet from "./pages/pet/ListPet";
import NewPet from "./pages/pet/NewPet";
import EditProfile from "./pages/profile/EditProfile";
import Profile from "./pages/profile/Profile";
import Statistic from "./pages/statistic/Statistic";
import EditUser from "./pages/user/EditUser";
import ListUser from "./pages/user/ListUser";
import PrivateRoute from "./PrivateRoute";
import RoleAccess from "./RoleAccess";
import "./style/dark.scss";

function App() {
  const { darkMode } = useContext(DarkModeContext);

  return (
    <div className={darkMode ? "app dark" : "app"}>
      <BrowserRouter>
        <Fragment>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />

            <Route path="unauthorized" element={<PrivateRoute />}>
              <Route index element={<Unauthorized />} />
            </Route>

            <Route path="/" element={<PrivateRoute />}>
              <Route index element={<Home />} />
              <Route path="home" element={<Home />} />
            </Route>

            <Route path="pets" element={<PrivateRoute />}>
              <Route element={<RoleAccess roles={["ROLE_USER"]} />} >
                <Route index element={<ListPet />} />
              </Route>
              <Route element={<RoleAccess roles={["ROLE_USER"]} />} >
                <Route path="new" element={<NewPet />} />
              </Route>
              <Route element={<RoleAccess roles={["ROLE_USER"]} />} >
                <Route path="edit" element={<EditPet />} />
              </Route>
            </Route>

            <Route path="profile" element={<PrivateRoute />}>
              <Route element={<RoleAccess roles={["ROLE_USER"]} />} >
                <Route index element={<Profile />} />
              </Route>
              <Route element={<RoleAccess roles={["ROLE_USER"]} />} >
                <Route path="edit" element={<EditProfile />} />
              </Route>
            </Route>

            <Route path="users" element={<PrivateRoute />}>
              <Route element={<RoleAccess roles={["ROLE_ADMIN"]} />} >
                <Route index element={<ListUser />} />
              </Route>
              <Route element={<RoleAccess roles={["ROLE_ADMIN"]} />}>
                <Route path="edit" element={<EditUser />} />
              </Route>
            </Route>

            <Route path="statistics" element={<PrivateRoute />}>
              <Route element={<RoleAccess roles={["ROLE_ADMIN"]} />} >
                <Route index element={<Statistic />} />
              </Route>
            </Route>

          </Routes>
        </Fragment>
      </BrowserRouter>
    </div>
  );
}

export default App;
