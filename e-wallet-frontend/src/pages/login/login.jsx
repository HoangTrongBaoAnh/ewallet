import './login.scss';
import { Button,Form } from 'react-bootstrap'

import { useState } from 'react';
import baserequest from '../../core/baserequest';
import ewalletApi from '../../core/ewalletApi';

import { Link, useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setemail] = useState("");
    const [password, setpassword] = useState("");
    const [error, seterror] = useState("");

    const navigate = useNavigate();
    const login = async () => {
        // var axios = require('axios');
        var data = JSON.stringify({
            "username": email,
            "password": password
        });
        try {
            const res = await ewalletApi.signIn(data);
            window.localStorage.setItem("token", res.token);
            navigate(`/`);
        }
        catch (err){
            seterror(err);
        }  
    }

    return (
        <div>
            <div className="container login">
                <div className="row">
                    <div className='col-md-6 login__image'>

                    </div>
                    <div className='col-md-6 login__right'>
                        <div className='login__right__title'>
                            Log In
                        </div>
                        <div className='login__right__subtitle'>
                            <div className='login__right__subtitle__left'>
                                New User?
                            </div>
                            <div className='login__right__subtitle__right'>
                                <Link to='/register'>Create an account</Link>
                            </div>
                        </div>
                        <Form.Group className='login__right__email'>
                            <Form.Label className='login__right__email__label'>User Name</Form.Label>
                            <Form.Control isInvalid={error} value={email} onChange={e => setemail(e.target.value)} className='login__right__input' placeholder="example..." />

                        </Form.Group>

                        <Form.Group className='login__right__password'>
                            <Form.Label className='login__right_password__label'>Password</Form.Label>
                            <p>
                                <Form.Control isInvalid={error} value={password} onChange={e => setpassword(e.target.value)} type='password' className='login__right__password__input' placeholder="********" />
                                <span className='login__right__password__vector'>
                                    <span className="bi bi-eye-slash-fill"><i className="bi bi-eye-slash"></i></span>
                                </span>
                            </p>

                        </Form.Group>

                        <Form.Control.Feedback type="invalid">
                            {error}
                        </Form.Control.Feedback>


                        <button onClick={login} className='login__right__button'>
                            <span>Log In</span>
                        </button>


                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
