/************
 * 修改用户信息
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
                    var bootstrapValidator = $('#FormUserInfo').data('bootstrapValidator');//获取表单对象
                    //$('#FormUserInfo').data('bootstrapValidator').validateField('email');//触发email验证
                    //$('#FormUserInfo').data('bootstrapValidator').validateField('mobile');//触发mobile验证
                    bootstrapValidator.validate();//触发全部的验证
                    if(bootstrapValidator.isValid())//全部验证通过，才能提交表单
                    {
                        var options = {
                            complete:function(data){
                                parent.layer.alert("员工【"+$('#username').val()+"】信息修改成功！", {
                                    icon: 1,
                                    closeBtn: 0,
                                },function(){parent.layer.closeAll();});
                            },
                            url:'/userinfo/modify',
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
                                            url: '/userinfo/validateMobileModify',
                                            data:{
                                                mobile:function(){return $("#mobile").val()},
                                                uuid:function(){return $("#uuid").val()}},
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
                                            url: '/userinfo/validateEmailModify',
                                            data:{
                                                mobile:function(){return $("#mobile").val()},
                                                uuid:function(){return $("#uuid").val()}},
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

                //利用正则表达式，获取url中的参数值
                function getQueryString(name) {
                    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null) {
                        return unescape(r[2]);
                    }
                    return null;
                }

                //获取用户的UUID，查询数据，并填充到表单中
                var uuid=getQueryString("uuid");
                $.ajax({
                    url: '/userinfo/findByUuid',
                    type: 'post',
                    data: {uuid: uuid},
                    async: true,//true为异步，false为同步
                    success: function (data) {
                        //把json字符串转换成json对象（fastjson的不足，作为字符串反馈时有斜杠）
                        var jsonObj=$.parseJSON(data);
                        $("#uuid").val(jsonObj.uuid);
                        $("#username").val(jsonObj.username);
                        $("#realname").val(jsonObj.realname);
                        $("#depname").val(jsonObj.depname);
                        $("#birthdate").val(jsonObj.birthdate);
                        $("#nativeplace").val(jsonObj.nativeplace);
                        $("#homeaddress").val(jsonObj.homeaddress);
                        $("#email").val(jsonObj.email);
                        $("#mobile").val(jsonObj.mobile);
                        $("#position").val(jsonObj.position);
                    }

                });



                //*****自定义功能块 EndRegion*****
            });
    });