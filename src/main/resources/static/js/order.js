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

        //const cssClass = "list-group-item d-flex";
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
		      <span >${this.price}&nbsp;x</span>
		      <span class="cart_number">
		      ${this.number}
              </span>
		    </div>
		    <div>
		     <span class="cart_price">&yen;${this.price * this.number}</span>
		    </div>
        `;
        parent.appendChild(li);
    }
}

class GoodList{

    constructor(){
        this.goods = [];
    }

    render(parent){
        const cssClass = "list-group";
        let ul = document.createElement("ul");
        ul.className = cssClass;

        for(let good of this.goods){
            good.render(ul);
        }
        parent.appendChild(ul);
    }
}

const orderId = location.pathname.match(/\/(order)\/(.*?)$/)[2];
const userName = $("#userName");

$.get("/user/name", function(data){
    if(data.success){
        userName.text(data.data);
    } else {
        location.href = "/index";
    }
}, "json")