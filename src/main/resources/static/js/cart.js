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
		       <input type="button" value="结算" onclick="checkout(${this.gid}, ${this.number})"><br><br>
		       <input type="button" value="移除该商品" onclick="deleteGood(${this.gid})">
		    </div>
		`;
        parent.appendChild(div);
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
        for(let good of this.goods){
            good.render(div);
        }
		
		CartGood.checkListener();
	}
}

function checkout(gid, number){
	$.ajax({
		type: 'PUT',
		url: "/order",
		data: [
			{
				gid: gid,
				number: number,
				price: null
			}
		],
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

function deleteGood(gid){
	$.ajax({
		type: 'DELETE',
		url: "/cart/good",
		data: [
			{
				scid: null,
				gid: gid,
				number:0,
				status:1
			}
		],
		contentType: "application/json",
		dataType: "json",
		success:
		function (msg) {
			if (msg.success) {
				window.location.reload();
			} else {
				if (msg.msg === "未登录") {
					alert("请登录!!!");
					window.location.href = "/index";
				}
			}
		}
	});
}