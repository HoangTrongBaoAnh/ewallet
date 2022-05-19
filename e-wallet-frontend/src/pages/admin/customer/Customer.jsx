import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form } from 'react-bootstrap';
import './customer.scss';
import baserequest from '../../../core/baserequest';
import MUIDataTable from "mui-datatables";

const Customer = () => {
  
    // const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    //const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        //setShow(true);
    };

    const [card, setcard] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchCards = async () => {
        await baserequest.get("customer")
            .then(res => {
                var tmp = [];
                res.data.map(item => {
                    tmp.push([item.id, item.username, item.balance,item.email, item.phonenumber])
                });
                setcard(tmp);
            })
    }

    const deleteCate = (id) => {
        baserequest.delete("card/" + id)
            .then(res => {

                fetchCards()
            })

    }

    React.useEffect(() => {
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
        // {
        //     name: "Action",
        //     label: "Action",
        //     options: {
        //         customBodyRender: (Data, rowData) => {
        //             //console.log(rowData.rowData);
        //             var obj = rowData.rowData.reduce(function (acc, cur, i) {
        //                 //console.log(cur);
        //                 acc[i] = cur;
        //                 return acc;
        //             }, {});
        //             //console.log(rowData);
        //             return (
        //                 <div className='adminBank__button'>
        //                     <button className='primary edit' onClick={e => handleShow(-1, obj)}><i className='bx bx-edit'></i></button>
        //                     <button className='primary delete' onClick={e => deleteCate(obj['0'])}><i className='bx bx-trash'></i></button>
        //                 </div>
        //             )
        //         }
        //     }
        // },
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