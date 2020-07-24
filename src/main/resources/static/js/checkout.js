class Good{
	constructor(gid, number){
		this.gid = gid;
		this.number = number;
		this.name = "0";
		this.imgUrl = "0";
		this.price = 0;
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
				this.imgUrl = data.data.images.split(",")[0];
				this.price = data.data.price;
			}
		})
	}

	render(parent){
		this.getGoodInfo();

		// const cssClass = "list-group-item d-flex";
		const cssClass = "cart";

		let li = document.createElement("li");
		li.className = cssClass;
		li.innerHTML = /*html*/`
			<div>
		        <img  class="cart_img" src="/${this.imgUrl}">
		    </div>
		    <div>
		       <a href="{this.gid}">${this.name}</a>
		    </div>
		    <div>
		      <span class="cart_price">${this.price}x${this.number}</span>
		    </div>
		    <div>
		     <span class="cart_price">${this.price * this.number}</span>
		    </div>
		   
        `;
		parent.appendChild(li);
		return this.price;
	}
}

class Checkout{
	constructor(){
		this.goods = [];
	}

	render(parent){
		const cssClass = "list-group";
		let ul = document.createElement("ul");
		ul.className = cssClass;
		let totalPrice = 0;
		for(let good of this.goods){
			const price = good.render(ul);
			totalPrice += good.number * price;
		}
		parent.appendChild(ul);
		$("#total-price").text(totalPrice);
	}
}

const orderId = location.pathname.match(/\/(checkout)\/(.*?)$/)[2];