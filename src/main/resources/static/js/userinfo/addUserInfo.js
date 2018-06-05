/************
 * 添加用户信息
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','jqueryform','jqueryupload'],
            function($){

                //自定义功能块 region
                $("#btn_Save").click(function(){
                    var options = {
                        complete:function(data){
                            parent.layer.alert("新增员工【"+$('#username').val()+"】信息成功！", {
                                icon: 1,
                                closeBtn: 0,
                            },function(){parent.layer.closeAll();});
                        },
                        url:'/userinfo/save',
                        dataType:'json',
                        resetForm: true,  // 成功提交后，重置所有的表单元素的值
                        timeout: 5000
                    };
                    $('#FormUserInfo').ajaxSubmit(options);
                });
                //自定义功能块 EndRegion
            });
    });