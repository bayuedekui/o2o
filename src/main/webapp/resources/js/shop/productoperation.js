/*
 商品操作的相关js
 */
$(function () {

    //从url中获取productId的值
    var productId = getQueryString("productId");
    //通过productId组装成获取商品信息的额url
    var infoUrl = "/o2o/shopadmin/getproductbyid?productId=" + productId;
    //获取当前店铺设设定的商品类别列表的url
    var categoryUrl = "/o2o/shopadmin/getproductcategorylist";
    //更新商品信息的url
    var productPostUrl = "/o2o/shopadmin/modifyproduct";

    //由于添加商品和修改商品采用的是同一套页面，因此采用一个标识位isEdit来标记
    var isEdit = false;
    if (productId) {
        //如果有productId则是编辑，要将修改前的信息展示到页面上
        getInfo(productId);
        isEdit = true;
    } else {
        //获取商品分类列表
        getCategory();
        prodcuctPostUrl = "/o2o/shopadmin/addproduct";
    }


    /**
     * 获取某个productId的商品信息，为页面各个表单内赋值
     *
     */
    function getInfo(productId) {
        $.ajax({
            url: infoUrl,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    //从返回的json中获取也买你所需要的数据
                    var product = data.product;
                    $("#product-name").val(product.productName);
                    $("#product-desc").val(product.productDesc);
                    $("#product-nowPrice").val(product.promotionPrice);
                    $("#product-prePrice").val(product.normalPrice);
                    $("#priority").val(product.priority);

                    //获取原本商品类别以及该店铺所有类别列表
                    var optionHtml = null;
                    var option = data.productCategoryList;
                    var optionSelected = product.productCategory.productCategoryId;
                    //生成前端的HTML商品列表，并选择默认之前选择的商品分类
                    option.map(function (item, index) {
                        var isSelected = optionSelected === item.productCategoryId ? 'selected' : '';
                        optionHtml += '<option data-value="' + item.productCategoryId + '"' + isSelected + '>' + item.productCategoryName + '</option>';
                    });
                    $("#product-category").html(optionHtml);
                }
            }
        });
    }


    /**
     *
     * 获取商品分类列表，用于增加商品的页面
     */
    function getCategory() {
        $.ajax({
            url: categoryUrl,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                var productCategoryList = data.data;  //应该是后台约定的data
                var optionHtml = "";
                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="' + item.productCategoryId + '">' + item.productCategoryName + '</option>';
                });
                $("#product-category").html(optionHtml);
            }
        });
    }

    /**
     * 针对商品详情控件组，若该控件组的最后一个元素发生了变化（即上传了图片），且控件书没有达到六个时，就生成一个新的上传控件，达到六个时就不再生成新的控件
     */
    $(".detail-img-div").on('change', 'detail-img:last-child', function () {
        if ($(".detail-img").length < 6) {
            $("#detail-img").append('<input type="file" class="detail-img">');
        }
    });


    /**
     * 提交按钮的时间响应，分别对商品添加和编辑操作做不同的的响应
     *
     */
    $("#submit").click(function () {
        //创建商品json对象，并从表单中获取对应值，构造成json字串
        var product = {};
        product.productName = $("#product-name").val();
        product.productCategoryDesc = $("#product-desc").val();
        product.priority = $("#priority").val();
        product.normalPrice = $("#product-normalPrice").val();
        product.promotionPrice = $("#product-promotionPrice").val();

        //获取商品选定的类别值
        product.productCategoryId = {
            productCategoryId: $("#product-category").find("option").not(function () {
                return !this.selected;
            }).data('value')
        };
        product.productId = productId;

        //获取缩略图文件流
        var thumbnail = $("#small-img")[0].files[0];
        //生成表单对象，用于接收参数并传递给后台
        var formData = new formData();
        formData.append("thumbnail", thumbnail);

        //遍历商品详情图控件，获取里面的文件流
        $(".detail-img").map(function (item, index) {
            //判断该控件是否选择了文件
            if ($(".detail-img")[index].file.length > 0) {
                //将第i个文件流赋值给key为productImg的表单键值对里面
                formData.append("productImg" + index, $("#detail-img")[index].file[0]);
            }
        });

        //将商品的其他信息存到转成json字符串中，存到key为productStr的键值对里
        formData.append("productStr", JSON.stringify(product));

        //向表单中存入验证码信息，供后台去验证
        var vertifyCodeInput = $("#j_kaptcha").val();
        if (!vertifyCodeInput) {
            $.toast("验证码不能为空");
            return;
        }
        formData.append("vertifyCodeActual",vertifyCodeInput);
        
        
        //formData构造好了后，将数据传入到后台供后台处理
        $.ajax({
            url:productPostUrl,
            type:'POST',
            data:formDate,
            contentType:false,
            processData:false,
            cache:false,
            success:function(data){
                if(data.success){
                    $.toast("提交成功！");
                    $("#kaptcha_img").click();
                }else{
                    $.toast("提交失败");
                }
            }
        });
        
        
    });//submit结束


})
 