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
                    //**获取上传文件的真实文件名和保存文件名，并重新赋值给（插件临时创建）隐藏表单‘upload’**
                    //遍历class为‘item success’，获取当前上传成功的所有文件的原文件名
                    $(".filename").each(function(){
                        $("[name=upload]").val($("[name=upload]").val()+','+$(this).html());
                    });

                    //alert("上传文件名称："+$("[name=upload]").val());


                    var bootstrapValidator = $('#FormUserInfo').data('bootstrapValidator');//获取表单对象
                    bootstrapValidator.validate();//手动触发验证
                    if(bootstrapValidator.isValid())//全部验证通过，才能提交表单
                    {
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
                    }

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
                                        threshold: 2, //有2字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                        remote: {
                                            url: '/userinfo/validateUsername',
                                            data:{username:function(){return $("#username").val()}},
                                            message: '账号已存在',
                                            delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                            type: 'POST'//请求方式
                                        },
                                        regexp: {
                                            regexp: /^[a-zA-Z0-9_\.]+$/,
                                            message: '账号以字母开头，可包含数字'
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
                                },
                                mobile: {
                                    validators: {
                                        notEmpty: {
                                            message: '手机号不能为空'
                                        },
                                        threshold: 10, //有10字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                        remote: {
                                            url: '/userinfo/validateMobile',
                                            data:{mobile:function(){return $("#mobile").val()}},
                                            message: '手机号已存在',
                                            delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                            type: 'POST'//请求方式
                                        },
                                        regexp: {
                                            regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                                            message: '手机号码格式错误'
                                        }
                                    }
                                },
                                email: {
                                    validators: {
                                        notEmpty: {
                                            message: '邮箱不能为空'
                                        },
                                        threshold: 2, //有2字符以上才发送ajax请求,（input中输入一个字符，插件会向服务器发送一次，设置限制，2字符以上才开始）
                                        remote: {
                                            url: '/userinfo/validateEmail',
                                            data:{email:function(){return $("#email").val()}},
                                            message: '邮箱已存在',
                                            delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                                            type: 'POST'//请求方式
                                        },
                                        emailAddress: {
                                            message: '邮箱格式不正确'
                                        }
                                    }
                                }
                            }
                        })
                });


                //*****自定义功能块 EndRegion*****
            });
    });