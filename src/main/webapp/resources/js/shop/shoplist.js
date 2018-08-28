/**
 * 关于店铺列表操作的js
 */
$(function () {
    function getlist(e) {
        $.ajax({
            url: "/o2o/shop/getshoplist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleList(data.shoplist);
                    handleUser(data.user);
                }
            }


        });
    }   //getlist结束


    /**
     * 再页面展现店铺管理员姓名
     * @param data
     */
    function handleUser(data){
        $("#user-name").text(data.name);
    }

    /**
     * 在也买你展现店铺店铺列表
     * @param data
     */
    function handleList(data) {
        var shopListEle="";
        data.map(function (item,index) {
            shopListEle+='<div class="row row-shop"><div class="col-40">'
                +item.shopName+'</div><div class="col-40">'+shopStatus(item.enableStatus)
                +'</div><div class="col-20">'+goShop(item.enableStatus,item.shopId)+'</div></div>';
        });
    }

});


