import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import './navbar.scss'
import {Link,NavLink} from 'react-router-dom'
import { useSelector } from 'react-redux';
const Navbar = ({id}) => {
  let activeStyle = {
    color: '#1F2937', borderBottom: '2px solid #1F2937'
  };
  const user = useSelector(state=>state.auth.user);
  //console.log(user);
  return (
    <header className='p-12'>
    <nav className='flex items-center '>
      <Link className='inline-flex items-center'  to='/'>
        <img src='https://img.icons8.com/cute-clipart/64/000000/wallet-app.png' alt='wallet logo' />
        <span className='ml-4 text-3xl font-semibold text-gray-700'>Budget ツ</span>
      </Link>
      <NavLink
        className='ml-auto p-2 text-2xl font-medium text-gray-400 cursor-pointer'
        style={({ isActive }) =>
              isActive ? activeStyle : undefined
            }
        to={`/transaction`}>
        Transaction
      </NavLink>
      {user.id == null ? (
      <NavLink
        className='ml-10 p-2 text-2xl font-medium text-gray-400 cursor-pointer'
        style={({ isActive }) =>
              isActive ? activeStyle : undefined
            }
        to='/login'>
        Login
      </NavLink>) : (
        <NavLink
        className='ml-10 p-2 text-2xl font-medium text-gray-400 cursor-pointer'
        style={({ isActive }) =>
              isActive ? activeStyle : undefined
            }
            to={{
              pathname:"/profilepage/"+user.username,
              
              }}
        >
        <div className='isLogin'>
          <div className="isLogin__text">
          {user.username}
          </div>
          <div className="isLogin__image">
            
          </div>
        </div>
      </NavLink>
        
      )}
      
    </nav>
  </header>
  )
}

export default Navbar