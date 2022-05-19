import React from 'react'
import Sidebar from '../component/sidebar/sidebar'
import { Outlet } from 'react-router-dom'
import Header from '../component/navbar/navbar'
import './layout.scss'

const Layout = () => {
  return (
    <div className='layoutx'>
      <Header />
        <Sidebar />
        <Outlet />
    </div>
  )
}

export default Layout