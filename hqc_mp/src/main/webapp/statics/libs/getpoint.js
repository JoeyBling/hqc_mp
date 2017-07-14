window.qq = window.qq || {};
qq.maps = qq.maps || {};
window.soso || (window.soso = qq);
soso.maps || (soso.maps = qq.maps);
(function () {
    function getScript(src) {
        var protocol = (window.location.protocol == "https:") ? "https://" : "http://";
        src = src && (src.indexOf("http://") === 0 || src.indexOf("https://") === 0) ? src : protocol + src;
        document.write('<' + 'script src="' + src + '"' +' type="text/javascript"><' + '/script>');
    }
    qq.maps.__load = function (apiLoad) {
        delete qq.maps.__load;
        apiLoad([["2.4.63","",0],["open.map.qq.com/","apifiles/2/4/63/mods/","open.map.qq.com/apifiles/2/4/63/theme/",true],[1,18,34.519469,104.461761,4],[1495259283533,"pr.map.qq.com/pingd","pr.map.qq.com/pingd"],["apis.map.qq.com/jsapi","apikey.map.qq.com/mkey/index.php/mkey/check","sv.map.qq.com/xf","sv.map.qq.com/boundinfo","sv.map.qq.com/rarp","apis.map.qq.com/api/proxy/search","apis.map.qq.com/api/proxy/routes/","confinfo.map.qq.com/confinfo"],[[null,["rt0.map.gtimg.com/tile","rt1.map.gtimg.com/tile","rt2.map.gtimg.com/tile","rt3.map.gtimg.com/tile"],"png",[256,256],1,19,"114",true,false],[null,["m0.map.gtimg.com/hwap","m1.map.gtimg.com/hwap","m2.map.gtimg.com/hwap","m3.map.gtimg.com/hwap"],"png",[128,128],4,18,"110",false,false],[null,["p0.map.gtimg.com/sateTiles","p1.map.gtimg.com/sateTiles","p2.map.gtimg.com/sateTiles","p3.map.gtimg.com/sateTiles"],"jpg",[256,256],1,19,"101",false,false],[null,["rt0.map.gtimg.com/tile","rt1.map.gtimg.com/tile","rt2.map.gtimg.com/tile","rt3.map.gtimg.com/tile"],"png",[256,256],1,19,"",false,false],[null,["sv0.map.qq.com/hlrender/","sv1.map.qq.com/hlrender/","sv2.map.qq.com/hlrender/","sv3.map.qq.com/hlrender/"],"png",[256,256],1,19,"",false,false],[null,["rtt2.map.qq.com/rtt/","rtt2a.map.qq.com/rtt/","rtt2b.map.qq.com/rtt/","rtt2c.map.qq.com/rtt/"],"png",[256,256],1,19,"",false,false],null,[["rt0.map.gtimg.com/vector/","rt1.map.gtimg.com/vector/","rt2.map.gtimg.com/vector/","rt3.map.gtimg.com/vector/"],[256,256],4,18,"114",["rt0.map.gtimg.com/icons/","rt1.map.gtimg.com/icons/","rt2.map.gtimg.com/icons/","rt3.map.gtimg.com/icons/"]],null],["s.map.qq.com/TPano/v1.1.2/TPano.js","map.qq.com/",""]],loadScriptTime);
    };
    var loadScriptTime = (new Date).getTime();
    getScript("open.map.qq.com/apifiles/2/4/63/main.js");
})();