import React, { Component } from "react";
import {
    BrowserRouter as Router,
    Routes,
    Route,

} from "react-router-dom";
import LoginPage from '../pages/login/login'
import RegisterPage from '../pages/register/register'
import TransactionPage from '../pages/transaction/transaction'
import Payment from "../pages/payment/payment";

import Layout from "../layout/layout";
import Adminlayout from "../layout/adminlayout/adminlayout";

import Index from "../pages/admin";
import BillCategory from "../pages/admin/bill_category/BillCategory";
import Card from "../pages/admin/card/Card";
import Bank from "../pages/admin/bank/Bank";
import Bill from "../pages/admin/Bill/Bill";
import Customer from "../pages/admin/customer/Customer";
import Transaction from "../pages/admin/transactions/Transactions";
import ProfilePage from "../pages/ProfilePage/ProfilePage";
//import RegisterPage from '../pages/RegisterPage'
import App from '../App'
import Transactions from "../pages/admin/transactions/Transactions";

export default class Routerx extends Component {
    render() {
        return (
            <Router>
                <Routes>
                    <Route exact path="/login" element={<LoginPage />} />
                    <Route exact path="/register" element={<RegisterPage />} />
                    <Route path='/' element={<Layout />}>
                        <Route exact path="/" element={<App />} />

                        <Route exact path="/transaction" element={<TransactionPage />} />
                        <Route exact path="/payment/:name" element={<Payment />} />
                        <Route exact path="/profilepage/:name" element={<ProfilePage />} />
                        {/* <Route exact path="/register" element={<RegisterPage />} /> */}
                    </Route>
                    <Route path='/admin' element={<Adminlayout />}>
                        <Route exact path="/admin" element={<Index />} />
                        <Route exact path="/admin/category" element={<BillCategory />} />
                        <Route exact path="/admin/card" element={<Card />} />
                        <Route exact path="/admin/bank" element={<Bank />} />
                        <Route exact path="/admin/bill" element={<Bill />} />
                        <Route exact path="/admin/customer" element={<Customer />} />
                        <Route exact path="/admin/transaction" element={<Transactions />} />
                    </Route>

                </Routes>
            </Router>
        );
    }
}