import React, { useEffect, useState } from 'react'
import './modal.scss'
import baserequest from '../../core/baserequest';
const Modal = ({setactive,setto}) => {
    const [username,setusername]=useState("");
    const [listuser,setlistuser]=useState([]);
    useEffect(() => {
        const getUser = async () => {
            await baserequest.get('auth/listname/'+username)
                .then(res => {
                    setlistuser(res.data)
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
        getUser();
    
      
    },[username]);
    console.log(listuser)


    function SelectTo(e,name){
        e.preventDefault();
        setto(name);
        setactive(false);
    }
  return (
    
    <>
      <div className="darkBG" onClick={() => setactive(false)}>
        
      </div>
      <div className="modala flex">
            <div className="modalaContent flex items-center ">
                <div className='modal__input flex'>
                    Search for username:  
                    <input value={username} onChange={e => setusername(e.target.value)} placeholder='search for username'/>
                </div>
                <div className='modal__list ml-auto'>
                    Chose one: 
                    {listuser!=null &&
                        listuser.map((item,index) => 
                            <p key={index} onClick={(e) => SelectTo(e,item.username)}>{item.username}</p>
                        )
                    
                    }
                </div>
          </div>
        </div>
    </>
  )
}

export default Modal