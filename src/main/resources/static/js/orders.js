const userName = $("#userName");

$.get("/user/name", function(data){
	if(data.success){
		userName.text(data.data);
	} else {
		location.href = "/index";
	}
}, "json")

class Order{
    static unpaid = "未付款";
    static paid = "已付款";
    static unshiped = "已发货";
    static shiped = "已收货";
    static neededListener = {
        pay: "付款函数: pay(oid)",
        hasten: "催货函数: hasten(oid)",
        receipt: "收货函数: receipt(oid)",
        refund: "退款函数: refund(oid)",
    }

    static checkListener(){
        for(let k in this.neededListener){
            try{
                eval(`${k} == undefined`);
            }catch{
                console.warn("未定义"+this.neededListener[k])
            }
        }
    }

    constructor(oid, date, orderprogress, amount){
        this.oid = oid;
        this.date = date || new Date();
        this.orderprogress = orderprogress || "进度";
        this.amount = amount || "0";
        
    }

    getOperation(){
        let str = `<button class="btn `;
        switch(this.orderprogress){
            case Order.unpaid:
                str+= `btn-primary" onclick="return pay(${this.oid})">付款`;
                break;
            case Order.paid:
                str+= `btn-secondary" onclick="return hasten(${this.oid})">催货`;
                break;
            case Order.unshiped:
                str+= `btn-primary" onclick="return receipt(${this.oid})">收货`;
                break;
            case Order.shiped:
                str+= `btn-danger" onclick="return refund(${this.oid})">退货`;
                break;
        }
        return str + "</button>";
    }

    render(parent){
        let tr = document.createElement("tr");
        tr.innerHTML = /*html*/`
            <td onclick="location.href='/order/${this.oid}'">${this.oid}</td>
            <td onclick="location.href='/order/${this.oid}'">
                ${this.amount}元
            </td>
            <td onclick="location.href='/order/${this.oid}'">
               ${this.orderprogress}
            </td>
		    <td onclick="location.href='/order/${this.oid}'">
		   		${this.date}
            </td>
		    <td > 
		      ${this.getOperation()}
		    </td>
		`;
        parent.append(tr);
    }
}

class OrderList{

    constructor(){
        this.orders = [];
    }

    render(parent){


        for(let order of this.orders){
        	order.render(parent);
        }
        Order.checkListener();
    }
}

function pay(oid){
    window.open(`/checkout/${oid}`, "_blank")
    return false;
}

function hasten(oid){
    $.post(`/order/${oid}/hasten`, function(result){
        if(result.success){
            location.reload();
        } else {
            alert("网络异常");
        }
    }, "json");
    return false;
}

function receipt(oid){
    $.post(`/order/${oid}/receipt`, function(result){
        if(result.success){
            location.reload();
        } else {
            alert("网络异常");
        }
    }, "json");
    return false;
}

function refund(oid){
    // 退款？想什么呢？
    alert("请联系客服");
    return false;
}