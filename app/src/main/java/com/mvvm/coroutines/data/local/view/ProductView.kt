package com.mvvm.coroutines.data.local.view

//@DatabaseView(
//    "SELECT PI.productCode, PI.productName, PI.price, PI.priceCode, C.customerCode," +
//            "(SELECT saleUom FROM Product WHERE productId = PI.productCode) productUom ," +
//            "(SELECT longDescription FROM Product WHERE productId = PI.productCode) longDescription  " +
//            "FROM Price P, PriceItem PI, Customer C " +
//            "WHERE C.priceListCode = P.priceListCode " +
//            "AND P.priceListCode = PI.priceCode"
//)
//data class ProductView(
//    var productCode: String? = "",
//    var productName: String? = "",
//    var longDescription: String? = "",
//    var productUom: String? = "",
//    var customerCode: String? = "",
//    var priceCode:String? = "",
//    var price: String? = ""
//)