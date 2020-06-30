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
        const cssClass = "list-group-item list-group-item-action";

        let a = document.createElement("a");
        a.className = cssClass;
        a.href = `/order/${this.oid}`;
        a.innerHTML = /*html*/`
            <div>订单编号：${this.oid}</div>
            <div class="d-flex justify-content-between">
                <ul>
                    <li>${this.amount} 元</li>
                    <li>${this.orderprogress}</li>
                    <li>${this.date}</li>
                </ul>
                <div class="operation">
                    ${this.getOperation()}
                </div>
            </div>
        `;
        parent.appendChild(a);
    }
}

class OrderList{

    constructor(){
        this.orders = [];
    }

    render(parent){
        const cssClass = "list-group";
        let div = document.createElement("div");
        div.className = cssClass;

        for(let order of this.orders){
        	order.render(div);
        }
        parent.appendChild(div);

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