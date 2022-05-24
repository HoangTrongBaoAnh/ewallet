import history from '../router/history';
const axios = require("axios");
const apiUrl = "http://localhost:8080/api/";

const axiosClient = axios.create({
    baseURL:apiUrl,
    headers: {
        'Content-Type': 'application/json',
    },
});


//axiosClient.interceptors.request.use(async(config)=>config);
axiosClient.interceptors.request.use(function (config) {
    const token = localStorage.getItem('token');
    config.headers.Authorization =  token ? `Bearer ${token}` : '';

    return config;
});

axiosClient.interceptors.response.use(
    (res)=>{
        if(res && res.data){
            return res.data;
        }
        return res;
    },
    (error)=>{
        if (error.response.status === 400 || error.response.status === 404) {
            //console.log(error.response.data)  
            //return Promise.reject(error.response.data.message);
            throw error.response.data.message;       
        }
        else if(error.response.status === 401 || error.response.status === 403){
            // navigate('/login');
            window.alert("Please sign in before continue!");
            history.replace("/login");
            //throw error
        }
        return Promise.reject(error);
    }
);

export default axiosClient;