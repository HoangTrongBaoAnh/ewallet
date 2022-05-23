import React from 'react'
import './transactions.scss';
import baserequest from '../../../core/baserequest';
import MUIDataTable from "mui-datatables";

const Transactions = () => {

    const [card, setcard] = React.useState([]);

    const fetchCards = async () => {
        await baserequest.get("transaction")
            .then(res => {
                var tmp = [];
                res.data.forEach(item => {
                    var day = new Date(item.createdDate).toLocaleString(undefined, {year: 'numeric', month: '2-digit', day: '2-digit', weekday:"long", hour: '2-digit', hour12: false, minute:'2-digit', second:'2-digit'});
                    tmp.push([item.id, item.from, item.amount,item.description, day])
                });
                setcard(tmp);
            })
    }

    React.useEffect(() => {
        fetchCards();
    }, [])

    const options = {
        filterType: 'checkbox',
        responsive: 'simple',

    };
    const columns = [
        {
            label: "ID",
            name: "ID",
            sortable: true,
            filter: true,
            options: {
                customBodyRender: (data) => {
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            label: "From",
            name: "From",
            sortable: true,
            filter: true,
            options: {
                customBodyRender: (data) => {
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Amount",
            label: "Amount",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Description",
            label: "Description",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        {
            name: "Created day",
            label: "Created day",
            options: {
                customBodyRender: (data) => {
                    //console.log(data);
                    return (
                        <div className='inputName'>{data}</div>
                    )
                }
            }
        },
        
    ]

    return (
        <div className='adminCustomer'>
           
            {/* <Button className='primary new'>
                Add Customer
            </Button> */}
              
            <MUIDataTable
                title={"Transaction List"}
                data={card}
                columns={columns}
                options={options}
            />
            
        </div>
    )
  
}

export default Transactions