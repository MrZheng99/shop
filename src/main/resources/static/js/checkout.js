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
                this.imgUrl = data.data.imgUrls[0];
                this.price = data.data.price;
			}
		})
	}

	render(parent){	
		this.getGoodInfo();

		const cssClass = "list-group-item d-flex";

        let li = document.createElement("li");
        li.className = cssClass;
        li.innerHTML = /*html*/`
            <img src="/${this.imgUrl}" class="h-100"  style="max-height:200px">
            <div class="flex-grow-1 d-flex flex-column">
                <a href="#" target="_blank" class="flex-grow-1"><h4>${this.name}</h4></a>
                <h6>
                    <span>${this.price}</span>
                    x
                    <span>${this.number}</span>
                </h6>
            </div>
            <div class="h-100" style="padding: 5% 0;">
                <span style="margin-top: 25%">${this.price * this.number}</span>
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