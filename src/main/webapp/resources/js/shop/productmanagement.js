$(function () {
    //获取店铺下的商品列表的url
    var productUrl = "/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";
    //商品下架url
    var stausUrl = "/o2o/shopadmin/modifyproduct?statusChange=true";

    getList();


    /**
     * 获取店铺下的商品列表
     */
    function getList() {
        $.ajax({
            url: productUrl,
            type: "GET",
            cache:false,
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var product = data.productList;
                    var tempHtml = '';
                    //遍历每条商品信息,拼接成一行显示,列信息包括(商品名称,优先级,上下架(含productId),编辑按钮(含productId),预览(含productId))
                    product.map(function (item, index) {
                        var textOp = "下架";
                        var contraryStatus = 0; //标识符,记录上下架情况
                        if (item.enableStatus == 0) {
                            //如果和\状态值为零,表示已经是下架的商品,操作变为上架按钮(点击上架即可上架该商品)
                            textOp = "上架";
                            contraryStatus = 1;
                        }
                        //拼接没见商品的行信息
                        tempHtml += '<div class="row row-product"> ' +
                            '<div class="col-33">'+item.productName+'</div> ' +
                            '<div class="col-20">'+item.priority+'</div> ' +
                            '<div class="col-40">' +
                                '<a href="#" class="edit" data-id="'+item.productId+'" data-status="'+item.enableStatus+'">编辑</a>' +
                                '<a href="#" class="status" data-id="'+item.productId+'" data-status="'+contraryStatus+'">'+textOp+'</a>'+
                                '<a href="#" class="preview" data-id="'+item.productId+'" data-status="'+item.enableStatus+'">预览</a>'+
                            '</div>' +
                            '</div>';
                    });
                    //将拼接好的信息赋值进html控件中
                    $('.product-wrap').html(tempHtml);
                }
            }
        });
    }

    //将class为product-wrap里面的a标签绑定上点击事件
    $('.product-wrap').on('click','a',function(e){
        let target=$(e.currentTarget);
        if(target.hasClass('edit')){
            //如果有edit标识,则进入店铺信息编辑页面,并带productId参数
            window.location.href='/o2o/shopadmin/productoperation?productId='+e.currentTarget.dataset.id;
        }else if(target.hasClass('status')){
            //如果点击的是status标签,则去调用后台功能上下架相关商品,并带有productId参数
            changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
        }else if(target.hasClass('preview')){
            //如果有class=preview则去前台展示系统该商品详情页预览商品情况
            window.location.href='/o2o/frontend/prodcutdetail?productId='+e.currentTarget.dataset.id;
        }
        
    });
    function changeItemStatus(id,enableStatus) {
        //调用后台controller中的modifyProduct接口,修改商品的enable Status
        let product={};
        product.productId=id;
        product.enableStatus=enableStatus;
        $.confirm('确定执行该操作?',function(){
           //上下架相关商品
           $.ajax({
               url:stausUrl,
               type:'POST',
               dataType:'json',
               data:{
                   productStr:JSON.stringify(product),
                   statusChange:true
               },
               success:function(data){
                   if(data.success){
                       $.toast('操作成功!');
                       getList();
                   }else{
                       $.toast("操作失败!");
                   }
               }
           }); 
        });
    }
    
    
    
    
});//总结束