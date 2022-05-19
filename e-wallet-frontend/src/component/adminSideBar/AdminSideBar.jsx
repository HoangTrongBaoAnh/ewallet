import React from 'react'
import { NavLink } from 'react-router-dom'
import './AdminSideBar.scss'
const AdminSideBar = () => {
    const sideNavBarItems = [
        {
            display: 'Bill category',
            icon: <i className='bx bx-category'></i>,
            to: '/admin/category',
            section: ''
        },
        {
            display: 'Bill',
            icon: <i className='bx bxs-note'></i>,
            to: '/admin/bill',
            section: 'transabankction'
        },
        {
            display: 'Card',
            icon: <i className='bx bx-credit-card'></i>,
            to: '/admin/card',
            section: 'transaction'
        },
        {
            display: 'Bank',
            icon: <i className='bx bxs-bank'></i>,
            to: '/admin/bank',
            section: 'transabankction'
        },
        {
            display: 'Customer',
            icon: <i className='bx bxs-user'></i>,
            to: '/admin/customer',
            section: 'Customer'
        },
        {
            display: 'Transactions',
            icon: <i className='bx bx-transfer'></i>,
            to: '/admin/transaction',
            section: 'transabankction'
        }
    ]
    return (
        <div className='admin__sidebar'>
            <div className='sidebar'>
            <div className='sidebar__logo'>
                Admin Page
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
                </div>
            </div>
        </div>
    )
}

export default AdminSideBar