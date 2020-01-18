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

/**
 * 格式化日期的方法
 * @param fmt
 * @constructor
 */
Date.prototype.Format = function(fmt){
    var o={
        "M+":this.getMonth()+1, //月份
        "d+":this.getDate(),    //日期
        "h+":this.getHours(),   //小时
        "m+":this.getMinutes(), //分钟
        "s+":this.getSeconds(), //秒
        "q+":Math.floor((this.getMonth()+3)/3), //季度
        "S+":this.getMilliseconds() //毫秒
    };
    
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1,(this.getFullYear()+1+"").substr(4-RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+k+")").test(fmt)){
            fmt=fmt.replace(RegExp.$1,(RegExp.$1.length == 1)?(0[k]):(("00"+o[k]).substr(""+o[k].length)));
        }
    }
    return fmt; 
    
};
