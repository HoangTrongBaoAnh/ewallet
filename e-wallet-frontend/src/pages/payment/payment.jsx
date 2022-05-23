import React, { useState,useRef } from 'react'
import { useParams } from 'react-router-dom'
import { useSelector } from "react-redux";
import { Form, Button } from 'react-bootstrap'
import ewalletApi from '../../core/ewalletApi';

import './payment.scss'

import ExampleBill from '../../asset/bill.jpg'
import ToastSuccess from '../../component/toast/ToastSuccess';
import ToastError from '../../component/toast/ToastError';

const Payment = () => {
  //useParams
  const param = useParams();

  //useRef
  const toasterr = useRef();
  const toastsucc = useRef();

  //useSelector
  const user = useSelector(state => state.auth.user);

  //useState
  const [step, setstep] = useState(1);
  const [billinfo, setbillinfo] = useState({});
  const [customercode, setcusstomercode] = useState("");
  const [status, setstatus] = useState(false);
  
  //function from here
  const nextStep = () => {
    setstep(step + 1);
  };

  // Go to prev step
  const prevStep = () => {
    setstep(step - 1);
  };

  const handleStep = (val) => {
    if (val !== step) {
      setstep(val);
      console.log(val)
    }

  }
  const RenderSwitch = () => {
    switch (step) {
      case 1:
        return <CustomerInput toasterr={toasterr} showModalImage={showModalImage} nextStep={nextStep} param={param} customercode={customercode} setcusstomercode={setcusstomercode} setbillinfo={setbillinfo} />;
      case 2:
        return <BillInfo toasterr={toasterr} toastsucc={toastsucc} billinfo={billinfo} prevStep={prevStep} setstatus={setstatus} status={status} />;
      default:
        return <h1>User Forms not working. Enable Javascript!</h1>;

    }
  }

  const showModalImage = () => {
    var modal = document.getElementById("myModal");
    var modalImg = document.getElementById("img01");
    var captionText = document.getElementById("caption");
    modal.style.display = "block";
    modalImg.src = ExampleBill;
    captionText.innerHTML = "Example";
  }

  const closeModalImage = () => {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
  }

  return (
    <div className='payment p-12'>
      <div className='flex items-center max-w-6xl gap-4 mx-auto my-20'>
        <img src='https://img.icons8.com/color/96/000000/wallet--v2.png' alt='wallet' />
        <div>
          <div className='text-4xl font-medium text-blue-500'>{user.username}</div>
          <div className='text-xl font-medium text-gray-400'>Your Balance: {user.balance}$</div>
        </div>
      </div>
      <div className='stepform'>
        <RenderSwitch />
        <div className='stepIndicator'>
          {[...Array(2)].map((e, i) => {
            return <span key={i} className={"step " + (i === step - 1 ? 'active' : '')} onClick={() => handleStep(i + 1)}></span>
          })}

        </div>
      </div>
      <div id="myModal" className="modalImage">
        <span className="close" onClick={closeModalImage}>&times;</span>
        <img className="modalImage-content" id="img01" alt='example'/>
        <div id="caption"></div>
      </div>
      <ToastError ref={toasterr} />
      <ToastSuccess ref={toastsucc} />
    </div>
  )
}

const CustomerInput = (props) => {
  const [error, seterror] = useState("");
  const [customercode, setcusstomercode] = useState(props.customercode);

  const getbillinfo = async (event) => {
    event.preventDefault();
    seterror("");
    try{
      const res = await ewalletApi.getBillInfo(customercode, props.param.name)
      props.setbillinfo(res);
      props.setcusstomercode(customercode);
      props.nextStep();
    }
    catch (err){
      seterror(err);
      console.log(err)
      props.toasterr.current.notify(err);
    }
  }

  return (
    <div className='CustomerInput'>
      <div className='header'>
        <p>Customer infomation</p>
      </div>
      <Form onSubmit={getbillinfo}>
        <div className='row'>
          <div className="col-12 col-lg-6 billInfo">
            <Form.Group className='row' controlId="formBasicEmail">
              <Form.Label className='col-4'>Customer Code</Form.Label>
              <Form.Control isInvalid={error} className='col-4' value={customercode} onChange={e => setcusstomercode(e.target.value)} placeholder="Enter code" />
            </Form.Group>
          </div>
          <div className='col-12 col-lg-6 billImageExample'>
            <img id="myImg" src={ExampleBill} alt="Snow" onClick={props.showModalImage} />
          </div>
        </div>
        <div className='button'>
          <Button className='btnNext' type="submit">
            Continue
          </Button>
        </div>
      </Form>

    </div>
  );
}

const BillInfo = (props) => {
  const billinfo = props.billinfo;
  const buttonRef = useRef(null);

  const transaction = async (event) => {
    event.preventDefault();
    var data = JSON.stringify({
      "to": null,
      "description": "Thanh toán hóa đơn",
      "amount": props.billinfo.amount,
      "TransactionCategory": "payment",
      "billInfoId": props.billinfo.id
    });
    try{
      await ewalletApi.transactionPayBill(data);
      buttonRef.current.className = buttonRef.current.className + ' unactive';
      buttonRef.current.setAttribute("disabled", "");
      props.setstatus(true);
      props.toastsucc.current.notify("Pay successfully!!");
    }
    catch (err){
      console.log(err);
      props.toasterr.current.notify(err);
    }
  }
  return (
    <div className='billInfo'>
      <div className='header'>
        <p>Bill infomation</p>
      </div>
      <div >
        {Object.keys(billinfo).length ? (
          <Form className='billInfo__container' onSubmit={transaction}>

            <Form.Group className='row billInfo__row' controlId="formBasicEmail">
              <Form.Label className='billInfo__label col-12 col-lg-6'>Amount</Form.Label>
              <div className='billInfo__value col-12 col-lg-6'>${billinfo.amount}</div>
            </Form.Group>

            <Form.Group className='row billInfo__row' controlId="formBasicPassword">
              <Form.Label className='billInfo__label col-12 col-lg-6'>Address</Form.Label>
              <div className='billInfo__value col-12 col-lg-6'>{billinfo.address} </div>
            </Form.Group>

            <Form.Group className='row billInfo__row' controlId="formBasicPassword">
              <Form.Label className='billInfo__label col-12 col-lg-6'>Customer Name</Form.Label>
              <div className='billInfo__value col-12 col-lg-6'>{billinfo.customerName} </div>
            </Form.Group>

            <Form.Group className='row billInfo__row' controlId="formBasicPassword">
              <Form.Label className='billInfo__label col-12 col-lg-6'>Customer Code</Form.Label>
              <div className='billInfo__value col-12 col-lg-6'>{billinfo.customercode} </div>
            </Form.Group>

            <div className='button'>
              <Button className='btnBack' onClick={props.prevStep}>Back</Button>
              {props.status === false ? <Button ref={buttonRef} className='btnNext' type="submit">
                Proceed to pay
              </Button> : null}
            </div>

          </Form>
        ) : null}
      </div>
    </div>
  )
}

export default Payment