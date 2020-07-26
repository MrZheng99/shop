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


		let tr = document.createElement("tr");
		tr.innerHTML = /*html*/`
			<td>
		        <img  class="cart_img" src="/${this.imgUrl}">
		    </td>
		    <td>
		       <a href="{this.gid}">${this.name}</a>
		    </td>
		    <td>
		      <span class="cart_price">${this.price}x${this.number}</span>
		    </td>
		    <td>
		     <span class="cart_price">${this.price * this.number}</span>
		    </td>
		   
        `;
		parent.append(tr);
		return this.price;
	}
}

class Checkout{
	constructor(){
		this.goods = [];
	}

	render(parent){

		let totalPrice = 0;
		for(let good of this.goods){
			const price = good.render(parent);
			totalPrice += good.number * price;
		}
		$("#total-price").text(totalPrice);
	}
}

const orderId = location.pathname.match(/\/(checkout)\/(.*?)$/)[2];