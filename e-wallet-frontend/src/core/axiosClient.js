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

axiosClient.interceptors.response.use(
    (res)=>{
        if(res && res.data){
            return res.data;
        }
        return res;
    },
    (error)=>{
        if (error.response?.status === 400 || error.response.status === 404) {         
            throw error.response.data.message;       
        }
        throw error; 
    }
);

export default axiosClient;