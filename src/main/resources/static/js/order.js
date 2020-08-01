class Good{
    constructor(gid, name, number, imgUrl, price){
        this.gid = gid;
        this.name = name || "goodName";
        this.number = number || 0;
        this.imgUrl = imgUrl || "#";
        this.price = price || 0;
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
		      <span >${this.price}&nbsp;x</span>
		      <span class="cart_number">
		      ${this.number}
              </span>
		    </td>
		    <td>
		     <span class="cart_price">&yen;${this.price * this.number}</span>
		    </td>
        `;
        parent.append(tr);
    }
}

class GoodList{
    constructor(){
        this.goods = [];
    }
    render(parent){
        console.log(parent);
        for(let good of this.goods){
            good.render(parent);
        }
    }
}

const orderId = location.pathname.match(/\/(order)\/(.*?)$/)[2];
