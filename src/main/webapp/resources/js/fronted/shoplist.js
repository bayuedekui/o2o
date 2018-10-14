$(function () {
    var loading = false;
    //分页允许返回的最大条数,超过此数目则禁止访问后台
    var maxItems = 999;
    //一页返回的最大条数
    var pageSize = 10;
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

    //预先加载10条店铺信息
    addItems(pageSize, pageNum);


    /**
     * 从后台拿到店铺列表信息以及区域列表信息并且渲染到前台的页面上
     */
    function getSearchDivData() {
        //如果传入了parentId,就擦查出此一级类别下的所有二级类别
        var url = searchDivUrl + "?" + "parentId="
        parentId;
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
                        html += '<a href="#" class="button" data-category-id="' + item.shopCategoryid + '">' + item.shopCategoryName + '</a>';
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
    }   <!--getSearchDivData方法结束-->

    /**
     * w为页面
     */
    function addItems() {

        
    }   <!--addItem方法结束-->
});