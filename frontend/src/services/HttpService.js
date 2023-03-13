import axios from "./axios";

const AUTH_TOKEN = localStorage.getItem("tokenKey");
const config = {
  headers: {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": true,
    "Content-Type": "application/json",
    Accept: "application/json",
    Authorization: `Bearer ${AUTH_TOKEN}`
  },
};

export const getWithAuth = (url) => {
  const request = axios.get(url, config);
  return request.then((response) => response.data);
};

export const postWithoutAuth = (url, body) => {
  const request = axios.post(url, body);
  return request.then((response) => response.data);
};

export const postWithAuth = (url, body) => {
  const request = axios.post(url, body, config);
  return request.then((response) => response.data);
};

export const putWithAuth = (url, body) => {
  const request = axios.put(url, body, config);
  return request.then((response) => response.data);
};

export const deleteWithAuth = (url) => {
  const request = axios.delete(url, config);
  return request.then((response) => response.data);
};
