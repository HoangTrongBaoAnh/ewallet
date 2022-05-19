import './register.scss';
import { useState } from 'react';
import baserequest from '../../core/baserequest';
import ewalletApi from '../../core/ewalletApi';

import { useNavigate } from 'react-router-dom';
import { Button,Form } from 'react-bootstrap'
const Login = () => {
    const [email, setemail] = useState("");
    const [password, setpassword] = useState("");

    const [username, setusername] = useState("");
    const navigate = useNavigate();
    const register = async (e) => {
        e.preventDefault();
        // var axios = require('axios');
        var data = JSON.stringify({
            "username": username,
            "email": email,
            "password": password
        });
        try {
            const res = await ewalletApi.register(data);
            navigate(`/login`);
            console.log(res);
        }
        catch (err){
            //seterror(err);
        }
    }

    return (
        <>
            <div className="container register">
                <div className="row">
                    <div className='container__image col-md-6'>

                    </div>
                    <div className='container__right col-md-6'>
                        <div className='container__right__title'>
                            SignUp
                        </div>
                        <Form onSubmit={register}>
                        <Form.Group className='container__right__username'>
                            <Form.Label className='container__right__username__label'>User Name</Form.Label>
                            <Form.Control value={username} onChange={e => setusername(e.target.value)} className='container__right__username__input' placeholder="John@example.com" />

                        </Form.Group>
                        <Form.Group className='container__right__email'>
                            <Form.Label className='container__right__email__label'>Email</Form.Label>
                            <Form.Control type='email' className='container__right__email__input' value={email} onChange={e => setemail(e.target.value)}  placeholder="John@example.com" />

                        </Form.Group>

                        <Form.Group className='container__right__password'>
                            <Form.Label className='container__right__password__label'>Password</Form.Label>
                            <Form.Control value={password} onChange={e => setpassword(e.target.value)} type='password' className='container__right__password__input' placeholder="********" />
                                <span className='container__right__password__vector'>
                                    <span className="bi bi-eye-slash-fill"><i className="bi bi-eye-slash"></i></span>
                                </span>
                            
                        </Form.Group>


                        <button type='submit' className='container__right__button'>
                            <span>Sign Up</span>
                        </button>
                        </Form>

                    </div>
                </div>
            </div>
        </>
    );
}

export default Login;
