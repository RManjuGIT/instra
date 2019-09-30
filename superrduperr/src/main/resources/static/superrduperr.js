function callProductList() {
	var xmlhttp = new XMLHttpRequest();
	var url = "product/list";
	
	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var myArr = JSON.parse(this.responseText);
	        plotProductTable(myArr, 'product');
	    }
	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}
function plotProductTable(arr, divName) {
    var col = [];
    for (var i = 0; i < arr.length; i++) {
        for (var key in arr[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }
    col.push("Select");
    var table = document.createElement("table");
    var tr = table.insertRow(-1);                   

    for (var i = 0; i < col.length; i++) {
        var th = document.createElement("th");      
        th.innerHTML = col[i];
        tr.appendChild(th);
    }

    var flag = true;
    // ADD JSON DATA TO THE TABLE AS ROWS.
    for (var i = 0; i < arr.length; i++) {
        tr = table.insertRow(-1);
        for (var j = 0; j < col.length; j++) {
            var tabCell = tr.insertCell(-1);
            tabCell.innerHTML = arr[i][col[j]];
            if(j==col.length-2){
            	if(arr[i][col[j]]==false){
            		flag = false;
            	}
            }
            if(j==col.length-1){
            	if(flag){
            		tabCell.innerHTML = '<input type="checkbox" name="'+divName+'productId" value="'+arr[i][col[0]]+'" />';
            	}else{
            		tabCell.innerHTML = '';
            		flag = true;
            	}
            }
        }
    }
    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
    var divContainer = document.getElementById(divName);
    divContainer.innerHTML = "";
    divContainer.appendChild(table);
}
var shoppingcart;

function createCart(cartId){
	
	var xmlhttp = new XMLHttpRequest();   
	var url;
	if(cartId==null){
		url = "shopping/cart/user/1/Cart1";
		xmlhttp.open("POST", url);
		if(shoppingcart==null){
			shoppingcart = [];
		}
	}else{
		url = "shopping/cart/additems/"+cartId;
		xmlhttp.open("PUT", url);
	}
	var array = []
	var checkboxes = document.querySelectorAll('input[name=productproductId]:checked')
    if(checkboxes!=null && checkboxes.length>0){
    	for (var i = 0; i < checkboxes.length; i++) {
    		array.push({ "productId": Number(checkboxes[i].value), "quantity": 1 });
    		checkboxes[i].checked = false;
    	}
    	xmlhttp.onload = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			if(cartId==null){
    				shoppingcart.push(this.responseText);
    				populateAddCartButton();
    			}else{
    				showCartItems(cartId);
    			}
    	    }
    	};
    	xmlhttp.setRequestHeader("Content-Type", "application/json");
    	xmlhttp.send(JSON.stringify(array));
    } else {
    	alert("Select Items to add to cart")
    }
	
}

function markAsComplete(){
	var xmlhttp = new XMLHttpRequest();   
	var url = "product/markcomplete";
	xmlhttp.open("PUT", url);
	var checkboxes = document.querySelectorAll('input[name=productproductId]:checked')
    if(checkboxes!=null && checkboxes.length>0){
    	var array = [];
    	for (var i = 0; i < checkboxes.length; i++) {
    		array.push(Number(checkboxes[i].value));
    		checkboxes[i].checked = false;
    	}
    	xmlhttp.onload = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			callProductList();
    	    }
    	};
    	xmlhttp.setRequestHeader("Content-Type", "application/json");
    	xmlhttp.send(JSON.stringify(array));
    } else {
    	alert("Select Items to mark as complete")
    }
}

function populateAddCartButton(){
	var divContainer = document.getElementById("addCartButton");
	divContainer.innerHTML="";
	
    var table = document.createElement("table");
    
    var tr = table.insertRow(-1);                   
    var th = document.createElement("th");      
    th.innerHTML = '<button onClick="createCart(null)">Create Cart</button>';
    tr.appendChild(th);
    var th = document.createElement("th");      
    th.innerHTML = '<button onClick="markAsComplete()">Mark as Complete</button>';
    tr.appendChild(th);
    for (var i = 0; i < shoppingcart.length; i++) {
        var th = document.createElement("th");      
        th.innerHTML = '<button onClick="createCart('+shoppingcart[i]+')">Add to Cart'+ (i+1)+'</button>';;
        tr.appendChild(th);
    }
    divContainer.appendChild(table);
    populateShowCartDetails(shoppingcart);
    
}

function onLoadPopulateCartButton(){
	var xmlhttp = new XMLHttpRequest();
	var url = "shopping/cart/user/1";
	
	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var divContainer = document.getElementById("addCartButton");
	    	divContainer.innerHTML="";
	    	
	        var table = document.createElement("table");
	        
	        var tr = table.insertRow(-1);                   
	        var th = document.createElement("th");      
	        th.innerHTML = '<button onClick="createCart(null)">Create Cart</button>';
	        tr.appendChild(th);
	        var th = document.createElement("th");      
	        th.innerHTML = '<button onClick="markAsComplete()">Mark as Complete</button>';
	        tr.appendChild(th);
	        var userCart = JSON.parse(this.responseText);
	        var carts = userCart.carts;
	        for (var i = 0; i < carts.length; i++) {
	            var th = document.createElement("th");      
	            th.innerHTML = '<button onClick="createCart('+carts[i].id+')">Add to '+ carts[i].name+'</button>';
	            tr.appendChild(th);
	        }
	        divContainer.appendChild(table);
	        onLoadPopulateShowCartDetails(carts)
	    }
	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}

function onLoadPopulateShowCartDetails(cartArr){
	var divContainer = document.getElementById("showCartButton");
	divContainer.innerHTML="";
    var table = document.createElement("table");
    var tr = table.insertRow(-1);                   
    for (var i = 0; i < cartArr.length; i++) {
    	var th = document.createElement("th");      
        th.innerHTML = '<button onClick="showCartItems('+cartArr[i].id+')">Show '+ cartArr[i].name+'</button>';
        tr.appendChild(th);
    }
    divContainer.appendChild(table);
}

function populateShowCartDetails(shoppingcart){
	var divContainer = document.getElementById("showCartButton");
	divContainer.innerHTML="";
    var table = document.createElement("table");
    var tr = table.insertRow(-1);                   
    for (var i = 0; i < shoppingcart.length; i++) {
        var th = document.createElement("th");      
        th.innerHTML = '<button onClick="showCartItems('+shoppingcart[i]+')"> Show Cart'+ (i+1)+'</button>';
        tr.appendChild(th);
    }
    divContainer.appendChild(table);
}

function showCartItems(cartId){
	var xmlhttp = new XMLHttpRequest();
	var url = "shopping/cart/"+cartId;
	
	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var myArr = JSON.parse(this.responseText);
	        plotProductTable(myArr,'item');
	        var divContainer = document.getElementById('showDeleteTagButton');
	        divContainer.innerHTML="";
	        var table = document.createElement("table");
	        var tr = table.insertRow(-1);                   
	        var th = document.createElement("th");      
	        th.innerHTML = '<button onClick="deleteItems('+cartId+')"> Delete Items </button>';
	        tr.appendChild(th);
	        var th = document.createElement("th");      
	        th.innerHTML = '<button onClick="tagItems('+cartId+')"> Tag Items </button>';
	        tr.appendChild(th);
	        var th = document.createElement("th");      
	        th.innerHTML = '<button onClick="restoreCart('+cartId+')"> Restore Cart </button>';
	        tr.appendChild(th);
	        divContainer.appendChild(table);
	    }
	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}

function deleteItems(cartId){
	var xmlhttp = new XMLHttpRequest();   
	var url = "shopping/cart/deleteitems/"+cartId;
	xmlhttp.open("PUT", url);
	var array = []
	var checkboxes = document.querySelectorAll('input[name=itemproductId]:checked')
    if(checkboxes!=null && checkboxes.length>0){
    	for (var i = 0; i < checkboxes.length; i++) {
    		array.push(Number(checkboxes[i].value));
    		checkboxes[i].checked = false;
    	}
    	xmlhttp.onload = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			showCartItems(cartId);
    	    }
    	};
    	xmlhttp.setRequestHeader("Content-Type", "application/json");
    	xmlhttp.send(JSON.stringify(array));
    } else {
    	alert("Select Items to delete from cart")
    }
}

function tagItems(cartId){
	var xmlhttp = new XMLHttpRequest();   
	var url = "shopping/cart/tagitems/"+cartId;
	xmlhttp.open("PUT", url);
	var request = '{"name":"Tagged","productIds":['
	var checkboxes = document.querySelectorAll('input[name=itemproductId]:checked')
    if(checkboxes!=null && checkboxes.length>0){
    	for (var i = 0; i < checkboxes.length; i++) {
    		if(i<checkboxes.length-1){
    			request = request+checkboxes[i].value+',';
    		}else{
    			request = request+checkboxes[i].value+']}'
    		}
    		checkboxes[i].checked = false;
    	}
    	xmlhttp.onload = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			showCartItems(cartId);
    		}
    	};
    	xmlhttp.setRequestHeader("Content-Type", "application/json");
    	xmlhttp.send(request);
    } else {
    	alert("Select Items to Tag")
    }
}

function restoreCart(cartId){
	var xmlhttp = new XMLHttpRequest();
	var url = "shopping/cart/restore/"+cartId;
	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var myArr = JSON.parse(this.responseText);
	        plotProductTable(myArr, 'item');
	    }
	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}