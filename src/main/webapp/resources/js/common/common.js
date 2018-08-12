/**
 * 
 * 通用的js
 */
 
//点击更换验证码
function changeVerifyCode(img){
    img.src="../Kaptcha?"+Math.floor(Math.random()*100);
}

/**
 * 提取url中传入的参数值
 * @param name
 * @returns {string}
 */
function getQueryString(name){
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r=window.location.search.substr(1).match(reg);
    if(r!=null){
        return decodeURIComponent(r[2]);
    }
    return '';
}