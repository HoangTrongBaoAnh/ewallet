import React, { useEffect } from 'react'
import { Outlet } from 'react-router-dom'
import AdminNavBar from '../../component/adminNavBar/AdminNavBar'
import AdminSideBar from '../../component/adminSideBar/AdminSideBar'
import ewalletApi from '../../core/ewalletApi';
const Adminlayout = () => {
  useEffect(() => {
    const adminVerify = async () => {
      await ewalletApi.adminVerify();
    }
    adminVerify();

  }, [])

  return (
    <div className='layoutx'>
      <AdminNavBar />
      <AdminSideBar />
      <Outlet />

    </div>
  )
}

export default Adminlayout