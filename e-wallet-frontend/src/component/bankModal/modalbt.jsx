import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';

import ewalletApi from '../../core/ewalletApi';

import './modalbt.scss'

import { Modal, Button, Form } from 'react-bootstrap'
import ToastSuccess from '../toast/ToastSuccess';
import ToastError from '../toast/ToastError';
import OtpInput from 'react-otp-input';

function MyVerticallyCenteredModal(props) {
    const [bankactive, setbankactive] = useState({});

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
                    Add account to your wallet
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <StepForm getwallets={props.getwallets} setshow={props.setshow} show={props.show} user={props.user} setbankactive={setbankactive} bankactive={bankactive} />
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}

const StepForm = ({ getwallets, setshow, show, user, setbankactive, bankactive }) => {
    const [banks, setbank] = useState([]);
    const [cardnumber, setcardnumber] = useState("");
    const [otp, setotp] = useState("");
    const [err, seterr] = useState(false);
    const [loading, setloading] = useState(false);
    // const [active, setactive] = useState(false);
    const [ocb, setocb] = useState([]);

    const toastsucc = useRef();
    const toasterr = useRef();

    const getbanks = async () => {
        const res = await ewalletApi.getBanks();
        setbank(res);
    }

    var currentTab = 0;
    const createCard = async (e) => {
        e.preventDefault();
        var card = ocb;
        let data = new FormData();
        data.append("cardnumber", card.data.accounts.account[0].accountNumber);
        data.append("balance", card.data.accounts.account[0].balance);
        data.append("securitycode", card.trace.bankRefNo);
        data.append("bank_id", 164);
        data.append("customerId", card.trace.clientTransId);

        try {
            await ewalletApi.addOcbAccount(data);
            addcard();
        }
        catch (err) {
            console.log(err.message);
        }
    }
    const addcard = async () => {
        var data = new FormData();
        data.append('userId', user.id);
        data.append('bankId', 164);
        data.append('cardnumber', cardnumber);
        data.append('otp', otp);

        try {
            await ewalletApi.addCardToWallet(data);
            seterr(false);
            setbankactive({});
            getwallets();
            setshow(false);
            toastsucc.current.notify("You have add card successfully");
        }
        catch (err) {
            console.log(err);
            seterr(true);
            //console.log(err.response.data);
            setshow(false);
            toasterr.current.notify(err);
        }
    }

    const sendotp = async () => {
        setloading(false);
        document.getElementById('nextBtn').disabled = false;
        document.getElementsByClassName('otpMailNotify')[0].style.display = "block";
        if (cardnumber) {
            setloading(true);
            try {
                const res = await ewalletApi.getSignature(cardnumber);
                var data = JSON.stringify({
                    "clientid": "a009e24f7dcc0d20596adece715f0c18",
                    "signature": res.XSignature,
                    "accountnumber": cardnumber
                });
                try {
                    const res = await ewalletApi.getAccount(data);

                    if (res.error) {
                        toasterr.current.notify(res.error.details);
                        setloading(false);
                        throw res.error;
                    }

                    setocb(res);
                    try {
                        await ewalletApi.sendSms(cardnumber);
                        setloading(false);
                        document.getElementById('nextBtn').disabled = false;
                        document.getElementsByClassName('otpMailNotify')[0].style.display = "block";
                        toastsucc.current.notify("Please check your phone")
                    }
                    catch (err) {
                        toasterr.current.notify(err.slice(16));
                        setloading(false);
                        console.log(err)
                    }
                }
                catch (err) {
                    console.log(err);
                }
            }
            catch (err) {
                console.log(err.message);
            }
        }
        else {
            toasterr.current.notify("cardnumber is null");
        }
    }

    const handle = (e, item) => {
        e.preventDefault();
        setbankactive(item);
        // setactive(true);
    }

    useEffect(() => {
        getbanks();
        var bankss = document.getElementsByClassName('bankbody');
        var prevBtn = document.getElementById('prevBtn');
        var nextBtn = document.getElementById('nextBtn');
        prevBtn.addEventListener("click", function () { nextPrev(-1) });
        nextBtn.addEventListener("click", function () { nextPrev(1) });

        setTimeout(() => {
            for (var i = 0; i < bankss.length; i++) {
                bankss[i].addEventListener("click", function () {
                    var b = document.querySelector(".bankbody.active");
                    if (b) b.classList.remove("active");
                    this.classList.add('active');
                    nextBtn.disabled = false;
                });
            }
        }, 1000);

        function showTab(n) {
            // This function will display the specified tab of the form...
            if (
                document.getElementsByClassName("tab")
            ) {
                var x = document.getElementsByClassName("tab");

                n === 0 ? x[n].style.display = "flex" : x[n].style.display = "block";

                //... and fix the Previous/Next buttons:
                if (n === 0) {
                    document.getElementById("prevBtn").style.display = "none";
                } else {
                    document.getElementById("prevBtn").style.display = "inline";
                }
                if (n === (x.length - 1)) {
                    var b = document.getElementById("nextBtn");

                    b.innerHTML = "Submit";
                    b.disabled = false;
                    //console.log(b.type)
                } else {
                    document.getElementById("nextBtn").innerHTML = "Next";
                }
            }

            //... and run a function that will display the correct step indicator:
            fixStepIndicator(n)
        }

        function nextPrev(n) {
            //prevBtn.disabled = true;
            //nextBtn.disabled = true;
            if (document.getElementsByClassName("tab")) {
                //console.log(Object.keys(bankactive).length)
                var x = document.getElementsByClassName("tab");
                // Exit the function if any field in the current tab is invalid:
                //if (n == 1 && Object.keys(bankactive).length < 0) return false;
                // Hide the current tab:
                // eslint-disable-next-line
                x[currentTab].style.display = "none";
                // Increase or decrease the current tab by 1:
                currentTab = currentTab + n;
                // if you have reached the end of the form...
                if (currentTab >= x.length) {
                    // ... the form gets submitted:
                    var b = document.getElementById("nextBtn");
                    b.removeEventListener("click", function () { nextPrev(1) })
                    b.setAttribute('type', 'submit');;
                    return false;
                }
                nextBtn.disabled = true;
                showTab(currentTab);
            }
        }

        function fixStepIndicator(n) {
            // This function removes the "active" class of all steps...
            var i, x = document.getElementsByClassName("step");
            for (i = 0; i < x.length; i++) {
                x[i].className = x[i].className.replace(" active", "");
            }
            //... and adds the "active" class on the current step:
            x[n].className += " active";
        }

        showTab(currentTab);
        return () => {
            prevBtn.removeEventListener("click", function () { nextPrev(-1) });
            nextBtn.removeEventListener("click", function () { nextPrev(1) });
            setbankactive({});
            seterr(false);
            setbank([]);
        }
    }, [show])

    return (
        <div className='stepform'>
            <Form id="regForm" onSubmit={createCard}>
                <div className='row bank tab'>
                    <h4>Choose bank</h4>
                    {
                        banks.length > 0 && user.phonenumber ? (
                            banks.map((item, index) => (
                                <div key={index} className="bankbody col-lg-3" onClick={e => handle(e, item)}>
                                    {/* <img src={"https://www.baokim.vn/new_base/images/logo_partner/Bank/" + item.short_name.toLowerCase() + '.png'} /> */}
                                    <img src={item.url} alt={item.name}/>
                                    <div className='bankbody__banktitle'>{item.name}</div>

                                </div>
                            ))
                        )
                            : (
                                <div>Please update your account information before continue <Link to={'/profilepage/' + user.username}>click here to go to profilepage</Link></div>
                            )
                    }
                </div>
                <div className='tab'>
                    {
                        Object.keys(bankactive).length > 0 ? (
                            <div className='cardnumber'>
                                <div className='cardnumber__content'>

                                    <div>CardNumber</div>
                                    <div className="cardnumber__content__form">
                                        <input value={cardnumber} onChange={e => setcardnumber(e.target.value)} type="text" />
                                        <div>
                                            <Button className='button' onClick={sendotp} >
                                                {
                                                    loading ? (
                                                        <div className="spinner-border" role="status">
                                                            <span className="sr-only"></span>
                                                        </div>
                                                    ) : null
                                                }
                                                CheckCard
                                            </Button></div>
                                    </div>
                                    <div className='otpMailNotify'>Please check your phone</div>
                                </div>
                                {err ? <div className='cardnumber__error'>wrong card number: {cardnumber}</div> : null}
                            </div>
                        ) : (<div>Bank has yet been choosen</div>)
                    }
                </div>
                <div className='tab'>
                    {
                        Object.keys(bankactive).length > 0 && cardnumber ? (
                            <div className='otpContainer'>
                                <div className='otpContainer__content'>
                                    <div className='otpContainer__title'>OTP</div>
                                    <div className="cardnumber__content__form">
                                        <OtpInput
                                            separator={
                                                <span>
                                                    <strong>-</strong>
                                                </span>
                                            }
                                            numInputs={6}
                                            isInputNum={true}
                                            shouldAutoFocus={true}
                                            value={otp} onChange={setotp}
                                            inputStyle={{
                                                width: "3rem",
                                                height: "3rem",
                                                margin: "0 1rem",
                                                fontSize: "2rem",
                                                borderRadius: 4,
                                                border: "1px solid orange"
                                            }}
                                        />
                                    </div>

                                    {/* </Form> */}
                                </div>
                                {/* {err ? <div className='cardnumber__error'>wrong card number: {cardnumber}</div> : null} */}
                            </div>
                        ) : (<div>Bank or cardnumber has yet been choosen</div>)
                    }
                </div>
                <div className='stepIndicator'>
                    <span className="step"></span>
                    <span className="step"></span>
                    <span className="step"></span>

                </div>
                <div >
                    <div className='stepbutton' style={{ float: 'right' }}>
                        <button className='button' type="button" id="prevBtn" >Previous</button>
                        <button className='button' type="button" id="nextBtn" disabled>Next</button>
                    </div>
                </div>
            </Form>
            <ToastSuccess ref={toastsucc} />
            <ToastError ref={toasterr} />
        </div>
    )
}

export default MyVerticallyCenteredModal;