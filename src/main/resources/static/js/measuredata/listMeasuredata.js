/************
 * 测量信息模块
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','bootstraptable','bootstraptableCN','layer'],
            function($){
                //***自定义功能块 Region***

                //绑定列表中各按钮的事件
                window.operateEvents={
                    'click .delete':function(e,value,row,index) {
                        parent.layer.confirm('是否要彻底删除['+row.uuid+']？',{
                            icon: 0,
                            btn:['取 消','确 定']
                        },function(){
                            parent.layer.closeAll();
                        },function(){
                            var uuid = row.uuid;
                            $.ajax({
                                url: '/measuredata/deleteByUuid',
                                type: 'post',
                                data: {uuid: uuid},
                                async: true,//true为异步，false为同步
                                complete: function () {
                                    $("#tb_Measuredatas").bootstrapTable('refresh');
                                }

                            });
                        });

                    }
                };

                //判断行详情和行删除按钮是否存在，用于处理是否显示对应的按钮
                var blRowDelete=true;
                if($("#rowdelete").length>0){blRowDelete=true;}else{blRowDelete=false;}
                //数据列表展示
                $('#tb_Measuredatas').bootstrapTable({
                    url: '/measuredata/list',         //请求后台的URL（*）
                    method: 'post',                      //请求方式（*）post/get
                    //contentType: "application/json",//post请求的话就加上这句话
                    //toolbar: '#toolbar',                //工具按钮用哪个容器
                    striped: true,                      //是否显示行间隔色
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber:1,                       //初始化加载第一页，默认第一页
                    pageSize: 5,                       //每页的记录行数（*）
                    pageList: [2, 5, 10, 20],        //可供选择的每页的行数（*）
                    search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch: true,
                    showColumns: false,                  //是否显示所有的列
                    showRefresh: false,                  //是否显示刷新按钮
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                    showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
                    cardView: false,                    //是否显示详细视图
                    detailView: false,                   //是否显示父子表
                    queryParams : function (params) {
                        //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
                        var temp = {
                            size: params.limit,//页面大小
                            page: (params.offset / params.limit),//页码
                            //页面动态查询条件
                            poiname:$("#poiname").val(),
                            datatype:$("#datatype").val()
                        };
                        return temp;
                    },
                    columns: [{
                        title:'编号',
                        width:'60',
                        align:'center',
                        formatter:function(value,row,index){
                            return index+1;
                        }
                    },{
                        field: 'uuid',
                        title: 'UUID',
                        width:'300',
                        visible:true
                    },{
                        field: 'poiname',
                        title: '测点名称'
                    }, {
                        field: 'datatype',
                        title: '数据类型',
                        width:'60'
                    }, {
                        field: 'num',
                        title: '数量'
                    }, {
                        field: 'unitprice',
                        title: '位移(mm)'
                    },{
                        field:'',
                        title:'操 作',
                        width:'150',
                        events:operateEvents,
                        formatter:function (value, row, index){
                            var btnInfo='';
                            if(blRowDelete)
                            {
                                btnInfo+='<button style="margin-right: 10px;padding: 2px" type="button" class="delete btn btn-outline btn-danger btn-sm">删 除</button>';
                            }

                            return btnInfo;
                        }
                    }]
                });

                //多条件查询刷新
                $("#btnSearch").click(function(){
                    $("#tb_Measuredatas").bootstrapTable('refresh');
                });

                //弹出新增窗口
                $("#btn_add").on('click',function(){
                    parent.layer.open({
                        type: 2,
                        skin: 'layui-layer-molv',
                        title: '新增数据',
                        shadeClose: true,
                        shade: 0.2,
                        maxmin: false,
                        area: ['35%', '30%'],
                        content: '/measuredata/addMeasuredata.html',
                        end: function () {
                            $("#tb_Measuredatas").bootstrapTable('refresh');
                        }
                    });
                });


                //***自定义功能块 EndRegion***
            });
    });