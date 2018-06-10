/************
 * 管理主功能模块，包括新增或者重命名
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','bootstraptable','bootstraptableCN','layer','jqueryform'],
            function($){
                //自定义功能块Region

                //数据列表展示
                $('#tb_MainAuth').bootstrapTable({
                    url: '/roleauth/findMainAuth',         //请求后台的URL（*）
                    method: 'post',                      //请求方式（*）post/get
                    columns: [{
                        field: 'uuid',
                        title: 'UUID',
                        visible:false
                    },{
                        field: 'name',
                        title: '模块名称',
                        width:'80%'
                    },{
                        field:'',
                        title:'操 作',
                        align:'center',
                        width:'20%',
                        formatter:function (value, row, index){
                            return[
                                '<button style="margin-right: 10px" type="button" class="delete btn btn-outline btn-danger btn-sm"> 删 除 </button>'
                            ].join('');
                        }
                    }]
                });

                //添加主功能模块
                $("#addMainRole").click(function(){
                    //判断主功能模块功能是否重复
                    var namenew=$('#name').val();
                    var arrData = $('#tb_MainAuth').bootstrapTable('getData');//获取全部行数据
                    for (var i = 0; i < arrData.length; i++)
                    {
                        var nameexist=arrData[i].name;
                        if(namenew==nameexist)
                        {
                            alert("【"+namenew+"】已存在！");
                            return false;
                        }
                    }

                    //保存新增功能模块名称
                    var options = {
                        complete:function(data){
                            alert("新增一级功能【"+namenew+"】信息成功！");
                            $('#name').val('');
                            $("#tb_MainAuth").bootstrapTable('refresh');
                        },
                        url:'/roleauth/saveMainAuth',
                        dataType:'json',
                        resetForm: true,  // 成功提交后，重置所有的表单元素的值
                        timeout: 5000
                    };
                    $('#formaddMainRole').ajaxSubmit(options);

                });

                //自定义功能块EndRegion
            });
    });