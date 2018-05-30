//基于bootstrap table插件的员工列表展示
window.onload=function(){
    //文件上传
    $("#case").upload();

$('#tb_UserInfos').bootstrapTable({
        url: '/userinfo/list',         //请求后台的URL（*）
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
        pageList: [3, 5, 10, 20],        //可供选择的每页的行数（*）
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
                    username:$("#username").val(),
                    depname:$("#depname").val()
                };
            return temp;
            },
        columns: [{
            checkbox: true
        },{
            field: 'uuid',
            title: 'UUID',
            visible:false
        },{
            field: 'username',
            title: '姓名'
        }, {
            field: 'sextype',
            title: '性别'
        }, {
            field: 'depname',
            title: '所属部门名称'
        }, {
            field: 'birthdate',
            title: '出生日期'
        }, {
            field: 'nativeplace',
            title: '籍贯'
        }, {
            field: 'homeaddress',
            title: '家庭地址'
        }, {
            field: 'email',
            title: '邮箱'
        },{
            field: 'position',
            title: '职位'
        },{
            field: 'status',
            title: '状态',
            formatter: function indexFormatter(value, row, index){
                var newvalue="";
                if(value == "停用"){newvalue= '<span style="color:#ff4c25">'+value+'</span>';}
                else {newvalue= '<span style="color:#37b706">'+value+'</span>';}
                return newvalue;
            }
        }]
    });
};

//多条件查询
function searchRec()
{
    $("#tb_UserInfos").bootstrapTable('refresh');
}

//弹出新增员工窗口
function showAddUserInfo()
{
    parent.layer.open({
        id: 'addlayer',
        type: 2,
        skin: 'layui-layer-molv',
        title: '新增员工',
        shadeClose: true,
        shade: 0.4,
        maxmin: false,
        area: ['700px', '600px'],
        content: '/userinfo/addUserInfo.html',
        end: function () {
            $("#tb_UserInfos").bootstrapTable('refresh');
        }
    });
}

//保存新增员工信息
function saveUserInfo() {
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

//删除员工信息(一条或多条)
function deleteUserInfo()
{
    parent.layer.confirm('是否要删除选定的记录？',{
        icon: 0,
        btn:['取 消','确 定']
    },function(){
        parent.layer.closeAll();
    },function(){
        var uuids="";
        var arrData = $('#tb_UserInfos').bootstrapTable('getSelections');//获取选择行数据
        for (var i = 0; i < arrData.length; i++)
        {
            if(i==0){uuids=arrData[i].uuid;}
            else{uuids=uuids+"_"+arrData[i].uuid;}
        }
        $.ajax({
            url:'/userinfo/delete',
            type:'get',
            data:'uuids='+uuids,
            async:false,//true为异步，false为同步
            complete:function(){
                $("#tb_UserInfos").bootstrapTable('refresh');
            }

        });
    })
}




