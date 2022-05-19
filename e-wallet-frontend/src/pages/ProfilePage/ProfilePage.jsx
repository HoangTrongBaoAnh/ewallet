import React, { useEffect, useState } from 'react'
import { Form, Button, Tabs, Tab, Row, Col, Nav } from 'react-bootstrap'
import './profilepage.scss';
import ewalletApi from '../../core/ewalletApi';
import baserequest from '../../core/baserequest';
import ListTransactions from '../../component/ListTransaction/ListTransactions';
import { useSelector } from 'react-redux'
import 'boxicons/css/boxicons.min.css'
import Chart from '../../component/Chart/Chart';


const ProfilePage = () => {
    const user = useSelector(state => state.auth.user);
    const [phonenumber, setphonenumber] = useState(user.phonenumber);
    const [firstname, setfirstname] = useState(user.firstName);
    const [lastname, setlastname] = useState(user.lastName);
    const [togglerActive, settogglerActive] = useState(false);
    const form_ele = document.getElementsByClassName("profileInfo__form");

    useEffect(() => {
        //form_ele.classList.add('form--disabled')
        disableFormEdit(form_ele[0]);

    }, [])

    const disableFormEdit = function (selector) {
        selector.classList.add('form--disabled');
        selector.classList.remove('form--enabled');
        for (var i = 0; i < selector.childNodes.length; i++) {
            //console.log(selector.childNodes[i].childNodes[1])
            selector.childNodes[i].childNodes[1].disabled = true;
        }

    }

    const enableFormEdit = function (selector) {
        selector.classList.remove('form--disabled');
        selector.classList.add('form--enabled');
        for (var i = 0; i < selector.childNodes.length; i++) {
            //console.log(selector.childNodes[i].childNodes[1])
            selector.childNodes[i].childNodes[1].disabled = false;
        }
    }

    const toggler = () => {
        settogglerActive(!togglerActive);
        var form_status = togglerActive ?  'enabled': 'disabled';
        switch (form_status) {
            case 'disabled':
                enableFormEdit(form_ele[0]);
                //   $(this).text('undo')
                break;
            case 'enabled':
                disableFormEdit(form_ele[0]);
                //   $(this).text('click to edit')
                break;
        }
    }

    const editUser = async (event) => {
        event.preventDefault();
        var data = JSON.stringify({
            "phonenumber": phonenumber,
            "firstName": firstname,
            "lastName": lastname,
        });
        try{
            await ewalletApi.editUser(user.id,data);
        }
        catch (err) {
            console.log(err)
        }
    }

    return (
        <div className='profilePage'>
            <div className='wallPaper'>

            </div>
            <div>
                <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                    <Row>
                        <Col lg={2}>
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                    <Nav.Link eventKey="first">Profile Information</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link eventKey="second">Transaction History</Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Col>
                        <Col lg={10}>
                            <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <div className='max-w-6xl mx-auto'>
                                        <Chart />
                                        <div className='profileInfo'>
                                            <div className="profileInfo__container">
                                                <div className='profileInfo__header flex font-medium text-gray-700 pt-3' style={{ alignItems: "center" }}>
                                                    <div className='text-3xl profileInfo__title'>Profile Information</div>
                                                    <div className='ml-auto'><button className='btnEdit' onClick={toggler}>Edit <i className='bx bx-edit'></i></button></div>
                                                </div>
                                                <div >
                                                    <div className='profileInfo__unChangeInfo'>
                                                        <div>{user.username}</div>
                                                        <div>{user.balance} $</div>
                                                    </div>


                                                    <Form className='profileInfo__form items-center' onSubmit={editUser}>

                                                        <Form.Group className='row' controlId="firstname">
                                                            <Form.Label className='col-2'>firstname</Form.Label>
                                                            <Form.Control className='col-9' disabled={false} value={firstname} onChange={e => setfirstname(e.target.value)} placeholder="Enter amount" />

                                                        </Form.Group>
                                                        <Form.Group className='row' controlId="lastname">
                                                            <Form.Label className='col-2'>lastname</Form.Label>
                                                            <Form.Control className='col-9' disabled={false} value={lastname} onChange={e => setlastname(e.target.value)} placeholder="Enter amount" />

                                                        </Form.Group>

                                                        <Form.Group className='row' controlId="formBasicEmail">
                                                            <Form.Label className='col-2'>phonenumber</Form.Label>
                                                            <Form.Control className='col-9' disabled={false} value={phonenumber} onChange={e => setphonenumber(e.target.value)} placeholder="Enter amount" />

                                                        </Form.Group>


                                                        <div className='button'>
                                                            <div></div>
                                                            <Button className='btnSave' type="submit">
                                                                Save
                                                            </Button>
                                                        </div>

                                                    </Form>
                                                </div>
                                            </div>

                                        </div>

                                    </div>
                                </Tab.Pane>
                                <Tab.Pane eventKey="second">
                                    <ListTransactions />
                                </Tab.Pane>
                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </div>
        </div>
    )
}

export default ProfilePage
