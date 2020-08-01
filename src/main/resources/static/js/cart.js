
class CartGood{
    constructor(gid, number){
        this.gid = gid;
        this.number = number;
        this.store = 1;
        this.name = "0";
        this.imgUrl = "0";
    }

    static neededListener = {
        checkout: "结算函数: checkout(gid, number)",
        deleteGood: "删除商品函数: deleteGood(gid)"
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


    getGoodInfo(){
        $.ajax({
            url:`/shopping/getGoodsDetails/${this.gid}`,
            method:"GET",
            dataType:"json",
            async: false,
            success: (data)=>{
                console.log(data)
                this.name = data.data.name;
                this.store = data.data.store;
                this.imgUrl = data.data.images.split(",")[0];
                this.price = data.data.price;
            }
        })
    }

    render(parent){
        this.getGoodInfo();
        let tr = document.createElement("tr");
        tr.innerHTML = /*html*/`
            <td>
                <img  class="cart_img" src="/${this.imgUrl}">
            </td>
            <td>
                <a href="/shopping/${this.gid}">${this.name}</a>
            </td>
		    <td>
		   		<span class="cart_price">${this.price}</span>
            </td>
		    <td> 
		      <input type="hidden" value=${this.gid}>
		      <input type="number" onchange="check()"  class="cart_number" max="${this.store}" min="1" value= ${this.number}>
		    </td>
		    <td>
		        <input type="checkbox" class="form-check-input mcheckbox" checked >
		       <input type="button" value="结算" onclick="checkout(${this.gid},this)" class="btn btn-primary">
		       <input type="button" value="移除" onclick="deleteGood(${this.gid})" class="btn btn-danger">
		    </td>
		`;
        parent.append(tr);

        return this.price * this.number;
    }
}

class Cart{
    constructor(){
        this.goods = [];
    }

    render(parent){
        let totalPrice = 0;
        for(let good of this.goods){
            totalPrice += good.render(parent);
        }
        // 计算总价
        $(".pay1>em").text(totalPrice);
        CartGood.checkListener();
    }
}
/*
*   核对输入的值
* */
function check() {
    console.info("计算总价");
    let numbers = $(".cart_number");
    let prices = $(".cart_price");
    let checkboxs = $(".mcheckbox");
    let amount=0;
    for(let i=0,len=prices.length;i<len;i++){
        if($(checkboxs[i]).is(":checked")){
            amount += (parseInt($(numbers[i]).val())*parseInt($(prices[i]).text()));
        }
    }
    $(".pay1>em").text(amount);
}
function checkout(gid,obj){
    let num = $(obj).parent().prev("div").children("input:last").val();
    let price =  $(obj).parent().prev("div").prev("div").children("span").text();
    $.ajax({
        type: 'PUT',
        url: "/order",
        data: JSON.stringify(
            {items:[{
                    gid: gid, number: num, price:price
                }],
                amount:parseInt(num)*parseInt(price)}
        ),
        contentType: "application/json",
        dataType: "json",
        success:
            function (msg) {
                if (msg.success) {
                    deleteGood(gid);
                    window.location.href = "/orders";
                } else {
                    if (msg.msg === "未登录") {
                        alert("请登录!!!");
                        window.location.href = "/index";
                    }
                }
            }
    });
}

function deleteGood(gid){
    $.ajax({
        type: 'DELETE',
        url: "/cart/good",
        data: JSON.stringify({
            scid: 0,
            gid: gid,
            number:0,
            status:0
        }),
        contentType: "application/json",
        dataType: "json",
        success:
            function (msg) {
                if (msg.success) {
                    window.location.reload();
                } else {
                    alert("请登录!!!");
                    window.location.href = "/index";
                }
            }
    });
}

function payAll(){
    let datas = [];
    let numbers = $(".cart_number");
    let checkboxs = $(".mcheckbox");
    let prices = $(".cart_price");

    for(let i=0,len=numbers.length;i<len;i++){
        if($(checkboxs[i]).is(":checked")){
            datas.push({
                gid:$(numbers[i]).prev("input").val(),
                number: $(numbers[i]).val(),
                price: $(prices[i]).text()
            })
        }
    }
    let date = new Date();
    let oid = ""+date.getHours()+date.getMinutes()+date.getSeconds()+date.getMilliseconds();
    $.ajax({
        type: 'PUT',
        url: "/order",
        data: JSON.stringify({items:datas,amount:$(".pay1>em").text(),oid:oid}),
        contentType: "application/json",
        dataType: "json",
        success:
            function (msg) {
                if (msg.success) {
                    window.location.href = "/checkout/"+oid;
                } else {
                    if (msg.msg === "未登录") {
                        alert("请登录!!!");
                        window.location.href = "/index";
                    }
                }
            }
    });
}