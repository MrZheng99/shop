function addoption(node, obj) {
    if (node.nodeType == 1) {
        // 如果是元素节点
        var opt = new Option();
        var txt = node.getAttribute("name"); // node.name
        opt.value = txt;
        opt.text = txt;
        obj.appendChild(opt);
    }
}
//第一个参数是要添加的节点，第二参数是添加到哪个下拉对象中
function addrInit() {
    var proObj = document.getElementById("province"); //获取省份下拉对象
    var cityObj = document.getElementById("city");
    var areaObj = document.getElementById("area"); //获取县、 地区下拉对象.
    //获取xm1文件
    var xmlhttp;
    if (window.XMLHttpRequest) { // IE7+ Firefox chorme opera等浏览器
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.open("GET", "city.xml", true); //发送-一个请求
    xmlhttp.send(null); //发送请求参数，null说明没有参数
    var dom;
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            dom = xmlhttp.responseXML.documentElement;
            // 获取所有省份
            var pros = dom.getElementsByTagName("province");
            for (var i = 0, len = pros.length; i < len; i++) {
                addoption(pros[i], proObj);
            }
            var cities;
            //当省份发生改变时，城市里面的值也要跟着变
            proObj.onchange = function() {
                var flag = proObj.value;
                cityObj.length = 1;
                for (let i = 0, len = pros.length; i < len; i++) {
                    if (pros[i].nodeType == 1 && pros[i].getAttribute("name") == flag) {
                        //cities = pros[i].childNodes;
                        cities = pros[i].getElementsByTagName("city");
                        for (var j = 0, len1 = cities.length; j < len1; j++) {
                            addoption(cities[j], cityObj);
                        }

                        cityObj.onchange();
                        break;
                    }
                }
            }
            //当城市发生改变时，地区要变
            cityObj.onchange = function() {
                var flag = cityObj.value;
                areaObj.length = 1;
                var areas;
                for (var i = 0, len = cities.length; i < len; i++) {
                    if (cities[i].nodeType == 1 && cities[i].getAttribute("name") == flag) {
                        areas = cities[i].getElementsByTagName("area");
                        for (let j = 0, len1 = areas.length; j < len1; j++) {
                            addoption(areas[j], areaObj);
                        }
                        break;
                    }
                }
            }
        }

    }
}