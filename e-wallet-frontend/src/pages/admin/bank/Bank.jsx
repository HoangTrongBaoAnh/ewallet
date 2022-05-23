import React from 'react'
import { useState, useEffect } from 'react';
import { Button, Modal, Form } from 'react-bootstrap';
import MUIDataTable from "mui-datatables";
import './bank.scss';
import baserequest from '../../../core/baserequest';
const Bank = () => {
    const [show, setShow] = React.useState(false);
    const [edititem, setedititem] = useState({ name: "defaultvalue" });
    // const handleClose = () => setShow(false);
    const handleShow = (index, item) => {
        setedititem(item)
        seteditedindex(index);
        setShow(true);
    };

    const [banks, setbanks] = React.useState([]);


    const [edittedindex, seteditedindex] = useState(1);
    const fetchCategories = async () => {
        await baserequest.get("bank")
            .then(res => {
                console.log(res.data);
                var tmp = [];
                res.data.forEach(item => {
                    tmp.push([item.id, item.name, item.url])
                })
                setbanks(tmp);
            })
    }

    const deleteCate = (id) => {
        baserequest.delete("bank/" + id)
            .then(res => {
                fetchCategories()
            })

    }

    React.useEffect(() => {
        fetchCategories();

    }, [])

    const options = {
        filterType: 'checkbox',
        responsive: 'scrollMaxHeight',

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
            label: "Name",
            name: "Name",
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
            name: "Image",
            label: "Image",
            options: {
                customBodyRender: (data) => {
                    return (
                        <img className='inputImage' src={data} alt='img'/>
                    )
                }
            }
        },
        {
            name: "Action",
            label: "Action",
            options: {
                customBodyRender: (Data,rowData) => {
                    //console.log(rowData.rowData);
                    var obj = rowData.rowData.reduce(function(acc, cur, i) {
                        console.log(cur);
                        acc[i] = cur;
                        return acc;
                      }, {});
                    console.log(rowData);
                    return (
                        <div className='adminBank__button'>
                            <button className='primary edit' onClick={e => handleShow(-1, obj)}><i className='bx bx-edit'></i>Edit</button>
                            <button className='primary delete' onClick={e => deleteCate(obj['0'])}><i className='bx bx-trash'></i></button>
                        </div>
                    )
                }
            }
        },
    ];
    return (
        <div className='adminBank'>
            <Button className='primary new' onClick={e => handleShow(1)}>
                Add bank
            </Button>
            
            <MyVerticallyCenteredModal
                show={show}
                setbanks={setbanks}
                setShow={setShow}
                onHide={() => setShow(false)}
                edittedindex={edittedindex}
                edititem={edititem}
                setedititem={setedititem}
                fetchCategories={fetchCategories}
            />
            <MUIDataTable
                title={"Bank List"}
                data={banks}
                columns={columns}
                options={options}
            />
        </div>
    )
}

function MyVerticallyCenteredModal(props) {
    const [name, setname] = useState("");
    const [img, setimg] = useState(null);
    useEffect(() => {
        if (props.edittedindex === -1) {
            setname(props.edititem['1'])
        }
        return () => {
            setname("")
        }
    }, [props.show,props.edittedindex,props.edititem])
    // const fetchCategories = async () => {
    //     await baserequest.get("bank")
    //         .then(res => props.setbanks(res.data))
    // }

    const createCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        data.append("url", img);
        data.append("name", name);
        //console.log(name,img);
        baserequest.post("bank", data)
            .then(res => {
                props.setShow(false);
                props.fetchCategories()
            })

    }

    const updateCate = (e) => {
        e.preventDefault();
        let data = new FormData();
        if (img != null) {
            data.append("url", img);
        }
        data.append("name", name);
        //console.log(name,img);
        baserequest.put("bank/" + props.edititem['0'], data)
            .then(res => {
                props.setShow(false);
                props.fetchCategories();
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
                <Modal.Title>{props.edittedindex === 1 ? "Add new Category" : "Edit"}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {props.edittedindex === 1 ? (
                    <Form onSubmit={createCate}>
                        <Form.Group>
                            <Form.Label>Name</Form.Label>
                            <Form.Control onChange={e => setname(e.target.value)} name="name" value={name} placeholder='name' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Image</Form.Label>
                            <Form.Control onChange={e => setimg(e.target.files[0])} type="file" />
                        </Form.Group>
                        <Button type='submit'>Submit</Button>
                    </Form>
                ) : (
                    <Form onSubmit={updateCate}>
                        <Form.Group>
                            <Form.Label>name</Form.Label>
                            <Form.Control onChange={e => setname(e.target.value)} name="name" value={name} placeholder='name' />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Image</Form.Label>
                            <Form.Control onChange={e => setimg(e.target.files[0])} type="file" />
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

export default Bank