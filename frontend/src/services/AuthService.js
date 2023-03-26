import axios from "./axios";

const login = (body) => {
  const url = "/auth/login";
  return axios.post(url, body).then((response) => {
    localStorage.setItem("user", JSON.stringify(response.data.data));
    return response.data;
  });
};

const signup = (body) => {
  const url = "/auth/signup";
  return axios.post(url, body).then((response) => {
    return response.data;
  });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  login,
  signup,
  logout,
  getCurrentUser,
};

export default AuthService;
