/************
 * 添加用户信息
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','jqueryform','layer','jqueryupload','validator'],
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

                //利用bootstrapvalidator进行表单验证
                $(document).ready(function(){
                    $('#FormUserInfo')
                        .bootstrapValidator({
                            message: '输入的值无效',
                            feedbackIcons: {
                                valid: 'glyphicon glyphicon-ok',
                                invalid: 'glyphicon glyphicon-remove',
                                validating: 'glyphicon glyphicon-refresh'
                            },
                            fields: {
                                username: {
                                    message: '账号名无效',
                                    validators: {
                                        notEmpty: {
                                            message: '账号不能为空'
                                        },
                                        stringLength: {
                                            min: 2,
                                            max: 20,
                                            message: '账号长度在2-20个字符范围内'
                                        },
                                        /*remote: {
                                            url: 'remote.php',
                                            message: 'The username is not available'
                                        },*/
                                        regexp: {
                                            regexp: /^[a-zA-Z0-9_\.]+$/,
                                            message: '账号以字母开头，可包含数字'
                                        }
                                    }
                                },
                                email: {
                                    validators: {
                                        notEmpty: {
                                            message: '邮箱不能为空'
                                        },
                                        emailAddress: {
                                            message: '邮箱格式不正确'
                                        }
                                    }
                                },
                                password: {
                                    validators: {
                                        notEmpty: {
                                            message: '密码不能为空'
                                        },
                                        stringLength: {
                                            min: 8,
                                            max: 100,
                                            message: '密码长度在8-100个字符范围内'
                                        },
                                        regexp: {
                                            regexp: /^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,})$/,
                                            message: '密码必须同时包含字符和数字'
                                        }
                                    }
                                }
                            }
                        })
                });




                //*****自定义功能块 EndRegion*****
            });
    });