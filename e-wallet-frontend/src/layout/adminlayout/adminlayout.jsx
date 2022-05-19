import React from 'react'
import { NavLink, Outlet } from 'react-router-dom'
import AdminNavBar from '../../component/adminNavBar/AdminNavBar'
import AdminSideBar from '../../component/adminSideBar/AdminSideBar'
const adminlayout = () => {
  return (
    <div className='layoutx'>
      <AdminNavBar />
      <AdminSideBar />
        <Outlet />
       
    </div>
  )
}

export default adminlayout