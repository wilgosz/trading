function simulator_option(obj) {
    return  {
        noData: {
            text: 'Loading...'
        },
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        series: [{
                name: "Buy",
                data: obj.buy
            }],
        chart: {
            toolbar: {
                show: false
            },
            height: 150,
            id: 'simulation',
            group: 'ind',
            type: 'area',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: 1,
            colors: ["#2255a4"],
            dashArray: [0, 0]
        },

        title: {
            text: 'Symulation',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}

function price_option(obj) {
    return  {
        noData: {
            text: 'Loading...'
        },
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        series: [{
                name: "Close Price",
                data: obj.price
            }],
        chart: {
            toolbar: {
                show: false
            },
            height: 400,
            id: 'price',
            group: 'ind',
            type: 'area',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: 1,
            colors: ["#2255a4"],
            dashArray: [0, 0]
        },

        title: {
            text: 'Close Price',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}
function rsi_option(obj) {
    return {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        annotations: rsi_annotation(obj),
        series: rsi_series(obj),
        chart: {
            toolbar: {
                show: false
            },
            height: 180,
            id: 'rsi',
            group: 'ind',
            type: 'line',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: 1,
            colors: ["#2255a4"],
            dashArray: [0, 0]
        },

        title: {
            text: 'RSI',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            min: 0,
            max: 100,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}
function stochastic_option(obj) {
    return {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        annotations: {
            yaxis: [
                {
                    y: obj.add[0],
                    borderColor: '#000000',
                },
                {
                    y: obj.add[1],
                    borderColor: '#000000',
                }
            ]
        },
        colors: ["#ff1a1a", "#008200"],
        series: stochastic_series(obj),
        chart: {
            toolbar: {
                show: false
            },
            height: 180,
            id: 'sto',
            group: 'ind',
            type: 'line',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: 1,
            colors: ["#ff1a1a", "#008200"],
            dashArray: [0, 0]
        },

        title: {
            text: 'Stochastic',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            min: 0,
            max: 100,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}
function sma_option(obj) {
    return {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        colors: ["#ff1a1a"],
        series: sma_series(obj),
        chart: {
            toolbar: {
                show: false
            },
            height: 180,
            id: 'sma',
            group: 'ind',
            type: 'area',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: 1,
            dashArray: [0, 0]
        },

        title: {
            text: 'SMA',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}
function macd_option(obj) {
    return {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        series: macd_series(obj),
        chart: {
            toolbar: {
                show: false
            },
            height: 180,
            id: 'macd',
            group: 'ind',
            type: 'line',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        colors: ["#ffb848"],
        stroke: {
            curve: 'straight',
            width: 1,
            colors: ["#ff1a1a"]
        },

        title: {
            text: 'MACD',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: [{
                show: true,
                axisBorder: {
                    show: true,
                    color: '#78909C',
                    offsetX: 0,
                    offsetY: 0
                },
                labels: {
                    formatter: (value) => {
                        return value.toFixed(2)
                    },
                    minWidth: 60,
                    maxWidth: 60
                }
            }],
        legend: {
            show: false
        }
    };
}
function bollinger_option(obj) {
    return  {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        series: bollinger_series(obj),
        colors: ['#28b779', '#da542e', '#fb8c00', '#20c997'],
        chart: {
            toolbar: {
                show: false
            },
            height: 400,
            id: 'boll',
            group: 'ind',
            type: 'line',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: [1, 2, 1, 1],
            dashArray: [0, 0]
        },

        title: {
            text: 'Bollinger Band',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}
function moneyflow_option(obj) {
    return {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40
            }
        },
        annotations: moneyflow_annotation(obj),
        series: moneyflow_series(obj),
        chart: {
            toolbar: {
                show: false
            },
            height: 180,
            id: 'rsi',
            group: 'ind',
            type: 'line',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: 1,
            colors: ["#da542e"],
            dashArray: [0, 0]
        },

        title: {
            text: 'Money Flow',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            min: 0,
            max: 100,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}
function adx_option(obj) {
    return {
        tooltip: {
            fixed: {
                enabled: true,
                position: 'topLeft',
                offsetX: 70,
                offsetY: 40,
            }
        },
        annotations: adx_annotation(obj),
        series: adx_series(obj),
        colors: ['#2255a4', '#da542e', '#28b779'],
        chart: {
            toolbar: {
                show: false
            },
            height: 180,
            id: 'adx',
            group: 'ind',
            type: 'line',
            zoom: {
                enabled: false
            },
            dropShadow: {
                enabled: true,
                top: 2,
                left: 2,
                blur: 1,
                opacity: 0.2
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'straight',
            width: [2, 1, 1],
            dashArray: [0, 1, 1]
        },

        title: {
            text: 'ADX Trend',
            align: 'left',
            margin: 0,
            offsetY: 24,
            offsetX: 36
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                opacity: 0.5
            },
        },
        xaxis: {
            type: 'datetime',
            categories: obj.dates,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                show: true
            },
            tooltip: {
                enabled: false
            }
        },
        yaxis: {
            show: true,
            axisBorder: {
                show: true,
                color: '#78909C',
                offsetX: 0,
                offsetY: 0
            },
            labels: {
                formatter: (value) => {
                    return value.toFixed(2)
                },
                minWidth: 60,
                maxWidth: 60
            }
        },
        legend: {
            show: false
        }
    };
}

function symulation_series(obj) {
    return [{
            name: "Buy",
            data: obj.buy
        }]
}

function rsi_series(obj) {
    return [{
            name: "RSI",
            data: obj.R
        }]
}
function stochastic_series(obj) {
    return [{
            name: "Stoch %K",
            data: obj.K
        },
        {
            name: "Stoch %D",
            data: obj.D
        }]
}
function sma_series(obj) {
    return [{
            name: "SMA",
            data: obj.SMA
        }]
}
function macd_series(obj) {
    return [
        {
            name: "MACD",
            type: 'area',
            data: obj.macd
        }]
}
function bollinger_series(obj) {
    return [{
            name: "Up",
            data: obj.BU
        },
        {
            name: "Close price",
            data: obj.BP
        },
        {
            name: "Middle",
            data: obj.BM
        },
        {
            name: "Low",
            data: obj.BL
        },
    ]
}
function moneyflow_series(obj) {
    return [{
            name: "MFL",
            data: obj.MF
        }]
}
function adx_series(obj) {
    return [{
            name: "ADX",
            data: obj.ADX
        },
        {
            name: "D minus",
            data: obj.DMIN
        },
        {
            name: "D plus",
            data: obj.DPLUS
        }
    ]
}

function rsi_annotation(obj) {
    return {
        yaxis: [
            {
                y: obj.add[0],
                borderColor: '#000000',
            },
            {
                y: obj.add[1],
                borderColor: '#000000',
            }
        ]
    }
}
function stochastic_annotation(obj) {
    return {
        yaxis: [
            {
                y: obj.add[0],
                borderColor: '#000000',
            },
            {
                y: obj.add[1],
                borderColor: '#000000',
            }
        ]
    }
}
function moneyflow_annotation(obj) {
    return {
        yaxis: [
            {
                y: obj.add[0],
                borderColor: '#000000',
            },
            {
                y: obj.add[1],
                borderColor: '#000000',
            }
        ]
    }
}
function adx_annotation(obj) {
    return {
        yaxis: [
            {
                y: obj.add[0],
                borderColor: '#000000',
            }
        ]
    }
}