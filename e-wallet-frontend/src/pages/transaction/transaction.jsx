import React, { useEffect, useState } from 'react'
import { useSelector, useDispatch } from "react-redux";
import { withRouter } from '../../router/withRouter';
import ewalletApi from '../../core/ewalletApi';
import socket from '../../socket/socket'
import { fetchUserAsync } from '../../store/auth/authreducer';

import 'bootstrap/dist/css/bootstrap.min.css';
import './transaction.scss'

import { Modal, Form, Button } from 'react-bootstrap'
import ListTransactions from '../../component/ListTransaction/ListTransactions';
import ToastSuccess from '../../component/toast/ToastSuccess';
import ToastError from '../../component/toast/ToastError';


const Transaction = () => {
    const dispatch = useDispatch();

    const childFunc = React.useRef();
    const toasterr = React.useRef();
    const toastsucc = React.useRef();

    const user = useSelector(state => state.auth.user);
    const [to, setto] = useState("");
    const [amount, setamount] = useState(10);
    const [desc, setdesc] = useState("");
    const [modalShow, setModalShow] = React.useState(false);

    useEffect(() => {
        socket.connect();
        return () => {
            socket.disconnect();
        };
    }, []);

    const transaction = async (event) => {
        event.preventDefault();
        var data = JSON.stringify({
            "to": to,
            "description": desc,
            "amount": amount,
            "TransactionCategoryId": 1,
            "TransactionCategory": "transfermoney"
        });
        const msg = {
            content: `You have received $${amount} from  ${user.username}`,
            id: to
        }
        try {
            await ewalletApi.tranferMoney(data);
            childFunc.current.getalltransaction();
            toastsucc.current.notify("Transfer to user " + to + " successfully");
            socket.emit('sendDataClient', msg);
            dispatch(fetchUserAsync());
        }
        catch (error) {
            console.log(error);
            toasterr.current.notify(error);
        }
    }
    return (
        <div className='transaction'>

            <div className='mx-auto'>
                <div className='flex items-center max-w-6xl gap-4 mx-auto my-20'>
                    <img src='https://img.icons8.com/color/96/000000/wallet--v2.png' alt='wallet' />
                    <div>
                        <div className='text-4xl font-medium text-blue-500'>{user.username}</div>
                        <div className='text-xl font-medium text-gray-400'>Your Balance: {user.balance}$</div>
                    </div>
                </div>
                <div className='max-w-6xl mx-auto'>
                    <Form className=' items-center' onSubmit={transaction}>
                        <div className='flex'>
                            <Form.Group className="m-3 w-10" controlId="formBasicEmail">
                                <Form.Label>Amount</Form.Label>
                                <Form.Control value={amount} onChange={e => setamount(e.target.value)} min="10" type='number' placeholder="Enter amount" />

                            </Form.Group>

                            <Form.Group className="m-3 w-10" controlId="formBasicPassword">
                                <Form.Label>To</Form.Label>
                                <Form.Control value={to} onClick={() => setModalShow(true)} onChange={e => setto(e.target.value)} placeholder="Receiver" />
                            </Form.Group>
                        </div>


                        <Form.Group className="m-3" controlId="formBasicDesc">
                            <Form.Label>Description</Form.Label>
                            <Form.Control as="textarea" rows={3} value={desc} onChange={e => setdesc(e.target.value)} placeholder="Description" />
                        </Form.Group>

                        <div className='button m-3'>
                            <Button className='button__success' variant="success" type="submit">
                                Tranfer Money
                            </Button>
                        </div>

                    </Form>
                </div>
                <ListTransactions ref={childFunc} />
            </div>

            <MyVerticallyCenteredModal
                show={modalShow}
                setto={setto}
                setshow={setModalShow}
                onHide={() => setModalShow(false)}
            />
            <ToastError ref={toasterr} />
            <ToastSuccess ref={toastsucc} />

        </div>
    )
}

function MyVerticallyCenteredModal(props) {
    const [username, setusername] = useState("");
    const [listuser, setlistuser] = useState([]);
    useEffect(() => {
        const getUser = async () => {
            try {
                const res = await ewalletApi.findUserByUserName(username);
                setlistuser(res)
            }
            catch (err) {
                console.log(err)
            }
        }
        getUser();
    }, [username]);
    console.log(listuser)


    function SelectTo(e, name) {
        e.preventDefault();
        props.setto(name);
        props.setshow(false);

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
                <Modal.Title id="contained-modal-title-vcenter">
                    Receiver
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h4>Chose another user to transfer</h4>
                <div className="transactionModal">
                    <div className="transactionModal__content">
                        <div className='transactionModal__content__input'>
                            <div className='transactionModal__content__input__label'>Username:</div>
                            <div className='transactionModal__content__input__searchbox'>
                                <i className="bx bx-search-alt"></i>
                                <input value={username} onChange={e => setusername(e.target.value)} placeholder='Search for username' />

                            </div>
                        </div>
                        <div className='transactionModal__content__list'>
                            {listuser != null &&
                                listuser.map((item, index) =>
                                    <div className='transactionModal__content__list__item' key={index} onClick={(e) => SelectTo(e, item.username)}>
                                        <div className='transactionModal__content__list__item__left'>{item.username}</div>
                                        <div className='transactionModal__content__list__item__right'>{item.balance} $</div>
                                    </div>
                                )

                            }
                        </div>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default withRouter(Transaction)