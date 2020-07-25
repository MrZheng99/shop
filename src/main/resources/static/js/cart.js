const userName = $("#userName");

$.get("/user/name", function(data){
	if(data.success){
		userName.text(data.data);
	} else {
		location.href = "/index";
	}
}, "json")

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

		const cssClass = "cart";

		let div = document.createElement("div");
		div.className = cssClass;
		div.innerHTML = /*html*/`
		    <div>
		        <img  class="cart_img" src="/${this.imgUrl}">
		    </div>
		    <div>
		       <a href="/shopping/${this.gid}">${this.name}</a>
		    </div>
		       <div>
		      <span class="cart_price">${this.price}</span>
		    </div>
		    <div>
		    <input type="hidden" value=${this.gid}>
		      <input type="number" onchange="check()" class="cart_number" max="${this.store}" min="1" value= ${this.number}>
		    </div>
		    <div>
		       <input type="button" value="结算" onclick="checkout(${this.gid},this)" class="btn btn-primary">
		       <input type="button" value="移除" onclick="deleteGood(${this.gid})" class="btn btn-danger">
		    </div>
		`;
		parent.appendChild(div);

		return this.price * this.number;
	}
}

class Cart{
	constructor(){
		this.goods = [];
	}

	render(parent){
		const cssClass = "list-group";
		let div = document.createElement("div");
		div.className = cssClass;

		parent.appendChild(div);

		let totalPrice = 0;
		for(let good of this.goods){
			totalPrice += good.render(div);
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
	let numbers = $(".cart_number");
	let prices = $(".cart_price");
	let amount=0;
	for(let i=0,len=prices.length;i<len;i++){
		amount += (parseInt($(numbers[i]).val())*parseInt($(prices[i]).text()));
	}
	console.log(amount);
	$(".pay1>em").text(amount);
}
function checkout(gid,obj){
	number = $(obj).parent().prev("div").children("input").val();
	console.log(number);
	$.ajax({
		type: 'PUT',
		url: "/order",
		data: JSON.stringify([
			{
				gid: gid,
				number: number,
				//TODO 结算
				price: 0
			}
		]),
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
	for(let i=0,len=numbers.length;i<len;i++){
		datas.push({
			gid:$(numbers[i]).prev("input").val(),
			number: $(numbers[i]).val(),
			price: $(numbers[i]).parent().prev("div").children("span").text()
		})
	}
	$.ajax({
		type: 'PUT',
		url: "/order",
		data: JSON.stringify({items:datas,amount:$(".pay1>em").text()}),
		contentType: "application/json",
		dataType: "json",
		success:
			function (msg) {
				if (msg.success) {
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