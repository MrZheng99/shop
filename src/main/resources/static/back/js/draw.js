/***饼图**/
var amount = echarts.init(document.getElementById('amount'));
var sales = echarts.init(document.getElementById('sales'));

$.get("/goods/report/default",function (data) {
    amount.setOption(getAmountBinOption(data.data));
    sales.setOption(getSalesBinOption(data.data));
});
function getAmountBarOption(data,flag) {
    var opData = [];
    var xdata = [];
    var legend ;
    if(flag=="type"){
        $.each(data,function (index,item) {
            opData.push(
                { name: item.goodsName,value:item.amount}
            );
            xdata.push(item.goodsName);

        });
      legend=  {
            data:opData.goodsName
        };
    }else if(flag=="good"){
        $.each(data,function (index,item) {
            opData.push(
                { name: item.goodsName,value:item.amount}
            );
            xdata.push(item.goodsName);

        });
        legend=  {
            data:opData.goodsName
        };
    }
    option = {
        title: {
            text: '销售额',
            subtext: '所有商品',

        },
        tooltip: {},
        legend: legend,
        xAxis: {
            data: xdata
        },
        yAxis: {},
        series: [{
            name: '销售额',
            type: 'bar',
            data: opData
        }]
    };
    return option;
}
function getSalesBarOption(data,flag) {
    var opData = [];
    var xdata = [];
    var legend ;
    if(flag=="type"){
        $.each(data,function (index,item) {
            opData.push(
                { name: item.goodsName,value:item.sales}
            );
            xdata.push(item.goodsName);

        });
        legend=  {
            data:opData.goodsName
        };
    }else if(flag=="good"){
        $.each(data,function (index,item) {
            opData.push(
                { name: item.goodsName,value:item.sales}
            );
            xdata.push(item.goodsName);

        });
        legend=  {
            data:opData.goodsName
        };
    }
    opData.sort(compare("value",0));

    option = {
        title: {
            text: '销售量',
            subtext: '所有商品',

        },
        tooltip: {},
        legend: legend,
        xAxis: {
            data: xdata
        },
        yAxis: {},
        series: [{
            name: '销售量',
            type: 'bar',
            data: opData
        }]
    };
    return option;
}

function getSalesBinOption(data) {
    var opData = [];
    var types = [];

    $.each(data,function (index,item) {
        opData.push(
            { name: item.type,value:item.sales}
        );
        types.push(item.type);

    });
    option = {
        title: {
            text: '销量情况统计',
            subtext: '按类',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            // data:
            data: types

        },
        series: [
            {
                type: 'pie',
                radius: '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:opData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    return option;
}
function getAmountBinOption(data) {
    var opData = [];
    var types = [];

    $.each(data,function (index,item) {
        opData.push(
            { name: item.type,value:item.amount}
        );
        types.push(item.type);

    });
    console.log(opData)
    option = {
        title: {
            text: '销售额情况统计',
            subtext: '按类',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            // data:
            data: types

        },
        series: [
            {
                type: 'pie',
                radius: '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:opData,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    return option;
}
function getAmountLineOption(data) {
    let opData = [];
    let pieces = [];
    let m = 0;
    $.each(data,function (index,item) {
        opData.push(
            [item.y+"-"+item.m+"-"+item.d,item.amount]
        );
        if(index>m&&index%2==1){
            pieces.push({
                gt: index,
                lt: index+2,
                color: 'rgba(0, 180, 0, 0.5)'
            })
            m = index+2;
        }

    });
    console.log(pieces);
    option = {
        xAxis: {
            type: 'category',
            boundaryGap: true
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '30%']
        },
        visualMap: {
            type: 'piecewise',
            show: false,
            dimension: 0,
            seriesIndex: 0,
            pieces:pieces
        },
        series: [
            {
                type: 'line',
                smooth: 0.6,
                symbol: 'none',
                lineStyle: {
                    color: 'green',
                    width: 5
                },
                areaStyle: {},
                data: opData

            }
        ]
    };
    return option;
}
function getSalesLineOption(data,flag) {
    let opData = [];
    let pieces = [];
    let m = 0;
    if(flag=="good"){
        $.each(data,function (index,item) {
            opData.push(
                [item.y+"-"+item.m+"-"+item.d,item.sales]
            );
            if(index>m&&index%2==1){
                pieces.push({
                    gt: index,
                    lt: index+2,
                    color: 'rgba(0, 180, 0, 0.5)'
                })
                m = index+2;
            }

        });
    }else if (flag=="type"){
        $.each(data,function (index,item) {
            opData.push(
                [item.goodsName,item.sales]
            );
            if(index>m&&index%2==1){
                pieces.push({
                    gt: index,
                    lt: index+2,
                    color: 'rgba(0, 180, 0, 0.5)'
                })
                m = index+2;
            }

        });
    }
    opData.sort(compare("sales",1));

    console.log(pieces);
    option = {
        xAxis: {
            type: 'category',
            boundaryGap: true
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '30%']
        },
        visualMap: {
            type: 'piecewise',
            show: false,
            dimension: 0,
            seriesIndex: 0,
            pieces:pieces
        },
        series: [
            {
                type: 'line',
                smooth: 0.6,
                symbol: 'none',
                lineStyle: {
                    color: 'green',
                    width: 5
                },
                areaStyle: {},
                data: opData

            }
        ]
    };
    return option;
}

//针对对象数据进行排序propertyName 要排序的属性名，order 1为正序0为倒序
function compare(propertyName,order) {
    return function (object1, object2) {
        var value1 = object1[propertyName];
        var value2 = object2[propertyName];
        if(order==0){
            if (value2 < value1) {
                return -1;
            }
            else if (value2 > value1) {
                return 1;
            }
            else {
                return 0;
            }
        }if(order==1){
            if (value2 > value1) {
                return -1;
            }
            else if (value2 < value1) {
                return 1;
            }
            else {
                return 0;
            }
        }

    }
}