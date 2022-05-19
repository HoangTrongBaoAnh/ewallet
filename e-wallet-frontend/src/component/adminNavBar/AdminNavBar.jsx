import React from 'react'
import { useSelector } from 'react-redux';

import {Link,NavLink} from 'react-router-dom'
const AdminNavBar = () => {
    let activeStyle = {
        color: '#1F2937', borderBottom: '2px solid #1F2937'
      };
      const user = useSelector(state=>state.auth.user);
    return (
    <header className='p-12'>
    <nav className='flex'>
      <Link className='inline-flex '  to='/'>
        <img src='https://img.icons8.com/cute-clipart/64/000000/wallet-app.png' alt='wallet logo' />
        <span className='ml-4 text-3xl font-semibold text-gray-700'>Budget ツ</span>
      </Link>
      
      {user.id == null ? (
      <NavLink
        
        style={({ isActive }) =>
              isActive ? activeStyle : undefined

            }
        to='/login'>
        Login
      </NavLink>) : (
        <div style={{marginLeft:'auto'}} className='isLogin'>
          <div className="isLogin__text">
          {user.username}
          </div>
          <div className="isLogin__image">
            
          </div>
        </div>
      )}
      
    </nav>
  </header>
  )
}

export default AdminNavBar