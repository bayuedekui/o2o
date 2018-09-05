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
        data.map(function (item, index) {
            html += '<div class="row row-shop">'
                + '<div class="col-40">' + item.productCategoryName + '</div>'
                + '<div class="col-40">' + item.priority + '</div>'
                + '<div col-20><a id="deleteProductCategory(' + item.productCategoryId + ')" class="button button-small button-info" >删除</a></div>'
                + '</div>';
        });

        $(".productCategory-wrap").append(html);
    }

    //点击新增按钮事件
    $("#new").click(function () {
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-40"><input class="category-input category" type="text" placeholder="商品分类名"></div>'
            + '<div class="col-40"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-20"><a href="#" class="button button-small delete">删除</a></div>'
            + '</div>';
        $(".productCategory-wrap").append(tempHtml);
    });
    //点击提交按钮事件
    $("#submit").click(function () {
        var tempArr = $(".temp");
        var productCategoryList = [];
        tempArr.map(function (index, item) {
            var tempProCateObj = {};
            tempProCateObj.productCategoryName = $(item).find(".category").val();
            tempProCateObj.priority = $(item).find(".priority").val();
            productCategoryList.push(tempProCateObj);
        });
        $.ajax({
            url: "o2o/shopadmn/addproductcategorys",
            type: "POST",
            data: JSON.stringify(productCategoryList),
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功!");
                    //重新展现增加后的商品列表
                    getShopCategory();
                } else {
                    $.toast("提交失败!");
                }
            }

        });

    });

});