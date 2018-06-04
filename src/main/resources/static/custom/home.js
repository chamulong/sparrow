/************
 * 前端页面主框架模块
 ***********/
require(
    ['/custom/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','metismenu','slimscroll','hplus','contabs','pace'],
            function($){
                //自定义功能块
            })
    });