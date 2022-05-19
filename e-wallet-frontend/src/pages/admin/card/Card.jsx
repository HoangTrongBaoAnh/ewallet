import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Table, Modal, Form } from 'react-bootstrap';
import './card.scss';
import baserequest from '../../../core/baserequest';
import MUIDataTable from "mui-datatables";


const Card = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [card, setcard] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchCards = async () => {
        await baserequest.get("card")
            .then(res => {
                var tmp = [];
                res.data.map(item => {
                    tmp.push([item.id, item.cardnumber, item.balance,item.securitycode, item.bank_id])
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
            label: "Card Number",
            name: "Card Number",
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
            name: "Security Code",
            label: "Security Code",
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
            name: "Bank ID",
            label: "Bank ID",
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
            name: "Action",
            label: "Action",
            options: {
                customBodyRender: (Data, rowData) => {
                    //console.log(rowData.rowData);
                    var obj = rowData.rowData.reduce(function (acc, cur, i) {
                        //console.log(cur);
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
        <div className='adminCard'>
           
            <Button className='primary new' onClick={e => handleShow(1)}>
                Add Card
            </Button>
            
            {/* <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th> cardnumber</th>
                        <th> balance</th>
                        <th> bank_id</th>
                        <th>Action</th>
                        
                    </tr>
                </thead>
                <tbody>
                    {card.map((item, index) => (
                        <tr key={index}>
                            <td>{item.id}</td>
                            <td>{item.cardnumber}</td>
                            <td>{item.balance}</td>
                            <td>{item.bank_id}</td>
                            <td>
                                <button onClick={e => handleShow(-1, item)}><i className='bx bx-edit'></i>Edit</button>
                                <button onClick={e => deleteCate(item.id)}><i className='bx bx-trash'></i></button></td>
                            
                        </tr>
                    ))}

                </tbody>
            </Table> */}
            <MyVerticallyCenteredModal
                show={show}
                setcard={setcard}
                setShow={setShow}
                onHide={() => setShow(false)}
                edittedindex={edittedindex}
                edititem={edititem}
                fetchCards={fetchCards}
                setedititem={setedititem}
            />
            <MUIDataTable
                title={"Card List"}
                data={card}
                columns={columns}
                options={options}
            />
            
        </div>
    )
}

function MyVerticallyCenteredModal(props) {
    const [cardnumber, setcardnumber] = useState("");
    const [securitycode, setsecuritycode] = useState("");
    const [balance, setbalance] = useState(0);
    const [bank_id, setbank_id] = useState(0);

    const [category, setcategory] = useState([]);
    useEffect(() => {
        fetchCategories();

    }, [])


    useEffect(() => {
        //console.log(props.edittedindex , bank_id)
        const f = async () => {
            if (props.edittedindex === -1) {
            
                setcardnumber(props.edititem['1'])
                setbalance(props.edititem['2']);
                setsecuritycode(props.edititem['3'])
                setbank_id(props.edititem['4'])
            }
        }
        f();
        return () => {
            setcardnumber("")
            setbalance(0);
            setsecuritycode("")
            //setbank_id(0)
        }
    }, [props])
    const fetchCategories = async () => {
        await baserequest.get("bank/list")
            .then(res => {
                //console.log(res.data)
                setcategory(res.data.data);
                //setbank_id(res.data[0].id)
            })
    }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        //data.append("url", img);
        data.append("cardnumber", cardnumber);
        data.append("balance", balance);
        data.append("securitycode", securitycode);
        data.append("bank_id", bank_id);

        baserequest.post("card", data)
            .then(res => {
                props.setShow(false);
                props.fetchCards();
            })
            .catch(err => console.log(err))

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("cardnumber", cardnumber);
        data.append("balance", balance);
        data.append("securitycode", securitycode);
        data.append("bank_id", bank_id);
        baserequest.post("card/" + props.edititem['0'], data)
            .then(res => {
                props.setShow(false);
                props.fetchCards();
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
                <Modal.Title>{props.edittedindex === 1 ? "Add new Card" : "Edit"}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {props.edittedindex === 1 ? (
                    <Form onSubmit={createCate}>
                        <Form.Group>
                        <Form.Label>Bank</Form.Label>
                            <Form.Control as="select" options={bank_id} onChange={e => setbank_id(e.target.value)} name="bank" id="bank">
                                {category.length > 0 && category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </Form.Control>
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Cardnumber</Form.Label>
                            <Form.Control onChange={e => setcardnumber(e.target.value)} name="cardnumber" value={cardnumber} placeholder='cardnumber' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Balance</Form.Label>
                            <Form.Control onChange={e => setbalance(e.target.value)} name="balance" value={balance} placeholder='balance' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Securitycode</Form.Label>
                            <Form.Control onChange={e => setsecuritycode(e.target.value)} name="securitycode" value={securitycode} placeholder='securitycode' />
                        </Form.Group>
                        <Button type='submit'>Submit</Button>
                    </Form>
                ) : (
                    <Form onSubmit={updateCate}>

                        <Form.Group>
                            <Form.Label>Bank</Form.Label>
                            <Form.Control as="select" defaultValue={bank_id} onChange={e => setbank_id(e.target.value)} name="bank" id="bank">
                                {category.map((item, index) => (
                                    <option key={index} value={item.id}>{item.name}</option>
                                ))}
                            </Form.Control>
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Cardnumber</Form.Label>
                            <Form.Control onChange={e => setcardnumber(e.target.value)} name="cardnumber" value={cardnumber} placeholder='cardnumber' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Balance</Form.Label>
                            <Form.Control onChange={e => setbalance(e.target.value)} name="balance" value={balance} placeholder='balance' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Securitycode</Form.Label>
                            <Form.Control onChange={e => setsecuritycode(e.target.value)} name="securitycode" value={securitycode} placeholder='securitycode' />
                        </Form.Group>

                        <Button type='submit'>Submit</Button>
                    </Form>
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

export default Card