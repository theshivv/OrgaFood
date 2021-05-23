package com.example.OrgaFood.Activity.Info


//creating the cart data module so that we can get the data in the in the following format
data class cart(
    var userId: String = "",
    var pName: String = "",
    var images: String = "",
    var ProDid: String = "",
    var price: String = "",
    var sellerName: String = "",
    var numOfItems:String = "",
var customerPid : String = ""
)