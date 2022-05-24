import React, { useEffect, useState, useRef } from 'react'
import revenue from '../../asset/revenue.png'
import ewalletApi from '../../core/ewalletApi';

import 'bootstrap/dist/css/bootstrap.min.css';

import { BiUpArrowAlt, BiDownArrowAlt } from "react-icons/bi";
import {
    Chart as ChartJS,
    ArcElement,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    SubTitle,
    Tooltip,
    Legend,
    Filler,
} from 'chart.js';

import { Line, Pie } from 'react-chartjs-2';
ChartJS.register(
    CategoryScale,
    ArcElement,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    SubTitle,
    Tooltip,
    Legend,
    Filler
);
let delayed;

export const options = {
    responsive: true,
    animation: {
        onComplete: () => {
            delayed = true;
        },
        // onProgress: function(animation) {
        //     progress.value = animation.currentStep / animation.numSteps;
        //     console.log(propgress.value)
        // },
        delay: (context) => {
            let delay = 0;
            if (context.type === 'data' && context.mode === 'default' && !delayed) {
                delay = context.dataIndex * 800 + context.datasetIndex * 600;
            }
            return delay;
        },
    },
    animations: {
        // tension: {
        //     duration: 1000,
        //     easing: 'linear',
        //     from: 1,
        //     to: 0,
        //     loop: true
        // },
        y: {
            easing: 'linear',
            from: (ctx) => {
                if (ctx.type === 'data') {
                    if (ctx.mode === 'default' && !ctx.dropped) {
                        ctx.dropped = true;
                        return 0;
                    }
                }
                //s
            }
        }
    },
    scales: {
        x: {
            beginAtZero: true,
            display: true,
            title: {
                display: true,
                text: 'Month',
                color: 'red',
                font: {
                    family: 'Comic Sans MS',
                    size: 22,
                    weight: 'bold',
                    lineHeight: 1.2,
                },
                padding: { top: 20, left: 0, right: 0, bottom: 0 },


            },
            ticks: {

                align: 'start',
                color: 'red',
                font: {
                    size: 15,
                    family: 'tahoma',
                    weight: 'bold',
                    style: 'italic'
                },
            },
            grid: {
                borderDash: [8, 4],
                color: "#348632"
            },
        },
        y: {
            beginAtZero: true,
            display: true,
            // title: {
            //     display: true,
            //     text: 'Money',
            //     color: '#191',
            //     font: {
            //         family: 'Times',
            //         size: 20,
            //         style: 'normal',
            //         lineHeight: 1.2
            //     },
            //     padding: { top: 30, left: 0, right: 0, bottom: 0 },

            // },
            ticks: {
                font: {
                    size: 15,
                    family: 'tahoma',
                    weight: 'bold',
                    style: 'italic'
                },
                color: '#fff',
                align: 'end'
            },
            grid: {
                borderDash: [8, 4],
                color: "#348632"
            },
        }
    },
    plugins: {
        tooltip: {
            //usePointStyle: true,
            callbacks: {
                label: (context) => {
                    let label = "";
                    //console.log(context)
                    if (context.parsed.y) {
                        label += " " + new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(context.parsed.y);
                        //label = context.parsed.y + "%";
                    }
                    return label;
                },
                // labelColor: function (context) {
                //     return {
                //         borderColor: "rgb(0, 0, 255)",
                //         backgroundColor: "rgb(255, 0, 0)",
                //         borderWidth: 2,
                //         borderDash: [2, 2],
                //         borderRadius: 2
                //     };
                // },
                // labelTextColor: function (context) {
                //     return "#ffff";
                // },
                // labelPointStyle: function (context) {
                //     return {
                //         pointStyle: "triangle",
                //         rotation: 0
                //     };
                // }
            }
        },
        legend: {

            labels: {
                //usePointStyle: true,

                font: {
                    size: 15,
                    family: 'tahoma',
                    weight: 'bold',
                    style: 'italic'
                },
            },
            position: 'top',
        },
        subtitle: {
            display: true,
            text: 'Year: ' + new Date().getFullYear(),
            color: 'blue',
            font: {
                size: 15,
                family: 'tahoma',
                weight: 'bold',
                style: 'italic'
            },
            padding: {
                bottom: 10
            }
        },
        title: {
            display: true,
            text: 'Transaction this year by month',
            font: {
                size: 15,
                family: 'tahoma',
                weight: 'bold',
                style: 'italic'
            },
        }
    },
};

export const byCategoriesOptions = {
    responsive: true,

    plugins: {
        tooltip: {
            //usePointStyle: true,
            // callbacks: {
            //     label: (context) => {
            //         let label = "";
            //         if (context.parsed) {
            //             label = context.parsed + " times";
            //             //label = context.parsed.y + "%";
            //         }
            //         return label;
            //     },

            // }
        },
        legend: {
            labels: {
                font: {
                    size: 15,
                    family: 'tahoma',
                    weight: 'bold',
                    style: 'italic'
                },
            },
            position: 'left',
        },

        title: {
            display: true,
            text: 'Transaction this year by month',
            font: {
                size: 15,
                family: 'tahoma',
                weight: 'bold',
                style: 'italic'
            },
        }
    },
};
const Chart = () => {
    const chartRef = useRef();
    // const onMouseOver = (event) => {
    //     var chart = getElementAtEvent(chartRef.current, event);
    //     //console.log(chart);
    //     chart[0].element.options.offset = 100;
    //     chart[0].element.options.spacing = 10;
    //     console.log(chart[0].element)
    // }
    const [chartData, setChartData] = useState([]);
    const [chartByCategoriesData, setchartByCategoriesData] = useState([]);
    const labels = chartData.map((item) => item.transcmonth);
    var months = ["", "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"];
    const [diff, setdiff] = useState(0);
    const data = {
        labels,
        datasets: [
            {
                label: 'Money',
                data: chartData.map((item) => item.amount),
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                fill: true,
                tension: 0.4
            },
        ],
    };

    const byCategoriesDataCount = {
        labels: chartByCategoriesData.map(item => item.name),
        datasets: [
            {
                data: chartByCategoriesData.map(item => item.count),
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    "#6800B4",

                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    "#6800B4",
                ],
                //hoverBackgroundColor: '#fff',
                //hoverBorderColor: '5px 10px #888888',
                hoverBorderWidth: 8,
                hoverRadius: 4,
                hoverOffset: 80,
                hoverSpacing: 30,
                hoverShadowColor: '#E56590',
                hovershadowBlur: 10,
                hovershadowOffsetX: 0,
                hovershadowOffsetY: 4,


            },
        ],
    };
    const byCategoriesDataSum = {
        labels: chartByCategoriesData.map(item => item.name),

        datasets: [
            {

                data: chartByCategoriesData.map(item => item.sum),
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    "#6800B4",

                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    "#6800B4",
                ],
                //hoverBackgroundColor: '#fff',
                //hoverBorderColor: '5px 10px #888888',
                hoverBorderWidth: 8,
                hoverRadius: 4,
                hoverOffset: 80,
                hoverSpacing: 30,
                hoverShadowColor: '#E56590',
                hovershadowBlur: 10,
                hovershadowOffsetX: 0,
                hovershadowOffsetY: 4,


            },
        ],
    };

    // const chartRef = useRef<HTMLCanvasElement>(null)

    useEffect(() => {
        const chartdata = async () => {
            try {
                const res = await ewalletApi.getDataLineChart();
                var temp = [];
                for (let i = 1; i <= 12; i++) {
                    if (typeof temp[i] === "undefined") {
                        temp[i] = { transcmonth: months[i], amount: 0 };
                    }
                }
                res.forEach(item => {
                    temp[item.transcmonth] = { ...item, transcmonth: months[item.transcmonth] };
                })
    
                var sum = 0;
                res.forEach(item => {
                    sum += item.amount;
                })
                setdiff(sum);
                temp.shift()
                setChartData(temp);
            }
            catch (err) {
                console.log(err)
            }
        }
    
        const chartByCategoriesdata = async () => {
            const res = await ewalletApi.getDataPieChart();
            setchartByCategoriesData(res)
        }

        //form_ele.classList.add('form--disabled')
        chartByCategoriesdata();
        chartdata();

    }, [])
    return (
        <div>
            <div className='pt-3 sumary'>
                <div className='sumary__container'>
                    <div className='row sumary__info'>
                        <div className='col-lg-4 col-12 mt-3'>
                            <div className=' sumary__revenue'>
                                <div>
                                    <div className='sumary__label'>Venue</div>
                                    <div className='sumary__value'>{diff > 0 ? <div><i><BiUpArrowAlt style={{ color: 'green', fontSize: '60px' }} /></i>+${diff}</div> : <div><i><BiDownArrowAlt style={{ color: 'red', fontSize: '60px' }} /></i>-${Math.abs(diff)}</div>}</div>
                                </div>
                                <img className='sumary__img ml-auto' src={revenue} alt='revenue'/>
                            </div>
                        </div>
                        <div className='col-lg-8'></div>
                        <div className='col-lg-5 col-12 mt-3 sumary__pie'>
                            <Pie ref={chartRef} data={byCategoriesDataCount} options={byCategoriesOptions} />
                        </div>
                        <div className='col-lg-2'></div>
                        <div className='col-lg-5 col-12 mt-3 sumary__pie'>
                            <Pie ref={chartRef} data={byCategoriesDataSum} options={byCategoriesOptions} />
                        </div>
                    </div>

                    <Line options={options} data={data} />

                </div>
            </div>


        </div>
    )
}

export default Chart