/************
 * 用户报表模块
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap'],
            function($){
                //页面展示报表
                $.ajax({
                    url:'/userinforeport_javabean',
                    type:'post',
                    async:true,//true为异步，false为同步
                    success:function(data){
                        $("#userinforeport").html(data);
                    }

                });

                //导出Excel
                $("#btn_ExportExcel").click(function(){
                    window.location="/userinforeport_xls";
                });

                //导出PDF
                $("#btn_ExportPDF").click(function(){
                    window.location="/userinforeport_PDF";
                });



            });
    });