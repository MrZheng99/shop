class Order{
    constructor(oid, date, orderprogress, amount){
        this.oid = oid;
        this.date = date || new Date();
        this.orderprogress = orderprogress || "进度";
        this.amount = amount || "0";
    }

    render(parent){
        const cssClass = "list-group-item list-group-item-action";

        let a = document.createElement("a");
        a.className = cssClass;
        a.href = `/order/${this.oid}`;
        a.innerHTML = /*html*/`
        	<div>订单编号:${this.oid}</div>
            <ul>
        		<li>${this.date}</li>
	        	<li>${this.orderprogress}</li>
	        	<li>${this.amount}</li>
        	</ul>
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
    }
}