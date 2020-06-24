class Good{
    constructor(gid, name, number, imgUrl, price){
        this.gid = gid;
        this.name = name || "goodName";
        this.number = number || 0;
        this.imgUrl = imgUrl || "#";
        this.price = price || 0;
    }

    render(parent){
        const cssClass = "list-group-item d-flex";

        let li = document.createElement("li");
        li.className = cssClass;
        li.innerHTML = /*html*/`
            <img src=${this.imgUrl} class="h-100">
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

const orderId = location.pathname.match(/\/(order|checkout)\/(.*?)$/)[2];