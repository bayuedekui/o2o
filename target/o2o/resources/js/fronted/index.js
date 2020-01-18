$(function () {
    //定义访问后台,获取访问头条以及一级列表的url
    var listMainPageInfoUrl='/o2o/frontend/listmainpageinfo';
    //$.ajax访问后台
    $.ajax({
        url:listMainPageInfoUrl,
        type:'GET',
        dataType:'json',
        success:function (data) {
            if(data.success){
                var headLineList=data.headLineList;
                var swiperHtml='';
                //遍历头条列表,并拼接出轮播图组
                headLineList.map(function (item, index) {
                    swiperHtml+='<div class="swiper-slide img-wrap">' +
                            '<a href="'+item.lineLink+'" external>' +
                                 '<img class="banner-img" src="'+item.lineImg+'" alt="'+item.lineName+'">' +
                            '</a>' +
                        '</div>';
                });
                //将轮播图的内容赋值个html页面
                $('.swiper-wrapper').html(swiperHtml);
                //设定轮播图的事件间隔为三秒
                $('.swiper-container').swiper({
                    autoplay:3000,  
                    //用户对轮播图进行操作时,是否自动停止autoplay
                    autoplayDisableOnInteraction:false
                });
                //获取后台传递过来的一级大类列表
                var shopCategoryList=data.shopCategoryList;
                var categoryHtml='';
                //遍历列表,拼接出类别
                shopCategoryList.map(function(item,index){
                    categoryHtml+='<div class="col-50 shop-classify" data-category="'+item.shopCategoryId+'">   ' +
                                        '<div class="word">' +
                                            '<p class="shop-title">'+item.shopCategoryName+'</p>' +
                                            '<p class="shop-desc">'+item.shopCategoryDesc+'</p>' +
                                        '</div>  ' +
                                        '<div class="shop-classify-img-warp">   ' +
                                            '<img class="shop-img" src="'+item.shopCategoryImg+'">' +
                                        '</div>' +
                                  '</div>';
                });
                //将拼接好的类别赋值给页面元素
                $('.row').html(categoryHtml);
            }
        }
    });
    
    
    //点击我的,显示侧栏
    $('#me').click(function () {
        $.openPanel('#panel-right-demo')
    });
    
    
    //为类别按钮绑定事件
    $('.row').on('click','.shop-classify',function(e){
        var shopCategoryId=e.currentTarget.dataset.category;
        var newUrl='/o2o/frontend/shoplist?parentId='+shopCategoryId;
        window.location.href=newUrl;    //在当前页面打开新页面
    });
    
    
});//总领结束