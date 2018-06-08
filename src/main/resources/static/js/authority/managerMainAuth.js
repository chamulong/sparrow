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

                //自定义功能块EndRegion
            });
    });