import React from 'react'
import { useState, useEffect } from 'react';
// import { Button, Table, Modal, Form } from 'react-bootstrap';
import './customer.scss';
import baserequest from '../../../core/baserequest';
import MUIDataTable from "mui-datatables";

const Customer = () => {

    const [card, setcard] = useState([]);

    const fetchCards = async () => {
        await baserequest.get("customer")
            .then(res => {
                var tmp = [];
                res.data.forEach(item => {
                    tmp.push([item.id, item.username, item.balance,item.email, item.phonenumber])
                });
                setcard(tmp);
            })
    }

    useEffect(() => {
        fetchCards();
    }, [])

    const options = {
        filterType: 'checkbox',
        responsive: 'simple',

    };
    const columns = [
        {
            label: "ID",
            name: "ID",
            sortable: true,
            filter: true,
            options: {
                customBodyRender: (data) => {
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            label: "Username",
            name: "Username",
            sortable: true,
            filter: true,
            options: {
                customBodyRender: (data) => {
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Balance",
            label: "Balance",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Email",
            label: "Email",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Phone number",
            label: "Phone number",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
    ]

    return (
        <div className='adminCustomer'>
           
            {/* <Button className='primary new'>
                Add Customer
            </Button> */}
              
            <MUIDataTable
                title={"Customer List"}
                data={card}
                columns={columns}
                options={options}
            />
            
        </div>
    )
  
}

export default Customer