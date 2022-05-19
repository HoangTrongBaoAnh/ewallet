import React, { useState, useRef, useEffect } from 'react'
import { Link, NavLink, useNavigate } from 'react-router-dom'
import 'boxicons/css/boxicons.min.css'
import { useSelector } from 'react-redux';

import './sidebar.scss'
import { useDispatch } from 'react-redux';
import { REMOVE_USER_STATE } from '../../store/auth/authreducer';

const sideNavBarItems = [
    {
        display: 'Dashboard',
        icon: <i className='bx bx-home'></i>,
        to: '/',
        section: ''
    },
    {
        display: 'Transaction',
        icon: <i className='bx bx-transfer'></i>,
        to: '/transaction',
        section: 'transaction'
    }
]

const Sidebar = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const logout = (e) => {
        e.preventDefault();
        dispatch(REMOVE_USER_STATE());
        window.localStorage.removeItem("token");
        navigate('/login')

    }
    const user = useSelector(state => state.auth.user);

    // const [activeindex,setactiveindex] = useState(0);
    // const [stepHeight, setStepHeight] = useState(0);

    const sidebarRef = useRef();
    // const indicatorRef = useRef();
    // let activeStyle = {
    //     color: '#1F2937', borderBottom: '3px solid red'
    // };

    return (
        <div className='sidebar'>
            <div className='sidebar__logo'>
                E-Wallet
            </div>
            <div className='sidebar__menu'>
                <div className="sidebar__menu__indicator">

                </div>
                {
                    sideNavBarItems.map((item, index) => (
                        <NavLink to={item.to} key={index} >
                            <div className={`sidebar__menu__item `}>
                                <div className="sidebar__menu__item__icon">
                                    {item.icon}
                                </div>
                                <div className="sidebar__menu__item__text">
                                    {item.display}
                                </div>
                            </div>
                        </NavLink>
                    ))
                }
                {
                    user.id != null ? (
                        user.roles.map(item => (
                            item.name === "ROLE_ADMIN" ?
                                (
                                    <NavLink key={item.id} to='/admin'  >
                                        <div className={`sidebar__menu__item `}>
                                            <div className="sidebar__menu__item__icon">
                                                <i className='bx bx-data'></i>
                                            </div>
                                            <div className="sidebar__menu__item__text">
                                            Admin
                                            </div>
                                        </div>
                                    </NavLink>
                                ) : null
                        ))
                    ) : null
                }

                {user.id != null ? (
                    <div className={`sidebar__menu__item last`}>
                        <div className="sidebar__menu__item__icon">
                            <i className='bx bx-log-out-circle'></i>
                        </div>
                        <div className="sidebar__menu__item__text" onClick={e => logout(e)}>
                            Logout
                        </div>
                    </div>
                ) : null}
            </div>
        </div>
    )
}

export default Sidebar