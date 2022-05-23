import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Modal, Form} from 'react-bootstrap';
import './Bill.scss';
import MUIDataTable from "mui-datatables";
import baserequest from '../../../core/baserequest';
import ewalletApi from '../../../core/ewalletApi';

const Bill = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });

    // const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [bills, setbills] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchBill = async () => {
        await baserequest.get("bill")
            .then(res => {
                var tmp = [];
                res.data.forEach(item => {
                    tmp.push([item.id, item.customerName, item.customercode, item.amount, item.status, item.cagetory_id])
                });
                setbills(tmp);
            })
    }

    const deleteCate = (id) => {
        baserequest.delete("bill/" + id)
            .then(res => {
                fetchBill();
            })

    }

    const setwalletactive = async (item) => {
        console.log(item)
        if (window.confirm("set acive this card")) {
          try {
            await ewalletApi.confirmBill(item['0']);
            fetchBill();
          }
          catch (err) {
            console.log(err.message);
          }
        }
      }

    React.useEffect(() => {
        fetchBill();
    }, []);
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
                        <div className='Form.ControlName'>{data}</div>
                    )
                }
            }
        },
        {
            label: "CustomerName",
            name: "CustomerName",
            sortable: true,
            filter: true,
            options: {
                customBodyRender: (data) => {
                    return (
                        <div className='Form.ControlName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "customercode",
            label: "customercode",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='Form.ControlName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "amount",
            label: "amount",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='Form.ControlName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Status",
            label: "Status",
            options: {
                customBodyRender: (data,rowData) => {
                    var obj = rowData.rowData.reduce(function (acc, cur, i) {
                        console.log(cur);
                        acc[i] = cur;
                        return acc;
                    }, {});
                    //console.log(data);
                    return (
                        <Form.Check
                        checked={data}
                        onChange={e => setwalletactive(obj)}
                        type="switch"
                        id="custom-switch"
                        className='Form.ControlSwitch'
                        
                      />
                    )
                }
            }
        },
        {
            name: "cagetory_id",
            label: "cagetory_id",
            options: {
                customBodyRender: (data) => {
                    console.log(data);
                    return (
                        <div className='Form.ControlName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Action",
            label: "Action",
            options: {
                customBodyRender: (Data, rowData) => {
                    //console.log(rowData.rowData);
                    var obj = rowData.rowData.reduce(function (acc, cur, i) {
                        console.log(cur);
                        acc[i] = cur;
                        return acc;
                    }, {});
                    //console.log(rowData);
                    return (
                        <div className='adminBank__button'>
                            <button className='primary edit' onClick={e => handleShow(-1, obj)}><i className='bx bx-edit'></i></button>
                            <button className='primary delete' onClick={e => deleteCate(obj['0'])}><i className='bx bx-trash'></i></button>
                        </div>
                    )
                }
            }
        },
    ]
    return (
        <div className='adminBill'>
            <Button className='primary new' onClick={e => handleShow(1)}>
                Add bill
            </Button>
            
            <MyVerticallyCenteredModal
                show={show}
                setbills={setbills}
                setShow={setShow}
                onHide={() => setShow(false)}
                edittedindex={edittedindex}
                edititem={edititem}
                fetchBill={fetchBill}
                setedititem={setedititem}
            />
            <MUIDataTable
                title={"Bill List"}
                data={bills}
                columns={columns}
                options={options}
            />
        </div>
    )
}

function MyVerticallyCenteredModal(props) {
    const [customercode, setcustomercode] = useState("");
    const [address, setaddress] = useState("");

    const [customername, setcustomername] = useState("");
    const [phonenumber, setphonenumber] = useState("");
    const [meternumber, setmeternumber] = useState("");

    const [amount, setamount] = useState(0);
    const [categoryid, setcategoryid] = useState("");
    const [billcode, setbillcode] = useState("");

    const [category, setcategory] = useState([]);
    useEffect(() => {
        fetchCategories();
        //console.log(props.edititem)
        if (props.edittedindex === -1) {
            setcustomercode(props.edititem['2'])
            setcustomername(props.edititem['1'])
            setaddress(props.edititem.address)
            setamount(props.edititem['3'])
            //setst(props.edititem['3'])
            setcategoryid(props.edititem['5'])
        }
        return () => {
            // setname("")
        }
    }, [props.show,props.edittedindex,props.edititem])

    const fetchCategories = async () => {
        await baserequest.get("category")
            .then(res => {
                setcategory(res.data);
                setcategoryid(res.data[0].id)
            })
    }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("customercode", customercode);
        data.append("address", address);
        data.append("amount", amount);
        data.append("customerName", customername);
        data.append("PhoneNumber", phonenumber);
        data.append("meterNumber", meternumber);
        data.append("billTypeId", categoryid);

        data.append("billcode", billcode);

        // console.log(categoryid);
        baserequest.post("bill/", data)
            .then(res => {
                props.setShow(false);
                props.fetchBill()
            })

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();

        data.append("customercode", customercode);
        data.append("address", address);
        data.append("amount", amount);
        data.append("customerName", customername);
        data.append("PhoneNumber", phonenumber);
        data.append("meterNumber", meternumber);
        data.append("billTypeId", categoryid);

        data.append("billcode", billcode);
        //console.log(name,img);
        baserequest.put("bill/" + props.edititem['0'], data)
            .then(res => {
                props.fetchBill();
                props.setShow(false)
                //fetchCategories()
            })

    }
    return (
        <Modal
            show={props.show}
            onHide={props.onHide}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>{props.edittedindex === 1 ? "Add new Bill" : "Edit"}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {props.edittedindex === 1 ? (
                    <Form onSubmit={createCate}>
                        <Form.Group>
                            <Form.Label>Customercode</Form.Label>
                            <Form.Control onChange={e => setcustomercode(e.target.value)} name="customercode" value={customercode} placeholder='customercode' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Address</Form.Label>
                            <Form.Control onChange={e => setaddress(e.target.value)} name="address" value={address} placeholder='address' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Amount</Form.Label>
                            <Form.Control onChange={e => setamount(e.target.value)} name="amount" value={amount} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>CustomerName</Form.Label>
                            <Form.Control onChange={e => setcustomername(e.target.value)} name="customername" value={customername} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>phonenumber</Form.Label>
                            <Form.Control onChange={e => setphonenumber(e.target.value)} name="phonenumber" value={phonenumber} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>MeterNumber</Form.Label>
                            <Form.Control onChange={e => setmeternumber(e.target.value)} name="meternumber" value={meternumber} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Bill code</Form.Label>
                            <Form.Control onChange={e => setbillcode(e.target.value)} name="Billcode" value={billcode} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Category</Form.Label>
                            <select onChange={e => setcategoryid(e.target.value)} name="updateCate" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </select>
                        </Form.Group >
                        <Button type='submit'>Submit</Button>
                    </Form >
                ) : (
                    <Form onSubmit={updateCate}>
                        <Form.Group>
                            <Form.Label>customercode</Form.Label>
                            <Form.Control onChange={e => setcustomercode(e.target.value)} name="customercode" value={customercode} placeholder='customercode' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>address</Form.Label>
                            <Form.Control onChange={e => setaddress(e.target.value)} name="address" value={address} placeholder='address' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>amount</Form.Label>
                            <Form.Control onChange={e => setamount(e.target.value)} name="amount" value={amount} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>CustomerName</Form.Label>
                            <Form.Control onChange={e => setcustomername(e.target.value)} name="customername" value={customername} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>phonenumber</Form.Label>
                            <Form.Control onChange={e => setphonenumber(e.target.value)} name="phonenumber" value={phonenumber} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>MeterNumber</Form.Label>
                            <Form.Control onChange={e => setmeternumber(e.target.value)} name="meternumber" value={meternumber} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Bill code</Form.Label>
                            <Form.Control onChange={e => setbillcode(e.target.value)} name="Billcode" value={billcode} placeholder='amount' />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Category</Form.Label>
                            <select defaultValue={categoryid} onChange={e => setcategoryid(e.target.value)} name="bank" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </select>
                        </Form.Group >
                        <Button type='submit'>Submit</Button>
                    </Form >
                )}
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={e => props.setShow(false)} variant="secondary" >
                    Close
                </Button>
            </Modal.Footer>
        </Modal>
    );
}
export default Bill