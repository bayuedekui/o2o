$(function () {
    var shopId = getQueryString("shopId");  //将浏览器中的url中的shopId获取到
    var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    $.ajax({
        url: shopInfoUrl,
        type: 'GET',
        contentType: false,
        cache: false,
        processData: false,
        success: function (data) {
            if (data.redirect) {
                window.location.href = data.url;
            } else {
                if (data.shopId != undefined && data.shopId != null) {
                    shopId = data.shopId;
                }
                $('#shopInfo').attr('href','/o2o/shopadmin/shopoperation?shopId='+shopId);

            }
        }
    });


});