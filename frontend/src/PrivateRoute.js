import React from "react";
import { Navigate, Outlet } from "react-router-dom";

const PrivateRoute = () => {
  const isAuthenticated =
    localStorage.getItem("currentUser") !== null &&
    localStorage.getItem("currentUser") !== "undefined";

  // if authorized, return an outlet that will render child elements; if not, return element that will navigate to login page
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;
