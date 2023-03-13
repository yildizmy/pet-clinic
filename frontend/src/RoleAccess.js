import React from "react";
import { Navigate, Outlet } from "react-router-dom";

const RoleAccess = ({ roles = [] }) => {
  const userRoles = localStorage.getItem("roles");

  return !roles.length || roles.some(r => userRoles.includes(r)) ? (
    <Outlet />
  ) : (
    <Navigate to="/unauthorized" replace />
  );
};

export default RoleAccess;
