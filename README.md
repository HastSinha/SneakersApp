Sneakers App:

Code:

Architecture Followed : MVVM
Languages used: Kotlin
All the main Strings are written in strings.xml
Data Source : Dummy JSON

HomePage:
	It is a product listing page that is havin grid layout divided to two columns.
	We can go to product details by clicking on the item.
	We can add the product to the cart bt clicking on + icon.
	We can perform search based no the product name.
	Data is coming from viewmodel which is LiveData and we are observing in the HomePageFragment.
	Since the data are not API driven, the behaviour would not be exact.(The purpose is just to load the data and display)

CartPage:
	It is a product listing for the products that are added to the cart.
	It also shows the total amount and tax amounts of the products.
	We can checkout but it is dummy.

Product Details Page:
	It is a single product details page with Product details like name, image and price.
	It consists of dummy data like sizes and colors but on click of it will only show us a Toast.
	Add to Cart will add the product in the cart.

NOTE: 

 - All the data is hardcoded and due to time constraints I couldn't populate the data properly. Please ignore the duplicate data and the Simple design.

 - The Pagination is not handled for products because it need a lots of boiler plate code and that would also make it complex.

 - Icon switch for bottom navigation is not handled properly.
