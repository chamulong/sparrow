/************
 * 江成军，系统日志展示模块
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','bootstraptable','bootstraptableCN'],
            function($){

                //数据列表展示
                $('#tb_SystemLog').bootstrapTable({
                    url: '/systemlog/list',         //请求后台的URL（*）
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
                    pageSize: 15,                       //每页的记录行数（*）
                    pageList: [12,15,20],        //可供选择的每页的行数（*）
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
                            realname:$("#realname").val(),
                            operetetype:$("#operetetype").val(),
                            operetedesc:$("#operetedesc").val()
                        };
                        return temp;
                    },
                    columns: [{
                        field: 'uuid',
                        title: 'UUID',
                        visible:false
                    },{
                        title: '编号',
                        formatter:function(value,row,index)
                        {
                            var pageSize=$('#tb_SystemLog').bootstrapTable('getOptions').pageSize;
                            var pageNumber=$('#tb_SystemLog').bootstrapTable('getOptions').pageNumber;
                            return pageSize * (pageNumber - 1) + index + 1;
                        },
                        align:'center',
                        width:'50'
                    }, {
                        field: 'username',
                        title: '账号',
                        visible:false
                    }, {
                        field: 'realname',
                        title: '姓名',
                        width:'60'
                    }, {
                        field: 'depname',
                        title: '所属部门'
                    }, {
                        field: 'operatetime',
                        title: '操作时间',
                        width:'140'
                    },{
                        field: 'operetetype',
                        title: '操作简述',
                        formatter: function indexFormatter(value, row, index){
                            var newvalue="";
                            if(value.indexOf("删除")>-1){newvalue= '<span style="color:#ff4c25">'+value+'</span>';}
                            else if(value.indexOf("修改")>-1) {newvalue= '<span style="color:#b7820f">'+value+'</span>';}
                            else if(value.indexOf("添加")>-1) {newvalue= '<span style="color:#29b709">'+value+'</span>';}
                            else if(value.indexOf("登录")>-1) {newvalue= '<span style="color:#297db7">'+value+'</span>';}
                            else if(value.indexOf("查询")>-1) {newvalue= '<span style="color:#696969">'+value+'</span>';}
                            return newvalue;
                        }
                    }, {
                        field: 'operetedesc',
                        title: '操作内容',
                        width:'30%'

                    }, {
                        field: 'osname',
                        title: '操作系统',
                        width:'110'
                    }, {
                        field: 'browser',
                        title: '浏览器',
                        width:'80'
                    },{
                        field: 'ip',
                        title: 'IP',
                        width:'80'
                    },{
                        field: 'methodname',
                        title: '调用方法',
                        width:'80'
                    },{
                        field: 'classname',
                        title: '调用类',
                        formatter:function(value)
                        {
                            var arrInfo=value.split('.');
                            var len=arrInfo.length;
                            var info="";
                            for(var i=3;i<len;i++)
                            {
                                info=info+arrInfo[i]+".";
                            }
                            info=info.substr(0,info.length-1);

                            return info;
                        }
                    }]
                });

                //多条件查询刷新
                $("#btnSearch").click(function(){
                    $("#tb_SystemLog").bootstrapTable('refresh');
                });







            });
    });