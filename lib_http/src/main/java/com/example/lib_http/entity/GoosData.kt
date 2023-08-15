package com.example.lib_http.entity

data class GoosData(
    var bannerList: List<String>,
    var category_id: Int,
    var goodsInfoList: List<GoodsInfo>,
    var goods_attribute: String,
    var goods_banner: String,
    var goods_code: String,
    var goods_default_icon: String,
    var goods_default_price: Int,
    var goods_desc: String,
    var goods_detail_one: String,
    var goods_detail_two: String,
    var goods_sales_count: Int,
    var goods_stock_count: Int,
    var id: Int
)

data class GoodsInfo(
    var bannerList: Any,
    var category_id: Int,
    var goodsInfoList: Any,
    var goods_attribute: String,
    var goods_banner: String,
    var goods_code: String,
    var goods_default_icon: String,
    var goods_default_price: Int,
    var goods_desc: String,
    var goods_detail_one: String,
    var goods_detail_two: String,
    var goods_sales_count: Int,
    var goods_stock_count: Int,
    var id: Int
)