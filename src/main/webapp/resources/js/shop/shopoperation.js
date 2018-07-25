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
         $.getJSON(initUrl,function(data){      //ajax请求
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
         });//向后天获取区域信息和所属类别信息结束
         
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
             var formData=new formData();
             formData.append('shopImg',shopImg);
             formData.append('shopStr',JSON.stringify(shop));
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
                 }
             });
             
         });
     }//getShopInitInfo结束
      
     
     
     
     
 })