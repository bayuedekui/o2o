$(function () {

    getShopCategory();

    /**
     * 获取后台返回的商品类别数据
     */
    function getShopCategory() {
        $.ajax({
            url: "/o2o/shopadmin/getproductcategorylist",
            type: "GET",
            cache: false,
            dataType: "json",
            success: function (data) {
                handleProductCategoryList(data.data);
            }
        });

    }


    /**
     * 展现后台传过来的商品列表数据
     */
    function handleProductCategoryList(data) {
        var html = "";
        data.map(function(item,index){
            html+='<div class="row row-shop">' +
                '<div class="col-40">'+item.productCategoryName+'</div>' +
                '<div class="col-40">'+item.priority+'</div>' +
                '<div col-20><button id="deleteProductCategory('+item.productCategoryId+')" class="button button-small button-info" >删除</></div>' +
                '</div>';
        });

        $(".productCategory-wrap").html(html);
    }

});