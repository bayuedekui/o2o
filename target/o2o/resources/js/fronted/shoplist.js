$(function () {
    var loading = false;
    //分页允许返回的最大条数,超过此数目则禁止访问后台
    var maxItems = 999;
    //一页返回的最大条数
    var pageSize = 3;
    //获取店铺列表的URL
    var listUrl = "/o2o/frontend/listshops";
    //获取店铺类别以及区域列表的url
    var searchDivUrl = "/o2o/frontend/listshopspageinfo";

    //页码
    var pageNum = 1;
    //从地址栏url中尝试获取parent shopCategory id
    var parentId = getQueryString("parentId");
    var areaId = "";
    var shopCategoryId = "";
    var shopName = "";

    //在前台渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();

    //预先加载3条店铺信息
    addItems(pageSize, pageNum);


    /**
     * 从后台拿到店铺列表信息以及区域列表信息并且渲染到前台的页面上
     */
    function getSearchDivData() {
        //如果传入了parentId,就擦查出此一级类别下的所有二级类别
        var url = searchDivUrl + "?" + "parentId="+parentId;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var shopCategoryList = data.shopCategoryList;
                    var html = "";
                    html += '<a href="#" class="button" data-category-id="">全部类别</a>';
                    //遍历shopCategoryList店铺类别列表,拼接出a标签
                    shopCategoryList.map(function (item, index) {
                        html += '<a href="#" class="button" data-category-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</a>';
                    });
                    //将拼接好的类别标签嵌入前台的html组件里
                    $("#shoplist-search-div").html(html);

                    //开始构造区域选项列表
                    var selectOptions = '<option value="">全部街道</option>';
                    var areaList = data.areaList;
                    areaList.map(function (item, index) {
                        selectOptions += '<option value="' + item.areaId + '">' + item.areaName + '</option>';
                    });
                    //将构造好的标签集添加到area列表里
                    $("#area-search").html(selectOptions);
                }
            }
        });
    }

    <!--getSearchDivData方法结束-->

    /**
     * 根据查询的条件展现查询出来的结果
     */
    function addItems(pageSize, pageIndex) {
        //拼接出查询的url,赋空值就默认去掉条件的限制,有值就代表按照这个条件去去查询
        var url = listUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&parentId=' + parentId +
            '&areaId=' + areaId + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        //设定加载符,若还在后台取数据则不能再次访问后台,避免重复加载
        loading = true;
        //访问后台获取相应的查询条件下的店铺列表
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                //获取当前查询条件下店铺的总数
                maxItem = data.count;
                var shopList = data.shopList;
                var html = "";
                //遍历出店铺列表,拼接出卡片集合
                shopList.map(function (item, index) {
                    html += '<div class="card" data-shop-id="' + item.shopId + '"> ' +
                        '<div class="card-header">' + item.shopName + '</div>' +
                        '<div class="card-content"> ' +
                        '<div class="list-block media-list">' +
                        '<ul>' +
                        '<li class="item-content">' +
                        '<div class="item-media"><img src="' + item.shopImg + '" width="44"></div>' +
                        '<div class="item-inner">' +
                        '<div class="item-subtitle">' + item.shopDesc + '</div>' +
                        '</div>' +
                        '</li>' +
                        '</ul>' +
                        '<div class="card-footer">' +
                        '<p class="color-gray">' + new Date(item.lastEditTime).Format("yyyy-MM-dd") + '更新</p>' +
                        '<span>点击查看</span>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                });
                //将卡片集合添加到html控件中
                $(".shop-list").append(html);
                //获取目前为止已显示的卡片总数,包含之前已经加载的
                var total = $('.shop-list .card').length;
                //若总数达到跟按照查询条件查询出来的总数一致,则停止后台加载
                if (total >= maxItems) {
                    //加载完毕,注销无限加载的事件,以防止不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    //删除加载提示符
                    $('.infinite-scroll-preloader').remove()
                }
                //否则页码加一,继续load出新的店铺
                pageNum += 1;
                //加载结束,可以再次加载了
                loading = false;
                //刷新页面,显示新加载的店铺
                $.refreshScroller();
            }
        });
    }<!--addItem方法结束-->
    
    //预先加载6条
    addItems(pageSize, pageNum);
    
    //下滑屏幕自动进行分页搜索(自己对这块有点疑问,问题？下滑的时候没触发)
    $(document).on('infinite','.infinite-scroll-bottom',function () {
        if(loading)
            return;
        addItems(pageSize, pageNum);    
    });
    
    //点击卡片进入该商店的详情页
    $(".shop-list").on('click','.card',function (e) {
        var shopId=e.currentTarget.dataset.shopId;
        window.location.href='/o2o/frontend/shopdetail?shopId='+shopId;
    });
    
    
    //选择了新的店铺类别之后,重置页码,清空原先的的店铺列表,按照新的类别去查询
    $('#shoplist-search-div').on('click','.button',function (e) {
        //如果传过来的是一个父类下的子类(因为存在了parentId,所以必是某个以及父类下的子类)
        if(parentId){
            shopCategoryId=e.target.dataset.categoryId;
            //若之前已经选定了别的category,则移除其选定效果,改成新的
            if($(e.target).hasClass('button-fill')){
                $(e.target).removeClass('button-fill');
                shopCategoryId='';
            }else{
                $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
            }
            
            //由于查询条件改变,清空店铺列表在进行查询
            $('.list-div').empty();
            //重置页码
            pageNum=1;
            addItems(pageSize, pageNum);
        }else{  //如果传过来的父类为空,那就按照父类查询,查询parentId为空的一级分类
            parentId=e.target.dataset.categoryId;
            if($(e.target).hasClass('button-fill')){
                $(e.target).removeClass('button-fill');
                parentId='';
            }else{
                $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
            }
            
            //由于查询条件改变,清空店铺列表再进行查询
            $('.list-div').empty();
            
            //重置页码
            pageNum=1;
            addItems(pageSize, pageNum);
        }
    });
    
    //查询新的店铺名字时,重置页码,清空原先的店铺列表,按照新的名字重新查询
    $('#search').on('input',function(e){
        shopName=e.target.value;
        $('.shop-list').empty();
        pageNum=1;
        addItems(pageSize, pageNum);
    });
    
    //区域信息发生变化时,清空原先结果列表,重置页码,按照新的区域去查询
    $('#area-search').on('change',function(){
        areaId=$('#area-search').val();
        $('.shop-list').empty();
        pageNum=1;
        addItems(pageSize, pageNum);
    });
    
    //点击我的弹出侧边栏
    $('#me').on('click',function(){
        $.openPanel('#panel-right-demo');
    });
    
    
    //初始化页面
    $.init();
    
    
    
    
    
    
    
    
    
    
    
    
});