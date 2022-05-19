import React, { forwardRef,useImperativeHandle  } from 'react'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './toast.scss'

const ToastSuccess = forwardRef((props, ref) =>  {
    useImperativeHandle(ref, () => ({
        notify(str) {
            notify(str);
      },
    }))

    const notify = (str) => toast.success(str, {
        icon: "🚀"
    });
    return (
        <ToastContainer />
    )
})

export default ToastSuccess