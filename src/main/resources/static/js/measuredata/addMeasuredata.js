/************
 * 添加现场测量信息
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','jqueryform','layer','validator'],
            function($){
                //***自定义功能块 Region***

                //保存信息
                $("#btn_Save").click(function(){
                   var bootstrapValidator = $('#FormMeasuredata').data('bootstrapValidator');//获取表单对象
                    bootstrapValidator.validate();//手动触发验证
                    if(bootstrapValidator.isValid())//全部验证通过，才能提交表单
                    {
                        var options = {
                            complete:function(data){
                                parent.layer.alert("新增数据【"+$('#poiname').val()+"】成功！", {
                                    icon: 1,
                                    closeBtn: 0,
                                },function(){parent.layer.closeAll();});
                            },
                            url:'/measuredata/save',
                            dataType:'json',
                            resetForm: true,  // 成功提交后，重置所有的表单元素的值
                            timeout: 5000
                        };
                        $('#FormMeasuredata').ajaxSubmit(options);
                    }

                });

                //利用bootstrapvalidator进行表单验证
                $(document).ready(function(){
                    $('#FormMeasuredata')
                        .bootstrapValidator({
                            message: '输入的值无效',
                            feedbackIcons: {
                                valid: 'glyphicon glyphicon-ok',
                                invalid: 'glyphicon glyphicon-remove',
                                validating: 'glyphicon glyphicon-refresh'
                            },
                            fields: {
                                poiname: {
                                    validators: {
                                        notEmpty: {
                                            message: '测量名称不能为空'
                                        },
                                        stringLength: {
                                            max: 15,
                                            message: '姓名不大于15个字符'
                                        }
                                    }
                                },
                                num: {
                                    validators: {
                                        notEmpty: {
                                            message: '测量点数量不能为空'
                                        },
                                        regexp: {
                                            regexp: /^[0-9]*[1-9][0-9]*$/,//正整数
                                            message: '必须是非0整数'
                                        }
                                    }
                                },
                                unitprice: {
                                    validators: {
                                        notEmpty: {
                                            message: '位移量不能为空'
                                        },
                                        regexp: {
                                            regexp: /^\d+(\.\d+)?$/,//浮点数
                                            message: '必须是浮点数'
                                        }
                                    }
                                }
                            }
                        })
                });



                //***自定义功能块 EndRegion***
            });
    });
