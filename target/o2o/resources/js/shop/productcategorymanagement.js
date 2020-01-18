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
            html += '<div class="row row-product-category now">'
                + '<div class="col-40">' + item.productCategoryName + '</div>'
                + '<div class="col-40">' + item.priority + '</div>'
                + '<div class="col-20"><a href="#" data-id="'+item.productCategoryId+'" class="button button-small button-info delete" >删除</a></div>'
                + '</div>';
        });

        $(".category-wrap").html(html);
    }

    //点击新增按钮事件
    $("#new").click(function () {
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-40"><input class="category-input category" type="text" placeholder="商品分类名"></div>'
            + '<div class="col-40"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-20"><a href="#" class="button button-small delete">删除</a></div>'
            + '</div>';
        $(".category-wrap").append(tempHtml);
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
            url: "/o2o/shopadmin/addproductcategorys",
            type: "POST",
            data: JSON.stringify(productCategoryList),
            contentType:'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功!");
                    //重新展现增加后的商品列表(这样再调用一便方法后导致页面内容多了一倍(不要采用append就好),应该重新加载下页面)
                    getShopCategory();

                } else {
                    $.toast("提交失败!");
                }
            }

        });

    });


    //删除前台页面的一行显示记录(并未增加到数据库中)
    $('.category-wrap').on('click','.row-product-category.temp .delete',function (e) {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();
    });

    //删除已经存到数据库里的内容
    $('.category-wrap').on('click','.row-product-category.now .delete',function (e) {
        var target=e.currentTarget;
      window.confirm("确定删除?")?$.ajax({
                url:"/o2o/shopadmin/removeproductcategory",
                type:"POST",
                data:{
                    productCategoryId:target.dataset.id
                },
                dataType:'json',
                success:function(data){
                    if(data.success){
                        $.toast("删除成功!");
                        //重新赋值商品类别列表
                       getShopCategory();

                    }else{
                        $.toast("删除失败!");
                    }
                }
            }):"";

    });
});