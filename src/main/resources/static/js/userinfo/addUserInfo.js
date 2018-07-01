/************
 * 添加用户信息
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','jqueryform','layer','jqueryupload'],
            function($){
                //*****自定义功能块 region*****

                //从后台获取角色，自动填充到下拉表单
                $.ajax({
                    url:'/roleauth/roles',
                    type:'post',
                    async:true,//true为异步，false为同步
                    success:function(data){
                        var options = '';
                        for (var i = 0; i <data.length; i++)
                        {
                            options += '<option value="' + data[i].uuid + '">' + data[i].name + '</option>';
                        }
                        $("#roleuuid").html(options);
                    }

                });


                //文件上传
                $("#case").upload();

                //保存员工信息
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
                //*****自定义功能块 EndRegion*****
            });
    });