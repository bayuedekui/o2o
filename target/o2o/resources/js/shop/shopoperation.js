/**
 * 
 * 店铺操作相关的js 
 */

 $(function(){
     var shopId=getQueryString('shopId');
     var isEdit=shopId?true:false;  //如果传入了shopId那么表示是编辑店铺,不穿shopId表示是注册店铺
     var initUrl='/o2o/shopadmin/getshopinitinfo';
     var registerUrl='/o2o/shopadmin/registershop';
     var shopInfoUrl="/o2o/shopadmin/getshopbyid?shopId="+shopId;
     var editShopUrl="/o2o/shopadmin/modifyshop";
     
     if(!isEdit){   //根据不同的状态进行不同的操作(注册则获取种类以及区域列表,更改则根据shopId获取店铺信息)
         getShopInitInfo();
     }else{
         getShopInfo(shopId);
     }
     
     getShopInitInfo(); //调用方法,获取后台数据显示到前台上

    function getShopInfo(shopId){
        $.ajax({
            url:shopInfoUrl,
            type:'GET',
            success:function (data) {
                if(data.success){
                    var shop=data.shop;
                    $('#shop-name').val(shop.shopName);
                    $('#shop-addr').val(shop.shopAddr);
                    $('#shop-phone').val(shop.phone);
                    $('#shop-desc').val(shop.shopDesc);
                    var shopCategory='<option data-id="'
                        +shop.shopCategory.shopCategoryId
                        +'" selected>'+shop.shopCategory.shopcategoryName+'</option>>';
                    var tempAreaHtml='';
                    data.areaList.map(function(item,index){
                        tempAreaHtml+='<option data-id="'+item.areaId+'">'+item.areaName+'</option>'
                    });
                    $('#shop-category').html(shopCategory);
                    $('#shop-category').attr('disabled','disabled');
                    $('#area').html(tempAreaHtml);
                    $("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
                }
            }
        });
    }
     
     
     /**
      * 初始化shopInfo包括区域,店铺种类的下拉列表
      */
     function getShopInitInfo(){
         //向后台拿类别信息和区域信息
         $.ajax({
             url:initUrl,
             type:'GET',
             contentType:false,
             cache:false,
             processData:false,
             success:function (data) {
                 var isOk=data.success;
                 if(data.success){
                     var tempAreaHtml="";
                     var tempCategoryHtml="";
                     data.shopCategoryList.map(function(item,index){
                         tempCategoryHtml+='<option data-id="'+item.shopCategoryId+'">'+item.shopCategoryName+'</option>>'
                     });
                     data.areaList.map(function(item,index) {
                         tempAreaHtml+='<option data-id="'+item.areaId+'">'+item.areaName+'</option>';
                     });
                     $("#shop-category").html(tempCategoryHtml);
                     $("#area").html(tempAreaHtml);
                 }
             }//向后台获取区域信息和所属类别信息结束
         });
         /*$.getJSON(initUrl,function(data){      //采用$.getJSON方法不行
             var isOk=data.success;
             if(data.success){
                 var tempAreaHtml="";
                 var tempCategoryHtml="";
                 data.shopCategoryList.map(function(item,index){
                    tempCategoryHtml+='<option data-id="'+item.shopCategoryId+'">'+item.shopCategoryName+'</option>>' 
                 });
                 data.areaList.map(function (item,index) {
                     tempAreaHtml+='<option data-id="'+item.areaId+'">'+item.areaName+'</option>>';
                 });
                 $("#shop-category").html(tempCategoryHtml);
                 $("#shop-area").html(tempAreaHtml);
             }
         });*/
         
         //收集用户所填数据,向后台注册shop
         $("#submit").click(function(){
              var shop={};
              if(isEdit){
                  shop.shopId=shopId;
              }
              shop.shopName=$("#shop-name").val();
              shop.shopAddr=$("#shop-addr").val();
              shop.phone=$("#shop-phone").val();
              shop.shopDesc=$("#shop-desc").val();
              shop.shopCategory={
                  shopCategoryId:$("#shop-category").find('option').not(function(){
                    return !this.selected;  
                  }).data('id')     //该写法时jQuery的写法(获取data-*),原始$("id").getAttribute("data-*")
              };
              shop.area={
                  areaId:$("#area").find('option').not(function () {
                      return !this.selected;
                  }).data('id')
              };
             //获取上传的文件
             var shopImg=$("#shop-img")[0].files[0];
             var formData=new FormData();
             formData.append('shopImg',shopImg);
             formData.append('shopStr',JSON.stringify(shop));   //像后天标注是通过shopStr没这个传过去的
             var verifyCodeInput=$("#j_kaptcha").val();
             if(!verifyCodeInput){
                 $.toast("请输入验证码!");
                 return;
             }
             formData.append('verifyCodeInput',verifyCodeInput);
             $.ajax({
                 url:(isEdit?editShopUrl:registerUrl),
                 type:'POST',
                 data:formData,
                 contentType:false, 
                 processData:false,
                 cache:false, 
                 success:function (data) {
                     if(data.success){
                         $.toast(isEdit?'修改店铺信息成功':'注册店铺成功!');
                     }else{
                         $.toast(isEdit?'修改店铺信息失败':'注册店铺失败'+data.errMsg);
                     }
                     $("#kaptcha_img").click();
                 }
             });
             
         });
     }//getShopInitInfo结束
      
     
     
     
     
 })