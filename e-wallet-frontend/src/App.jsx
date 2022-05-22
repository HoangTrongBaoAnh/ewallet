import React, { useEffect, useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';

import baserequest from './core/baserequest'
import ewalletApi from './core/ewalletApi'
import { fetchUserAsync } from './store/auth/authreducer';
import socket from './socket/socket'

import { Form, Button, Tabs, Tab } from 'react-bootstrap'
import { FcMoneyTransfer } from 'react-icons/fc'
import { BiUpArrowAlt, BiDownArrowAlt } from "react-icons/bi";
import WithDrawIcon from './asset/withdraw.png';
import TopUpICon from './asset/topup.png';
import TranferMoneyIcon from './asset/tranfermoney.png';
import BillPaymentIcon from './asset/billPayment.png';
import MyVerticallyCenteredModal from './component/bankModal/modalbt';
import ToastSuccess from './component/toast/ToastSuccess';
import ToastError from './component/toast/ToastError';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.scss';

const App = () => {
  const dispatch = useDispatch();

  const user = useSelector(state => state.auth.user);

  const [balance, setbalance] = useState(10);
  const [withdraw, setwithdraw] = useState(10);
  const [modalShow, setModalShow] = useState(false);
  const [activewallet, setactivewallet] = useState({});
  const [wallet, setwallet] = useState([]);
  const [transc, settransc] = useState([]);
  const [category, setcategory] = useState([]);

  const childFunc = React.useRef();
  const toasterr = React.useRef();

  useEffect(() => {
    // if (!window.localStorage.getItem("token")) {
    //   return;
    // }
    dispatch(fetchUserAsync());
    getserviceCategory();
    getwallets();
    socket.connect();
    waitForUser();
    return () => {
      socket.disconnect();
    };
  }, []);

  const waitForUser = async () => {
    await baserequest.post('auth/userDetail')
      .then(res => {
        getalltransaction(res.data.id)
        socket.on('sendDataServer', dataGot => {
          // console.log(dataGot.data.id + ' - ' + user.username)
          if (dataGot.data.id == res.data.username) {
            childFunc.current.notify(dataGot.data.content);
            getalltransaction(res.data.id);
          }
        })
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  const getalltransaction = async (userId) => {
    try {
      const res = await ewalletApi.getTransactionPage0(userId);
      const temp = [];
      res.transactions.map(item => {
        switch (item.category) {
          case "cashin":
            temp.push({ ...item, icon: TopUpICon, increment: true });
            break;
          case "cashout":
            temp.push({ ...item, icon: WithDrawIcon, increment: false });
            break;

          case "payment":
            temp.push({ ...item, icon: BillPaymentIcon, increment: false });
            break;

          case "transfermoney":
            temp.push({ ...item, icon: TranferMoneyIcon, increment: item.amount > 0 ? true : false });
            break;

          default:
            temp.push(item);
            break;
        }
      })
      settransc(temp);
      // console.log(res);
    }
    catch (err) {
      console.log(err.message);
    }
  }

  const getwallets = async () => {
    try {
      const res = await ewalletApi.getWallet();
      setwallet(res)
      res.map(item => {
        if (item.active) {
          setactivewallet(item);
        }
      })
    }
    catch (err) {
      console.log(err.message);
    }
  }

  const getserviceCategory = async () => {
    try {
      const res = await ewalletApi.getServiceCagetory();
      console.log(res)
      setcategory(res);
    }
    catch (err) {
      console.log(err.message);
    }
  }

  const cashin = async (event) => {
    event.preventDefault();
    var data = JSON.stringify({
      "to": user.username,
      "description": "Nạp tiền",
      "amount": balance,
      "transactionCategory": "cashin"
    });
    try {
      await ewalletApi.cashOut(activewallet.cardNumber, data);
      childFunc.current.notify("Cash in successfully");
      getalltransaction(user.id);
      getwallets()
      dispatch(fetchUserAsync());
    }
    catch (err) {
      //console.log(err);
      toasterr.current.notify(err);
    }
  }

  const cashout = async (event) => {
    event.preventDefault();
    var data = JSON.stringify({
      "to": user.name,
      "description": "rút tiền",
      "amount": -withdraw,
      // "TransactionCategory_id": 4
      "transactionCategory": "cashout"
    });
    try {
      await ewalletApi.cashOut(activewallet.cardNumber, data);
      childFunc.current.notify("Cash out successfully");
      getalltransaction(user.id);
      getwallets()
      dispatch(fetchUserAsync());
    }
    catch (err) {
      //console.log(err);
      toasterr.current.notify(err);
    }
  }

  const removewallet = async (item) => {
    if (window.confirm("Delete the item?")) {
      try {
        await ewalletApi.removeWallet(item.cardNumber);
        getwallets();
      }
      catch (err) {
        console.log(err.message);
      }
    }
  }

  const setwalletactive = async (item) => {
    console.log(item)
    if (window.confirm("set acive this card")) {
      try {
        await ewalletApi.setWalletActive(item.cardNumber);
        getwallets();
      }
      catch (err) {
        console.log(err.message);
      }
    }
  }

  return (
    <div className="App">
      <div className='row app__section'>
        <div className='col-lg-8  app__section__left'>
          <div className='app__section__left__wallet'>
            <div className="app__section__left__wallet__header">
              <h2>My wallet</h2>
              <div className='ml-auto'>
                <Button variant="primary" onClick={() => setModalShow(true)}>
                  <i className='bx bx-plus'></i>
                </Button>
                <MyVerticallyCenteredModal
                  user={user}
                  setshow={setModalShow}
                  getwallets={getwallets}
                  show={modalShow}
                  onHide={() => setModalShow(false)}
                  toasterr={toasterr}
                />
              </div>
            </div>

            {activewallet != null ? (
              <div className='app__section__left__wallet__card'>
                <div className="app__section__left__wallet__card__bankname">
                  <i className='bx bxs-bank'></i> Bank_name
                </div>
                <div className='app__section__left__wallet__card__chip'>
                </div>
                <div className="app__section__left__wallet__card__cardnumber">
                  {activewallet.cardNumber}
                </div>
                <div className="app__section__left__wallet__card__bottom">
                  <div>
                    {user.username}
                  </div>
                  <div>
                    VISA
                  </div>
                </div>
              </div>
            ) : null}

            <div className="app__section__left__wallet__content">
              <h2>All cards</h2>
              {wallet.length > 0 ? (
                wallet.map((item, index) => (
                  <div key={index} className={`app__section__left__wallet__list ${item.active ? 'active' : ''}`}>
                    <div className='app__section__left__wallet__list__info'>
                      <div className="app__section__left__wallet__list__info__cardnumber">
                        Card Number: {item.cardNumber}
                      </div>
                      <div className="app__section__left__wallet__list__info__balance">
                        Current balance: {item.balance} $
                      </div>
                    </div>
                    <div>
                      <Form.Check
                        checked={item.active}
                        onChange={e => setwalletactive(item)}
                        type="switch"
                        id="custom-switch"
                        label="set active"
                      />
                    </div>
                    <div className="app__section__left__wallet__list__info__button">
                      <div>Remove</div>
                      <button onClick={e => removewallet(item)}><i className='bx bx-minus'></i></button>
                    </div>
                  </div>
                ))
              ) : <div>Not have any wallet</div>}
            </div>
          </div>
          <div className='App__title'>
            <div><i className='bx bx-wallet'></i> Your balance: {user.balance} $</div>
          </div>
          <div className='app__section__left__transaction'>
            <div className='app__section__left__transaction__header' >
              <div className='app__section__left__transaction__header__title'>
                <div>Lasted Transaction</div>
              </div>
              <button className='app__section__left__transaction__header__button'>
                <Link to='/transaction'>More</Link>
              </button>
            </div>
            <div className="app__section__left__transaction__content">
              {
                transc.length > 0 ? (
                  transc.map((item, index) => (
                    <div key={index} className="flex p-20 items-center mb-4 bg-light text-dark">
                      <div className='font-semibold bg-green p-2'>
                        <img src={item.icon} className='icon' />
                        {/* <FcMoneyTransfer className='icon' /> */}
                      </div>
                      <div className='ml-4'>
                        <div className='text-2xl font-semibold text-gray-700'>From: {item.froms}</div>
                        <div className=' font-semibold text-gray-400'>Type: {item.category}</div>
                        <div className=' font-semibold text-gray-400'>Date: {item.created_date}</div>
                      </div>
                      <div className='ml-auto text-2xl font-semibold'>
                        {/* Amount: */}
                        {item.increment ? <div><i><BiUpArrowAlt style={{ color: 'green', fontSize: '60px' }} /></i>+${item.amount}</div> : <div><i><BiDownArrowAlt style={{ color: 'red', fontSize: '60px' }} /></i>-${Math.abs(item.amount)}</div>}
                        {/* {item.amount}$ */}
                      </div>
                    </div>
                  ))
                ) : null
              }
            </div>
          </div>
        </div>

        <div className='col-lg-4 app__section__right'>
          <div className="app__section__right__cash">
            <Tabs
              defaultActiveKey="home"
              transition={false}
              id="noanim-tab-example"
            >
              <Tab eventKey="home" title="Cash In">
                <Form className='' onSubmit={cashin}>
                  <Form.Group controlId="formBasicEmail">
                    <Form.Label>Amount</Form.Label>
                    <Form.Control value={balance} onChange={e => setbalance(e.target.value)} min="10" type='number' placeholder="Enter amount" />
                  </Form.Group>
                  <div className='button'>
                    <Button variant="success" type="submit">
                      Cash in
                    </Button>
                  </div>
                </Form>
              </Tab>
              <Tab eventKey="profile" title="Cash out">
                <Form className='' onSubmit={cashout}>
                  <Form.Group controlId="formBasicEmail">
                    <Form.Label>Amount</Form.Label>
                    <Form.Control value={withdraw} onChange={e => setwithdraw(e.target.value)} min="10" type='number' placeholder="Enter amount" />
                  </Form.Group>
                  <div className='button'>
                    <Button variant="warning" type="submit">
                      Cash out
                    </Button>
                  </div>
                </Form>
              </Tab>
            </Tabs>
          </div>

          <div className="app__section__right__service">
            <div className='app__section__right__service__title'>Service</div>
            {category.length < 0 ? (<div>not found</div>) : (
              category.map((item, index) => (
                <Link key={index} to={`/payment/` + item.id} >
                  <div className='app__section__right__service__item' >
                    <div><img className='app__section__right__service__item__img' src={item.url} /></div>
                    <div className='ml-auto'>{item.name} <span><i className='bx bx-right-arrow'></i></span></div>
                  </div>
                </Link>
              ))
            )}
          </div>

        </div>
      </div>
      <ToastSuccess ref={childFunc} />
      <ToastError ref={toasterr} />
    </div>
  );
}

export default App;
