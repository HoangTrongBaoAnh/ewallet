const axios = require("axios");
const apiUrl = "http://localhost:8080/api/";


const baserequest = {
  getHeaders() {
    let token = window.localStorage.getItem("token");
    if (token == null) {
      return {'Content-Type': 'application/json'};
    }
    return { Authorization: "Bearer " + token,'Content-Type': 'application/json' };
  },
  get(url) {
    return axios.get(apiUrl + url, { headers: this.getHeaders() });
  },
  post(url, data) {
    return axios.post(apiUrl + url, data, { headers: this.getHeaders() });
  },
  put(url, data) {
    return axios.put(apiUrl + url, data, { headers: this.getHeaders() });
  },
  delete(url) {
    return axios.delete(apiUrl + url, { headers: this.getHeaders() });
  }
};

export default baserequest;