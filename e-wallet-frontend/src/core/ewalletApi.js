import axiosClient from "./axiosClient";

const ewalletApi = {
    //admin verify
    adminVerify: () => {
        const url = 'auth/admin';
        return axiosClient.get(url);
    },
    //login
    signIn: (data) => {
        const url = 'auth/signin';
        return axiosClient.post(url,data);
    },
    //register
    register: (data) => {
        const url = 'auth/signup';
        return axiosClient.post(url,data);
    },
    //transaction history
    getTransactionPage0: (userId) => {
        const url = 'transaction/alltransaction/' + userId + "?page=" + 0;
        return axiosClient.get(url);
    },
    getAllTransaction: (userId,currentPage) => {
        const url = 'transaction/alltransaction/' + userId + "?page=" + currentPage;
        return axiosClient.get(url);
    },
    //chart data
    getDataLineChart: () => {
        const url = 'transaction/chartjs';
        return axiosClient.post(url);
    },
    getDataPieChart: () => {
        const url = 'transaction/chartbycategories';
        return axiosClient.post(url);
    },
    cashOut: (cardNumber, data) => {
        const url = 'transaction/cashin/' + cardNumber;
        return axiosClient.post(url,data);
    },
    cashIn: (cardNumber, data) => {
        const url = 'transaction/cashin/' + cardNumber;
        return axiosClient.post(url,data);
    },
    getWallet: () => {
        const url = 'wallet';
        return axiosClient.get(url);
    },
    //get list of services
    getServiceCagetory: () => {
        const url = 'category';
        return axiosClient.get(url);
    },
    removeWallet: (cardNumber) => {
        const url = 'card/remove/wallet/' + cardNumber;
        return axiosClient.post(url);
    },
    setWalletActive: (cardNumber) => {
        const url = 'card/wallet/' + cardNumber;
        return axiosClient.post(url);
    },
    //get list of banks
    getBanks: () => {
        const url = 'bank';
        return axiosClient.get(url);
    },
    addCardToWallet: (data) => {
        const url = 'wallet/addcardtowallet';
        return axiosClient.post(url,data);
    },
    sendSms: (cardnumber) => {
        const url = `bank/sms/` + cardnumber;
        return axiosClient.get(url); 
    },
    getSignature: (cardnumber) => {
        const url = 'bank/getsignature/a009e24f7dcc0d20596adece715f0c18/cardnumber/' + cardnumber;
        return axiosClient.get(url); 
    },
    getAccount: (data) => {
        const url = 'bank/getaccount/';
        return axiosClient.post(url,data); 
    },
    addOcbAccount: (data) => {
        const url = 'bank/addocbaccount';
        return axiosClient.post(url,data); 
    },
    //user
    editUser: (userId,data) => {
        const url = 'customer/' + userId;
        return axiosClient.put(url,data); 
    },
    findUserByUserName: (username) => {
        const url = 'customer/find/' + username;
        return axiosClient.get(url); 
    },
    //transaction (tranfer money)
    tranferMoney: (data) => {
        const url = 'transaction';
        return axiosClient.post(url,data); 
    },
    //transaction (bill payment)
    getBillInfo: (customercode,name) => {
        const url = 'bill/' + customercode + '/category/' + name;
        return axiosClient.get(url); 
    },
    transactionPayBill: (data) => {
        const url = 'transaction/payment';
        return axiosClient.post(url,data); 
    },
    confirmBill: (billInfoId) => {
        const url = 'bill/setactive/' + billInfoId;
        return axiosClient.post(url); 
    }
}


export default ewalletApi;