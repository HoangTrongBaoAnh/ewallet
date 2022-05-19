const axios = require("axios");
const apiUrl = "http://localhost:8080/api/";

const getHeaders = () => {
  let token = window.localStorage.getItem("token");
  if (token == null) {
    return {'Content-Type': 'application/json'};
  }
  return { Authorization: "Bearer " + token,'Content-Type': 'application/json' };
}

const axiosClient = axios.create({
    baseURL:apiUrl,
    headers: getHeaders(),
});


//axiosClient.interceptors.request.use(async(config)=>config);
axiosClient.interceptors.request.use(async (config) => config);

axiosClient.interceptors.response.use((res)=>{
    if(res && res.data){
        return res.data;
    }
    return res;
},(error)=>{
    throw error;
});

export default {
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
