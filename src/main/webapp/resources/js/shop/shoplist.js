/**
 * 关于店铺列表操作的js
 */
$(function () {
    getlist();


    /**
     * 获取店铺列表
     * @param e
     */
    function getlist(e) {
        $.ajax({
            url: "/o2o/shopadmin/getshoplist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleUser(data.user);
                    handleList(data.shopList);
                }
            }


        });
    }   //getlist结束


    /**
     * 再页面展现店铺管理员姓名
     * @param data
     */
    function handleUser(data) {
        $("#user-name").text(data.name);
    }

    /**
     * 在也买你展现店铺店铺列表
     * @param data
     */
    function handleList(data) {
        var shopListEle = "";
        data.map(function (item, index) {
            shopListEle += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">' + shopStatus(item.enableStatus)
                + '</div><div class="col-20">' + goShop(item.enableStatus, item.shopId) + '</div></div>';
        });
        $(".shop-wrap").html(shopListEle);
    }

    /**
     * 根据状态返回对应的状态中文
     * @param status
     * @returns {string}
     */
    function shopStatus(status) {
        if (status == 0) {
            return '审核中';
        } else if (status == 1) {
            return '审核通过';
        } else {
            return '店铺非法';
        }
    }

    function goShop(status, id) {
        if (status == 1) {
            return '<a href="/o2o/shopadmin/shopmanagement?shopId=' + id + '">进入</a>';
        } else {
            return '';
        }
    }


});


