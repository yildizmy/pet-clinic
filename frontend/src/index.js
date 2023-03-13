import { SnackbarProvider } from "notistack";
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { DarkModeContextProvider } from "./context/darkModeContext";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <DarkModeContextProvider>
    <SnackbarProvider preventDuplicate>
      <App />
    </SnackbarProvider>
  </DarkModeContextProvider>
);
