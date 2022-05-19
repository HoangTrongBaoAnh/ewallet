import React, { forwardRef,useImperativeHandle  } from 'react'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ToastSuccess = forwardRef((props, ref) =>  {
    useImperativeHandle(ref, () => ({
        notify(str) {
            ErrorNotify(str);
      },
    }))

    const ErrorNotify = (str) => toast.error(str,{
        position: "bottom-right",});
    return (
        <ToastContainer />
    )
})

export default ToastSuccess