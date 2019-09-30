1. To run this binary file you need Java8 in the system

2. Command to run the jar file:
	java -jar superrduperr.jar
	This will make the application run in 8080 port
3. If you want to change the port use the following command
	java -Dserver.port:<yourport> -jar superrduperr.jar
	Eg: java -Dserver.port:9090 -jar superrduperr.jar

4. You will have the UI application coming up on
	http://localhost:<port>
5. API documentation is available on 
	http://localhost:<port>/swagger-ui.html
6. You can try the API through swagger ui by going through different controllers
	Click Try it out
	Execute
7. Sample products are loaded on which you can test.

8. API mapping to requirment

o Add items to a list - /shopping/cart/{cartId} - adding items to cart list 
o Mark an item as Completed - /product/markcomplete - make a product as complete it will no more available for select
o Ability to delete items - /shopping/cart/deleteitems/{cartId} - delete items from the cart list
o Ability to restore items - /shopping/cart/restore/{cartId} - restore cart to previous state before the last action(add/delete)
o Support for multiple lists - /shopping/cart/user/{userId}/{cartName} - create as many carts for a user
o Ability to tag items within a list - /shopping/cart/tagitems/{cartId} - marks the items in the cart as tagged
o Ability to add reminders to items  - /user/addproductreminder/{userId} - makes the items as reminders for the user.

Note: UserId is always 1, no API exposed to add UserId
	  Product list is also fixed. There are about 6 products no API exposed for adding products.
	  
	  In memory h2 DB is used hence after the restart the data will get refreshed again.

