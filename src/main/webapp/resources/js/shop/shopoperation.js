/**
 * 
 * 
 */
 $(function(){
     var initUrl='/o2o/shopadmin/getshopinitinfo';
     var registerUrl='/o2o/shopadmin/registershop';
   getShopInitInfo(); //调用方法,获取后台数据显示到前台上


     function getShopInitInfo(){
         //向后台拿类别信息和区域信息
         $.ajax({
             url:initUrl,
             type:'GET',
             contenType:false,
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
                 url:registerUrl,
                 type:'POST',
                 data:formData,
                 contentType:false, 
                 processData:false,
                 cache:false, 
                 success:function (data) {
                     if(data.success){
                         $.toast('注册店铺成功!');
                     }else{
                         $.toast('注册店铺失败'+data.errMsg);
                     }
                     $("#kaptcha_img").click();
                 }
             });
             
         });
     }//getShopInitInfo结束
      
     
     
     
     
 })