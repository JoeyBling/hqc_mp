<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 订单详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <style>
    	a,abbr,acronym,address,applet,article,aside,audio,b,big,blockquote,body,canvas,caption,center,cite,code,dd,del,details,dfn,div,dl,dt,em,embed,fieldset,figcaption,figure,footer,form,h1,h2,h3,h4,h5,h6,header,hgroup,html,i,iframe,img,ins,kbd,label,legend,li,mark,menu,nav,object,ol,output,p,pre,q,ruby,s,samp,section,small,span,strike,strong,sub,summary,sup,table,tbody,td,tfoot,th,thead,time,tr,tt,u,ul,var,video{margin:0;padding:0;border:0;font:inherit;font-size:100%;vertical-align:middle}table{border-collapse:collapse;border-spacing:0}sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}fieldset,img{border:0}*,a,a:active,a:focus,button,div:active,div:focus,img,input,input:active,input:focus,textarea{-webkit-tap-highlight-color:transparent}a:focus,button:focus,input:focus{outline:none}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block}html{color:#333;height:100%;-webkit-user-select:none;user-select:none}*{max-height:999999px}#debuggapRoot .dg-out{width:5rem!important;height:5rem!important;border-radius:50%!important}#debuggapRoot .dg-inner{width:4rem!important;height:4rem!important;border-radius:50%!important;margin:.5rem!important}body{font-size:1.2rem;line-height:1.5;font-family:PingFang SC,-apple-system,Heiti SC,Helvetica,Helvetica Neue,Droid Sans Fallback,Droid Sans;height:auto;min-height:100%;display:-webkit-box;display:flex;-webkit-box-orient:vertical;flex-direction:column;-webkit-text-size-adjust:none}body.not-flex{display:block}ol,ul{list-style:none}blockquote,q{quotes:none}blockquote:after,blockquote:before,q:after,q:before{content:''}a{text-decoration:none}.scrolling{-webkit-overflow-scrolling:touch}.flex-column,.flex-row,.m-alert-dialog .operate,.m-alert-dialog .tip .tip-head,.m-confirm-dialog .operate,.m-confirm-dialog .tip .tip-head,.m-dialog .dialog-head,.m-stretch-swiper{display:-webkit-box;display:flex}.flex-column,.m-stretch-swiper{-webkit-box-orient:vertical;flex-direction:column}.flex-item,.m-alert-dialog .operate span,.m-alert-dialog .tip .tip-title,.m-confirm-dialog .operate span,.m-confirm-dialog .tip .tip-title,.m-dialog .dialog-head .title,.m-stretch-swiper>.swiper-wrapper,.pbd{-webkit-box-flex:1;flex:1}.flex-vertical-middle{-webkit-box-align:center;align-items:center}.m-stretch-swiper>.swiper-wrapper{-webkit-box-align:stretch;align-items:stretch}.m-stretch-swiper>.swiper-wrapper>.swiper-slide{height:auto}.single-line-clamp{white-space:nowrap;word-break:break-all}.ellipsis,.single-line-clamp{overflow:hidden;text-overflow:ellipsis}.ellipsis{display:-webkit-box;-webkit-line-clamp:1;-webkit-box-orient:vertical}.pbd{-webkit-user-select:none;overflow-x:hidden;overflow-y:scroll;-webkit-overflow-scrolling:touch}.border-box{box-sizing:border-box}.prompt{font-size:1.1rem;color:#ff7800;background-color:#faf6de;line-height:1.4rem;padding:.8rem 1.4rem;position:absolute;left:0;top:0;right:0;z-index:1000}.prompt .icon-prompt{background-position:50%;width:1.4rem;height:1.4rem;background-image:url(./images/icons/prompt.png);background-repeat:no-repeat;background-size:100%;display:inline-block}.dashboardScroll{height:100%;overflow-x:hidden;overflow-y:scroll;-webkit-overflow-scrolling:touch;-webkit-transform:translateZ(0);-webkit-perspective:1000}.dashboardScroll .dashboardScroll-inner,.dashboardScroll:after{height:calc(100% + 1px)}.modal-open .dashboardScroll{overflow-y:hidden}.modal-open .maskScroll{overflow-y:scroll}/*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 *  #border-radius {
 *    @include default-border-radius(25px);
 *  }
 *   
 *  #border-radius-top-left {
 *    @include top-left-border-radius(25px);
 *  }
 *  
 *  #border-radius-top-right {
 *    @include top-right-border-radius(25px);
 *  }
 *  #border-radius-bottom-left {
 *    @include bottom-left-border-radius(25px);
 *  }
 *  #border-radius-bottom-right {
 *    @include bottom-right-border-radius(25px);
 *  } 
 *  #border-radius-top {
 *    @include top-border-radius(25px, 25px)
 *  }
 *  #border-radius-bottom {
 *    @include bottom-border-radius(25px, 25px)
 *  }
 *  #border-radius-left {
 *    @include left-border-radius(25px, 25px)
 *  }
 *  #border-radius-right {
 *    @include right-border-radius(25px, 25px)
 *  }
 *  #border-radius-combo {
 *    @include combo-border-radius(50px, 30px, 15px, 80px)
 *  }
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins   
 * size  btn-lg default sm  xs
 * color  default primary success info warning danger dark disabled
 * type  Rounded dropdowns icon groups components
 */body{background-color:#f2f2f2}.money-info{clear:both;}.user-info .user-field{font-size:1.0rem;width:100%;}  .order-notpay{background-color:#fff} .order-notpay .g-content .g-validity{font-size: 0.9rem;} .order-notpay .countdown{position:relative;background-color:#faf6de;color:#666;font-size:1.0rem;height:3.3rem;line-height:3rem;text-align:center}.order-notpay .countdown .icon-yl-clock{background-position:-23.85rem -19.6rem;width:1.4rem;height:1.4rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.order-notpay .countdown strong{color:#fc7700}.order-notpay .countdown .orange_stripes{position:absolute;left:0;bottom:0;width:100%;height:.3rem;background:url(${base}/statics/images/wx/ticket/order_detail_image_orange_stripes.png) no-repeat 0;background-size:100%}.order-notpay .g-content{color:#666;padding-left:1.4rem;padding-right:1.4rem;padding-top:1.3rem;font-size:1.	rem}.order-notpay .g-content .g-head{font-size:1.2rem;display:-webkit-box;display:-moz-flex;display:-ms-flexbox;display:flex}.order-notpay .g-content .g-head .g-title{font-size:1.1rem;color:#333;-webkit-box-flex:1;-moz-flex:1;-ms-flex:1;flex:1}.order-notpay .g-content .g-head .g-status{font-size:1.1rem;color:#fc7700} .order-notpay .g-content .g-number{font-size:0.9rem;} .order-notpay .g-content .real-pay{padding-top:.8rem}.order-notpay .g-content .real-pay>div{padding:.5rem 0 .25rem;position:relative;text-align:right;color:#333;font-size:1.2rem}.order-notpay .g-content .real-pay>div:before{content:"";position:absolute;width:200%;display:block;-webkit-transform-origin:0 0;transform-origin:0 0;box-sizing:border-box;-webkit-transform:scale(.5);transform:scale(.5);border-radius:0;background-clip:padding-box;border:0 solid #e5e5e5;left:0;top:0;border-top-width:1px}.iphone .order-notpay .g-content .real-pay>div:before{border-top-width:2px}.iphoneplus .order-notpay .g-content .real-pay>div:before{border-top-width:3px}.order-notpay .g-content .real-pay>div span{color:#999}.order-notpay .g-content .real-pay>div strong{font-size:1.5rem}.order-notpay .split-bg{background-color:#fafafa}.order-notpay .separated{width:100%;height:1.4rem;background:url(${base}/statics/images/wx/ticket/order_detail_image_separated.png) no-repeat 0;background-size:100%}.order-notpay .money-info,.order-notpay .user-info{position:relative}.order-notpay .money-info:before,.order-notpay .user-info:before{content:"";position:absolute;width:200%;display:block;-webkit-transform-origin:0 0;transform-origin:0 0;box-sizing:border-box;-webkit-transform:scale(.5);transform:scale(.5);border-radius:0;background-clip:padding-box;border:0 solid #e5e5e5;left:0;bottom:0;border-bottom-width:1px}.iphone .order-notpay .money-info:before,.iphone .order-notpay .user-info:before{border-bottom-width:2px}.iphoneplus .order-notpay .money-info:before,.iphoneplus .order-notpay .user-info:before{border-bottom-width:3px}.order-notpay .money-info,.order-notpay .order-info,.order-notpay .user-info{padding-top:.4rem;padding-bottom:.4rem;margin-left:1.4rem;margin-right:1.4rem}.order-notpay .money-field,.order-notpay .order-field,.order-notpay .user-field{display:-webkit-box;display:-moz-flex;display:-ms-flexbox;display:flex;padding-top:.4rem;padding-bottom:.4rem}.order-notpay .money-field .label,.order-notpay .order-field .label,.order-notpay .user-field .label{font-size:1.2rem;color:#666}.order-notpay .money-field .value,.order-notpay .order-field .value,.order-notpay .user-field .value{-webkit-box-flex:1;-moz-flex:1;-ms-flex:1;flex:1;color:#333}.order-notpay .user-info .label{width:5rem}.order-notpay .money-field .value{text-align:right}.order-notpay .money-field .value.realy-pay{color:#fc7700}.order-notpay .order-field .label{width:6rem}.order-notpay .pay-btn{position:relative;background-color:#f2f2f2;text-align:center;padding:2.7rem 2.3rem 1.4rem}.order-notpay .pay-btn .securities{position:absolute;left:0;top:0;width:100%;height:1.3rem;background:url(${base}/statics/images/wx/ticket/order_detail_image_securities.png) no-repeat 0;background-size:100%}.order-notpay .pay-btn button{width:100%;height:3.6rem;border:1px solid transparent;border-radius:.3rem;background-clip:padding-box;background-color:#00c267;font-size:1.5rem;color:#fff}.pay-style{padding:0 1.4rem;font-size:1.2rem;background-color:#fafafa}.pay-style .head{color:#666;padding-top:.5rem;padding-bottom:.6rem;position:relative}.pay-style .head:before{content:"";position:absolute;width:200%;display:block;-webkit-transform-origin:0 0;transform-origin:0 0;box-sizing:border-box;-webkit-transform:scale(.5);transform:scale(.5);border-radius:0;background-clip:padding-box;border:0 solid #e5e5e5;left:0;bottom:0;border-bottom-width:1px}.iphone .pay-style .head:before{border-bottom-width:2px}.iphoneplus .pay-style .head:before{border-bottom-width:3px}.pay-style .pay-list .pay-item{padding:1.3rem 0;display:-webkit-box;display:-moz-flex;display:-ms-flexbox;display:flex}.pay-style .pay-list .pay-item:not(:first-child){position:relative}.pay-style .pay-list .pay-item:not(:first-child):before{content:"";position:absolute;width:200%;display:block;-webkit-transform-origin:0 0;transform-origin:0 0;box-sizing:border-box;-webkit-transform:scale(.5);transform:scale(.5);border-radius:0;background-clip:padding-box;border:0 solid #e5e5e5;left:0;top:0;border-top-width:1px}.iphone .pay-style .pay-list .pay-item:not(:first-child):before{border-top-width:2px}.iphoneplus .pay-style .pay-list .pay-item:not(:first-child):before{border-top-width:3px}.pay-style .pay-list .pay-item>div{-ms-flex-item-align:center;align-self:center}.pay-style .pay-list .pay-item .pay-icon{padding-right:.3rem}.pay-style .pay-list .pay-item .pay-name{-webkit-box-flex:1;-moz-flex:1;-ms-flex:1;flex:1;color:#333}.pay-style .pay-list .pay-item .icon-radio-default{background-position:-21.3rem -12rem}.pay-style .pay-list .pay-item .icon-radio-default,.pay-style .pay-list .pay-item.item-checked .icon-radio-default{width:2rem;height:1.8rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.pay-style .pay-list .pay-item.item-checked .icon-radio-default{background-position:-21.3rem -16.4rem}.pay-style .pay-list .pay-item .icon-pay-wechat{background-position:-21.3rem -18.6rem}.pay-style .pay-list .pay-item .icon-pay-wechat,.pay-style .pay-list .pay-item .icon-pay-ys{width:2rem;height:1.8rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.pay-style .pay-list .pay-item .icon-pay-ys{background-position:-21.3rem -14.2rem}/*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 *  #border-radius {
 *    @include default-border-radius(25px);
 *  }
 *   
 *  #border-radius-top-left {
 *    @include top-left-border-radius(25px);
 *  }
 *  
 *  #border-radius-top-right {
 *    @include top-right-border-radius(25px);
 *  }
 *  #border-radius-bottom-left {
 *    @include bottom-left-border-radius(25px);
 *  }
 *  #border-radius-bottom-right {
 *    @include bottom-right-border-radius(25px);
 *  } 
 *  #border-radius-top {
 *    @include top-border-radius(25px, 25px)
 *  }
 *  #border-radius-bottom {
 *    @include bottom-border-radius(25px, 25px)
 *  }
 *  #border-radius-left {
 *    @include left-border-radius(25px, 25px)
 *  }
 *  #border-radius-right {
 *    @include right-border-radius(25px, 25px)
 *  }
 *  #border-radius-combo {
 *    @include combo-border-radius(50px, 30px, 15px, 80px)
 *  }
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins   
 * size  btn-lg default sm  xs
 * color  default primary success info warning danger dark disabled
 * type  Rounded dropdowns icon groups components
 */.m-mask{background-color:transparent;transition:all 1s}.dialog-wrap-info{background-color:hsla(0,0%,57%,.7);z-index:1000;width:60%;border-radius:.4rem}.dialog-wrap-info .dialog-content-info{display:table;width:100%;height:100%}.dialog-wrap-info .dialog-content-info .dialog-content-bd{display:table-cell;padding:3rem 2rem;text-align:center;color:#fff;font-size:1.5rem}.m-alert-dialog,.m-confirm-dialog{width:25rem;background-color:#fff;border-radius:.4rem;text-align:center;margin:0 auto}.m-alert-dialog .tip,.m-confirm-dialog .tip{border-bottom:1.5px solid #dfdfdf}.m-alert-dialog .tip .tip-head,.m-confirm-dialog .tip .tip-head{background-color:#f5f5f5;height:3.4rem;line-height:3.4rem}.m-alert-dialog .tip .tip-title,.m-confirm-dialog .tip .tip-title{font-size:1.5rem;color:#727272;text-align:center}.m-alert-dialog .tip .tip-close,.m-confirm-dialog .tip .tip-close{padding:0 1rem 0 2rem;font-size:1.5rem;color:#21ab38;position:absolute;right:0}.m-alert-dialog .tip div.content,.m-confirm-dialog .tip div.content{padding:1.5rem 1.5rem 4rem;font-size:1.2rem;line-height:1.8rem}.m-alert-dialog .alert,.m-confirm-dialog .alert{padding:1.2rem 0;color:#727272;font-size:1.5rem}.m-alert-dialog .operate,.m-confirm-dialog .operate{height:2.8rem}.m-alert-dialog .operate span,.m-confirm-dialog .operate span{display:block;align-self:center;line-height:2.8rem}.m-alert-dialog .operate span:last-child,.m-confirm-dialog .operate span:last-child{color:#21ab38}.m-confirm-dialog{left:50%!important;top:50%!important;width:80%;margin-left:-40%;margin-top:-40%}.m-dialog{-moz-border-top-left-radius:.5rem;border-top-left-radius:.5rem;-moz-border-top-right-radius:.5rem;border-top-right-radius:.5rem;overflow:hidden;background-color:#fff;width:100%}.m-dialog .icon-close{background-position:-15.95rem -18.6rem;width:2rem;height:1.8rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.m-dialog .dialog-head{font-size:1.6rem;color:#333;background-color:#fafafa;height:4.4rem;box-sizing:border-box;position:relative;border-bottom:1.5px solid #dfdfdf}.m-dialog .dialog-head .close-wrap,.m-dialog .dialog-head .title{display:block;-ms-flex-item-align:center;align-self:center}.m-dialog .dialog-head .title{padding-left:1.4rem;position:relative}.m-dialog .dialog-head .title:before{position:absolute;left:0;top:50%;margin-top:-1rem;content:"";width:.4rem;height:2rem;background-color:#00c267}.m-dialog .dialog-head .close-wrap{text-align:right;padding-left:2.8rem;padding-right:1.4rem}.m-dialog .dialog-inner{overflow:hidden}.m-dialog .dialog-inner .dialog-content{overflow-x:hidden;overflow-y:scroll;-webkit-overflow-scrolling:touch}.m-dialog .dialog-operate{box-shadow:0 -2px 5px rgba(1,1,1,.1)}.m-dialog .dialog-operate button{display:block;width:100%;background-color:#00c267;height:4.6rem;font-size:1.8rem;color:#fff;border:0}.m-dialog .dialog-operate button sub{font-size:1.1rem;margin-right:.4rem}.m-dialog .dialog-operate button.disabled{background-color:#d3d3d3}.m-dialog .article{padding:1rem;color:#727272;font-size:1.1rem}.m-dialog .article h5{font-size:1.1rem;padding:.35rem 0}.m-dialog .article p{line-height:1.75rem;padding-bottom:.4rem}@-webkit-keyframes slidein{0%{transform:translate3d(0,100%,0)}to{transform:translateZ(0)}}.weui-mask{position:fixed;z-index:1000;width:100%;height:100%;top:0;left:0;background:rgba(11,14,23,.7)}.weui-dialog{position:fixed;z-index:5000;width:85%;top:50%;left:50%;-webkit-transform:translate(-50%,-50%);transform:translate(-50%,-50%);background-color:#fdfdfe;text-align:center;border-radius:.4rem;background-clip:padding-box;overflow:hidden}.weui-dialog .dialog-inner{width:96%}.weui-dialog .weui-dialog-hd{padding:2rem 1rem 0}.weui-dialog .weui-dialog-hd .weui-dialog-title{font-size:1.5rem;color:#262c2f}.weui-dialog .weui-dialog-bd{text-align:center;padding:1rem 1.4rem 2rem;font-size:1.2rem;color:#727272;word-wrap:break-word;word-break:break-all}.weui-dialog .weui-dialog-bd p{display:inline-block;text-align:justify}.weui-dialog .weui-dialog-ft{position:relative;line-height:4.3rem;border-top:1.5px solid #dfdfdf;display:-webkit-box;display:-moz-flex;display:-ms-flexbox;display:flex}.weui-dialog .weui-dialog-ft span{position:relative;display:block;-webkit-box-flex:1;-moz-flex:1;-ms-flex:1;flex:1;color:#21ac39;font-size:1.5rem;-webkit-tap-highlight-color:transparent}.weui-dialog .weui-dialog-ft span:first-child{border-right:1.5px solid #dfdfdf}.weui-dialog .weui-dialog-ft .cancel{color:#666;font-size:1.4rem}.weui-dialog-message .weui-mask-transparent{position:fixed;z-index:1000;top:0;right:0;left:0;bottom:0}.weui-dialog-message .weui-toast{background-color:rgba(0,0,0,.7);font-size:1.4rem;color:#fff;border-radius:1.4rem;background-clip:padding-box;padding:.8rem 2.4rem}.weui-dialog-window{border-radius:.4rem;background-clip:padding-box;background-color:#fff;width:92%;padding-bottom:1rem}.weui-dialog-window .head-inner{padding:1.4rem 0}.weui-dialog-window .title{border-left:.4rem solid #22ad3a;font-size:1.5rem;color:#262c2f;padding-left:.6rem}.weui-dialog-window .close-wrap{padding:0 1.4rem 0 2rem}.weui-dialog-window .dialog-inner{padding:0 1rem;overflow-x:hidden;overflow-y:scroll;-webkit-overflow-scrolling:touch}.weui-dialog-window .dialog-inner .dialog-content{padding:1rem 0;border-top:1.5px solid #d3d3d3;overflow-x:hidden;overflow-y:scroll;-webkit-overflow-scrolling:touch}.weui-dialog-window .dialog-inner .dialog-content img{max-width:100%}/*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 *  #border-radius {
 *    @include default-border-radius(25px);
 *  }
 *   
 *  #border-radius-top-left {
 *    @include top-left-border-radius(25px);
 *  }
 *  
 *  #border-radius-top-right {
 *    @include top-right-border-radius(25px);
 *  }
 *  #border-radius-bottom-left {
 *    @include bottom-left-border-radius(25px);
 *  }
 *  #border-radius-bottom-right {
 *    @include bottom-right-border-radius(25px);
 *  } 
 *  #border-radius-top {
 *    @include top-border-radius(25px, 25px)
 *  }
 *  #border-radius-bottom {
 *    @include bottom-border-radius(25px, 25px)
 *  }
 *  #border-radius-left {
 *    @include left-border-radius(25px, 25px)
 *  }
 *  #border-radius-right {
 *    @include right-border-radius(25px, 25px)
 *  }
 *  #border-radius-combo {
 *    @include combo-border-radius(50px, 30px, 15px, 80px)
 *  }
 *//*!
 * Mixins
 *//*!
 * Mixins
 */@-webkit-keyframes slideLn{0%{-webkit-transform:translate3d(0,100%,0);transform:translate3d(0,100%,0)}to{-webkit-transform:translateZ(0);transform:translateZ(0)}}@keyframes slideLn{0%{-webkit-transform:translate3d(0,100%,0);transform:translate3d(0,100%,0)}to{-webkit-transform:translateZ(0);transform:translateZ(0)}}@-webkit-keyframes slideOut{0%{-webkit-transform:translateZ(0);transform:translateZ(0)}to{-webkit-transform:translate3d(0,100%,0);transform:translate3d(0,100%,0)}}@keyframes slideOut{0%{-webkit-transform:translateZ(0);transform:translateZ(0)}to{-webkit-transform:translate3d(0,100%,0);transform:translate3d(0,100%,0)}}@-webkit-keyframes fadeIn{0%{opacity:0}to{opacity:1}}@keyframes fadeIn{0%{opacity:0}to{opacity:1}}@-webkit-keyframes fadeOut{0%{opacity:1}to{opacity:0}}@keyframes fadeOut{0%{opacity:1}to{opacity:0}}@-webkit-keyframes shake{0%,to{-webkit-transform:translateZ(0);transform:translateZ(0)}12.5%,37.5%,62.5%{-webkit-transform:translate3d(-10px,0,0);transform:translate3d(-10px,0,0)}25%,50%,75.5%{-webkit-transform:translate3d(10px,0,0);transform:translate3d(10px,0,0)}}@keyframes shake{0%,to{-webkit-transform:translateZ(0);transform:translateZ(0)}12.5%,37.5%,62.5%{-webkit-transform:translate3d(-10px,0,0);transform:translate3d(-10px,0,0)}25%,50%,75.5%{-webkit-transform:translate3d(10px,0,0);transform:translate3d(10px,0,0)}}/*!
 * Mixins
 *//*!
 * Mixins
 *//*!
 * Mixins   
 * size  btn-lg default sm  xs
 * color  default primary success info warning danger dark disabled
 * type  Rounded dropdowns icon groups components
 */.hr{height:1px;margin:.8rem 0;position:relative;border-bottom:1.5px solid #dfdfdf}.gap-h1{height:1.4rem;font-size:0;line-height:0;overflow:hidden;background-color:#f5f5f5}.quantity-form{position:relative;white-space:nowrap}.quantity-form .decrement,.quantity-form .increment{display:-moz-inline-stack;display:inline-block}.quantity-form .quantity{width:2rem;height:2.6rem;line-height:2.6rem;margin:0;text-align:center;font-size:1.2rem;-webkit-appearance:none;color:#262c2f;border:1px solid #fff;background-color:transparent;font-family:PingFang SC,Hiragino Sans GB,Microsoft YaHei,sans-serif;-moz-user-select:none;-webkit-user-select:none;color:#darkGray}.quantity-form .quantity:disabled{color:#darkGray;font-weight:700}.quantity-form .increment{background-position:0 -15.4rem}.quantity-form .increment,.quantity-form .increment.disabled{width:2.8rem;height:2.8rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.quantity-form .increment.disabled{background-position:-3.2rem -15.4rem}.quantity-form .decrement{background-position:-12.4rem -11.9rem}.quantity-form .decrement,.quantity-form .decrement.disabled{width:2.8rem;height:2.8rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.quantity-form .decrement.disabled{background-position:-9.2rem -11.9rem}.calendar-full-btn button,.opt-full-btn button{display:block;width:100%;background-color:#21ac39;height:4.95rem;font-size:1.8rem;color:#fff;border:0}.calendar-full-btn button sub,.opt-full-btn button sub{font-size:1.1rem;margin-right:.4rem}.calendar-full-btn button.disabled,.opt-full-btn button.disabled{background-color:#d3d3d3}.slidein{-webkit-animation-name:slideLn;animation-name:slideLn;-webkit-animation-iteration-count:1;animation-iteration-count:1;-webkit-animation-duration:.5s;animation-duration:.5s;-webkit-animation-delay:0s;animation-delay:0s;-webkit-animation-timing-function:ease;animation-timing-function:ease;-webkit-animation-fill-mode:none;animation-fill-mode:none}.slidein,.slideout{-webkit-backface-visibility:hidden;backface-visibility:hidden}.slideout{-webkit-animation-name:slideOut;animation-name:slideOut;-webkit-animation-iteration-count:1;animation-iteration-count:1;-webkit-animation-duration:.5s;animation-duration:.5s;-webkit-animation-delay:0s;animation-delay:0s;-webkit-animation-timing-function:ease;animation-timing-function:ease;-webkit-animation-fill-mode:none;animation-fill-mode:none}.fadein{-webkit-animation-name:fadeIn;animation-name:fadeIn;-webkit-animation-iteration-count:1;animation-iteration-count:1;-webkit-animation-duration:1s;animation-duration:1s;-webkit-animation-delay:0s;animation-delay:0s;-webkit-animation-timing-function:ease;animation-timing-function:ease;-webkit-animation-fill-mode:both;animation-fill-mode:both}.fadein,.fadeout{-webkit-backface-visibility:hidden;backface-visibility:hidden}.fadeout{-webkit-animation-name:fadeOut;animation-name:fadeOut;-webkit-animation-iteration-count:1;animation-iteration-count:1;-webkit-animation-duration:1s;animation-duration:1s;-webkit-animation-delay:0s;animation-delay:0s;-webkit-animation-timing-function:ease;animation-timing-function:ease;-webkit-animation-fill-mode:both;animation-fill-mode:both}.shake{-webkit-animation-name:shake;animation-name:shake;-webkit-animation-iteration-count:1;animation-iteration-count:1;-webkit-animation-duration:1s;animation-duration:1s;-webkit-animation-delay:0s;animation-delay:0s;-webkit-animation-timing-function:ease;animation-timing-function:ease;-webkit-animation-fill-mode:both;animation-fill-mode:both;-webkit-backface-visibility:hidden;backface-visibility:hidden}.tools-menu{position:fixed;bottom:0;left:0;width:100%;height:4.6rem;box-sizing:border-box;z-index:1000;background-color:hsla(0,0%,100%,.93)}.tools-menu .icon-wx-purchase{background-position:-5.4rem -18.6rem}.tools-menu .icon-my-order,.tools-menu .icon-wx-purchase{width:2.3rem;height:2rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.tools-menu .icon-my-order{background-position:0 -18.6rem}.tools-menu .icon-card-package{background-position:-17.75rem -15.4rem;width:2.3rem;height:2rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.tools-menu .inner-menu{display:-webkit-box;display:-moz-flex;display:-ms-flexbox;display:flex;width:100vw;position:relative}.tools-menu .inner-menu:before{content:"";position:absolute;width:200%;display:block;-webkit-transform-origin:0 0;transform-origin:0 0;box-sizing:border-box;-webkit-transform:scale(.5);transform:scale(.5);border-radius:0;background-clip:padding-box;border:0 solid #e8e8e8;left:0;top:0;border-top-width:1px}.iphone .tools-menu .inner-menu:before{border-top-width:2px}.iphoneplus .tools-menu .inner-menu:before{border-top-width:3px}.tools-menu .inner-menu>div{-webkit-box-flex:1;-moz-flex-grow:1;-ms-flex-positive:1;flex-grow:1;text-align:center;height:4rem;padding-top:.6rem;align-content:space-between;position:relative}.tools-menu .inner-menu>div .dots{position:absolute;width:.7rem;height:.7rem;border-radius:50%;background-clip:padding-box;background-color:#fc7700;left:50%;margin-left:.3rem;visibility:hidden}.tools-menu .inner-menu>div .dots.show{visibility:visible}.tools-menu .inner-menu>div span{display:block;font-size:1rem;color:#999;padding-top:.1rem}.tools-menu .inner-menu>div.active .icon-wx-purchase{background-position:-2.7rem -18.6rem}.tools-menu .inner-menu>div.active .icon-my-order,.tools-menu .inner-menu>div.active .icon-wx-purchase{width:2.3rem;height:2rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.tools-menu .inner-menu>div.active .icon-my-order{background-position:-8.1rem -18.6rem}.tools-menu .inner-menu>div.active .icon-card-package{background-position:-15.05rem -15.4rem;width:2.3rem;height:2rem;background-image:url("${base}/statics/images/wx/ticket/sprite.png?v=1494496928110");background-repeat:no-repeat;background-size:25.55rem 24.9rem;display:inline-block}.tools-menu .inner-menu>div.active span{color:#00c267}
    </style>
</head>
<body>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]

<!--header 开始-->
[#assign pageName="订单详情" ]
[#include "/wx/header.ftl"]
<!--header 结束-->

<div class="order-detail order-notpay">
    [#if entity.status?? && entity.status==3]
    <div class="countdown"><i class="icon-yl-clock">
    </i><span>请在</span><strong id="cancel-date"
            >[#-- 我的订单倒计时 --]
        [@order unix="${entity.createTime?c}" index="1" flag
        ="1"] [/@order]
    </strong><span>内完成支付</span>

        <div class="orange_stripes"></div>
    </div>
    [/#if]
    <div class="g-content">
        <div class="g-head">
            <div class="g-title">${entity.orderTitle}</div>
            <div class="g-status">
                [#if entity.status?? && entity.status==1]已付款[/#if]
                [#if entity.status?? && entity.status==2]支付失败[/#if]
                [#if entity.status?? && entity.status==3]待付款[/#if]
                [#if entity.status?? && entity.status==4]已关闭[/#if]
                [#if entity.status?? && entity.status==5]退款[/#if]
            </div>
        </div>
        <div class="g-number">共有${entity.ticketCount?c}张</div>
        <div class="g-validity">有效期：
            [#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
            [@formatTime unix="${entity.startTime?c}" pattern="yyyy.MM.dd"] [/@formatTime]-
            [#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
            [@formatTime unix="${entity.endTime?c}" pattern="yyyy.MM.dd"] [/@formatTime]
        </div>
    </div>

    <div class="separated"></div>
    <div class="split-bg">
        <div class="user-info">
            <div class="user-field">
                <div class="label">手机号</div>
                <div class="value">${entity.orderPhone}</div>
            </div>
        </div>
        <div class="money-info">
            <div class="money-field">
                <div class="label">商品总额</div>
                <div class="value"><sub>¥</sub><span>${entity.totalFee}</span></div>
            </div>

            <div class="money-field">
                <div class="label">实 付 款</div>
                <div class="value realy-pay"><sub>¥</sub><span>${entity.totalFee?c}</span></div>
            </div>
        </div>
        <div class="order-info">
            <div class="order-field">
                <div class="label">订 单 号</div>
                <div class="value">${entity.orderNo}</div>
            </div>
            <div class="order-field">
                <div class="label">下单时间</div>
                <div class="value">[@formatTime unix="${entity.createTime?c}" pattern="yyyy.MM.dd HH.mm.ss"]
                    [/@formatTime]
                </div>
            </div>
        </div>
    </div>


    [#if entity.status?? && entity.status==3]
    <div class="separated"></div>
    <div class="pay-style" node-name="paystyle">
        <div class="head">选择支付方式</div>
        <div class="pay-list">

            <div id="wechar_js" class="pay-item item-checked" data-key="WECHAT_JS">
                <div class="pay-icon"><i class="icon-pay-wechat"></i></div>
                <div class="pay-name">微信支付</div>
                <div class="pay-checkbox"><i class="icon-radio-default"></i></div>
            </div>

            [#--
            <div id='ys_wap' class="pay-item" data-key="YS_WAP">
                <div class="pay-icon"><i class="icon-pay-ys"></i></div>
                <div class="pay-name">正在开发中</div>
                <div class="pay-checkbox"><i class="icon-radio-default"></i></div>
            </div>
            --]

        </div>
    </div>
    [/#if]

    <div class="securities"></div>
    [#if entity.status?? && entity.status==3]
    <div class="pay-btn">
        <div class="order-opt-btn">
            <button type="button" id='nowPay' class="btn btn2" node-name="soonpay"><span>立即支付</span></button>
        </div>
    </div>
    [/#if]
</div>

[#-- Confirm UI --]
[#assign confirmId="delOrder-confirm"]
[#assign confirmTitle="温馨提示"]
[#assign confirmContent="删除订单后不可恢复，确定删除吗?"]
[#include "/wx/confirmUI.ftl"]


[#-- Confirm UI --]
[#assign confirmId="canOrder-confirm"]
[#assign confirmTitle="温馨提示"]
[#assign confirmContent="确定要取消此订单吗?"]
[#include "/wx/confirmUI.ftl"]

[#-- Alert UI --]
<div class="am-modal am-modal-alert" tabindex="-1" id="delOrder-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">提示</div>
        <div class="am-modal-bd" id="modal-alert">
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>
</body>
<script src="${base}/statics/js/wx/user/orderDetail.js"></script>
</html>
<script>

    $(function () {
        $("#ys_wap").click(function () {
            $("#wechar_js").removeClass("pay-item item-checked");
            $("#wechar_js").addClass("pay-item");
            if ($(this).hasClass("pay-item item-checked")) {
                $(this).removeClass("pay-item item-checked");
                $(this).addClass("pay-item");
            } else {
                $(this).removeClass("pay-item");
                $(this).addClass("pay-item item-checked");
            }
        });

        $("#wechar_js").click(function () {
            $("#ys_wap").removeClass("pay-item item-checked");
            $("#ys_wap").addClass("pay-item");
            if ($(this).hasClass("pay-item item-checked")) {
                $(this).removeClass("pay-item item-checked");
                $(this).addClass("pay-item");
            } else {
                $(this).removeClass("pay-item");
                $(this).addClass("pay-item item-checked");
            }
        });

    });
</script>