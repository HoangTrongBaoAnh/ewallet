
import { useSelector } from 'react-redux';
import React, { useEffect, useState, forwardRef, useRef, useImperativeHandle } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import baserequest from '../../core/baserequest';
import ewalletApi from '../../core/ewalletApi';

import { FcMoneyTransfer } from 'react-icons/fc'
import { useNavigate } from 'react-router-dom';

import Pagination from '../../component/pagination/pagination';
// import Modal from '../../component/modal/modal'
const ListTransactions = forwardRef((props, ref) => {
    useImperativeHandle(ref, () => ({
        getalltransaction() {
            getalltransaction();
        },
    }))
    const navigate = useNavigate();
    const [totalPage, setTotalpage] = useState(0);
    const [currenPage, setCurrentPage] = useState(0);
    const [transc, settransc] = useState([]);
    useEffect(() => {
        if (window.localStorage.getItem("token")) {
            getalltransaction();
        }
        else {
            navigate('/login')
        }
    }, [currenPage]);

    var byYearAndByMonth = {};

    const [byYearAndByMonthP, setbyYearAndByMonthP] = useState({});
    const user = useSelector(state => state.auth.user)
    const getalltransaction = async () => {
        try {
            const res = await ewalletApi.getAllTransaction(user.id, currenPage);
            setTotalpage(res.totalPages)
            setCurrentPage(res.currentPage)
            settransc(res.transactions)
            res.transactions.map(item => {

                var year = item.created_date.substring(0, 4);
                var month = item.created_date.substring(5, 7);
                if (typeof byYearAndByMonth[year] === "undefined") {
                    byYearAndByMonth[year] = {};
                }

                if (typeof byYearAndByMonth[year][month] === "undefined") {
                    byYearAndByMonth[year][month] = [];
                }
                byYearAndByMonth[year][month].push(item);

            })

            var reverorderd = new Map(), key = [], k = [];
            Object.keys(byYearAndByMonth).sort().reverse()
                .forEach(key2 => {
                    //console.log(key)
                    k.push({ key: key2, data: [] });

                })

            k.forEach(item => {

                Object.keys(byYearAndByMonth[item.key]).forEach(item1 => {

                    item.data.push({ month: item1, data: byYearAndByMonth[item.key][item1] })
                })


            })

            const obj = Object.fromEntries(reverorderd);
            setbyYearAndByMonthP(k);
            //console.log(byYearAndByMonthP)
        }
        catch (err) {
            console.log(err.message);
        }
    }
    return (
        <div className='max-w-6xl mx-auto'>
            <div className='flex-1 text-3xl font-medium text-gray-700 pt-3'>Transaction history</div>
            {transc.length > 0 ? (
                byYearAndByMonthP.length > 0 ? (
                    byYearAndByMonthP.map((datayear, i) => (
                        <div key={i}>
                            {
                                datayear.data.map((datamonth, i1) => (
                                    <div key={i1}>
                                        <div className='p-20 font-semibold text-gray-700'>{datamonth.month} - {datayear.key}</div>
                                        {
                                            datamonth.data.map((item, index) => (
                                                <div key={index} className="flex p-20 items-center mb-4 bg-light text-dark">
                                                    <div className='font-semibold bg-green p-2'><FcMoneyTransfer className='icon' /></div>
                                                    <div className='ml-4'>
                                                        <div className='text-2xl font-semibold text-gray-700'>From: {item.froms}</div>
                                                        <div className=' font-semibold text-gray-400'>Type: {item.category}</div>
                                                        <div className=' font-semibold text-gray-400'>Date: {item.created_date}</div>
                                                    </div>
                                                    <div className='ml-auto text-2xl font-semibold'>
                                                        Amount:
                                                        {item.amount}$</div>
                                                </div>
                                            ))
                                        }
                                    </div>

                                ))
                            }
                        </div>
                    ))
                ) : null

            ) : (
                <div className='mx-auto'>
                    <div className='text-2xl font-semibold text-center text-gray-600'>
                        No Transactions as of yet
                    </div>
                    <img
                        className='mx-auto'
                        src='https://img.icons8.com/fluent/120/000000/nothing-found.png'
                        alt='not found'
                    />
                </div>
            )}
            <Pagination totalPage={totalPage} setCurrentPage={setCurrentPage} />
        </div>
    )
})

export default ListTransactions