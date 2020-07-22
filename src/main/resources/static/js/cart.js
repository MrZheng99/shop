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
				this.imgUrl = data.data.imgUrls[0];
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
		    <img  class="cart_img" src="/${this.imgUrl}">
		    <div class="cart_index_2">
		       <a href="/shopping/${this.gid}">${this.name}</a>
		    </div>
		    <div class="cart_index_3">
		       ${this.number}
		    </div>
		    <div class="cart_de">
		       <input type="button" value="结算" onclick="checkout(${this.gid}, ${this.number})" class="btn btn-primary"><br><br>
		       <input type="button" value="移除该商品" onclick="deleteGood(${this.gid})" class="btn btn-danger">
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

function checkout(gid, number){
	$.ajax({
		type: 'PUT',
		url: "/order",
		data: JSON.stringify([
			{
				gid: gid,
				number: number,
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
			status:1
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
	for(let good of cart){
		datas.push({
			gid: good.gid,
			number: good.number,
			price: 0
		})
	}
	$.ajax({
		type: 'PUT',
		url: "/order",
		data: JSON.stringify(datas),
		contentType: "application/json",
		dataType: "json",
		success:
		function (msg) {
			if (msg.success) {
				for(let good of cart){
					deleteGood(good.gid);
				}
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