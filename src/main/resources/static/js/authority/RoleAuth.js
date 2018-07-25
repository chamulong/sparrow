/************
 * 有关角色权限相关的脚本控制都在这个文件中
 * 2018.06.01 江成军
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','ztree','layer','jqueryform'],
            function($){
                //自定义功能块，region

                //弹出新增角色层
                $("#btn_addrole").click(function(){
                    layer.open({
                        type: 1,
                        title: '新增角色',
                        shadeClose: true,
                        shade: 0.2,
                        maxmin: false,
                        skin: 'layui-layer-molv', //加上边框
                        area: ['320px', '190px'], //宽高
                        content: $('#addrolediv')
                    });

                });

                //保存新增角色
                $("#btn_SaveRole").click(function(){
                    //遍历页面上所有radio，得到已有的角色名称，并与输入项进行比较，判断是否重复
                    var newRoleName=$("#name").val();
                    var items = document.getElementsByName("radiorole");
                    for(var i=0,n=items.length;i<n;i++)
                    {
                        var tempName=items[i].nextSibling.nodeValue;//获取每个radio的显示文本（不是value）
                        if(tempName==newRoleName)
                        {
                            alert("角色名称重复，请重新输入!");
                            return false;
                        }
                    }

                    //保存数据到数据库
                    var options = {
                        complete:function(data){
                            layer.alert("新增角色【"+newRoleName+"】信息成功！", {
                                icon: 1,
                                closeBtn: 0,
                            },function(){ window.location.reload();});
                        },
                        url:'/roleauth/save',
                        dataType:'json',
                        resetForm: true,  // 成功提交后，重置所有的表单元素的值
                        timeout: 5000
                    };
                    $('#FormSysRole').ajaxSubmit(options);
                });

                //删除角色
                $("#btn_deleterole").click(function(){
                     var seluuid=$('input:radio[name="radiorole"]:checked').val();
                    parent.layer.confirm('是否要删除选定的角色？',{
                        icon: 0,
                        closeBtn:0,
                        btn:['取 消','确 定']
                    },function(){
                        parent.layer.closeAll();
                    },function(){
                        $.ajax({
                            url:'/roleauth/deleterole',
                            type:'post',
                            data:{uuid:seluuid},
                            async:true,//true为异步，false为同步
                            complete:function(){
                                window.location.reload();
                            }

                        });
                    });

                });

                //弹出一级功能编辑窗口
                $("#btn_managermainfun").click(function(){
                    parent.layer.open({
                        type: 2,
                        skin: 'layui-layer-molv',
                        title: '一级功能模块管理',
                        shadeClose: true,
                        shade: 0.2,
                        maxmin: false,
                        area: ['800px', '550px'], //宽高
                        content:'/authority/managerMainAuth.html'
                    });

                });

                //保存添加的权限
                $("#btn_SaveAuth").click(function(){
                    var newAuthName=$("#authname").val();
                    var nowid=$("#nowid").val();

                    //根据新增加的子节点名称和当前节点的id，判断子节点名称是否重复,如果不重复则保存数据
                    $.ajax({
                        url:'/roleauth/saveChildAuth',
                        type:'post',
                        data:{"id":nowid,"name":newAuthName},
                        async:true,//true为异步，false为同步
                        success:function(data){
                            var msg=data.msg;
                            if(msg=="exist")
                            {
                                alert("节点名称已存在，请重新添加！")
                            }
                            else
                            {
                                alert("节点添加成功！");
                            }

                            $("#nowid").val("无");
                            $("#nowpid").val("无");
                            $("#nowname").val("无");

                            window.location.reload();
                        }

                    });

                });

                //角色勾选状态变化后，重新加载权限树
                $('input:radio[name="radiorole"]').change( function(){
                    showAuthTree();
                })


                //******加载权限树 Start******

                var arrModuleID=new Array();//用于记录全部树的id

                //判断行详情和行删除按钮是否存在，用于处理是否显示对应的按钮
                var blmodulAuth_add=true;
                var blmodulAuth_delete=true;
                if($("#modulAuth_add").length>0){blmodulAuth_add=true;}else{blmodulAuth_add=false;}
                if($("#modulAuth_delete").length>0){blmodulAuth_delete=true;}else{blmodulAuth_delete=false;}


                function showAuthTree()
                {
                    //清空系统权限模块页面内容
                    $("#allAuthbody").html("");

                    //获取对应的角色
                    var seluuid=$('input:radio[name="radiorole"]:checked').val();
                    if(seluuid==null)//如果没有选中任何角色，则赋一个默认值
                    {
                        seluuid="nouuid";
                    }

                    //ztree参数设置
                    var setting = {
                        check: {
                            enable: true,
                            chkboxType: {"Y":"p","N":"ps"}
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        callback:{
                            onClick: onClick
                        }
                    };

                    //节点单击事件，获取节点的相关信息，并赋值给统一的隐藏表单
                    function onClick(event, treeId, treeNode, clickFlag)
                    {
                        $("#nowid").val(treeNode.id);
                        $("#nowpid").val(treeNode.pId);//当前节点为根节点时，该值为空
                        $("#nowname").val(treeNode.name);
                    }

                    //加载权限明细树
                    $.ajax({
                        url:'/roleauth/listAuth',
                        type:'post',
                        datatype:'json',
                        data:{roleuuid:seluuid},
                        async:true,//true为异步，false为同步
                        success:function(map){
                            var m=0;
                            arrModuleID=new Array();
                            for(var key in map)
                            {
                                var id="model"+m;
                                arrModuleID[m]=id;
                                var btnInfo='';
                                if(blmodulAuth_add)//根据权限要求动态添加‘添加按钮’
                                {
                                    btnInfo+=' <button id="plus'+id+'" class="btn btn-warning btn-xs pull-right" type="button"><i class="fa fa-plus-square"></i></button>';
                                }
                                if(blmodulAuth_delete)//根据权限要求动态添加‘删除按钮’
                                {
                                    btnInfo+='<button id="minus'+id+'" class="btn btn-warning btn-xs pull-right" type="button"><i class="fa fa-minus-square"></i></button>';
                                }

                                var str = $('<div class="col-md-3"><div class="panel panel-info" style="height:400px;overflow:auto"><div class="panel-heading">'+key+btnInfo+'</div><div class="panel-body" style="padding:2px"><div class="panel ztree" id="'+id+'"></div></div></div></div>');
                                $("#allAuthbody").append(str);
                                $.fn.zTree.init($("#"+id), setting, map[key]);

                                //在当前节点下增加子节点
                                $("#plus"+id).click(function(){

                                    var authname=$("#nowname").val();
                                    if(authname=="无")
                                    {
                                        alert("请先点击相应的节点，再进行操作！");
                                        return;
                                    }

                                    layer.open({
                                        type: 1,
                                        title: '增加【'+authname+'】的子节点',
                                        shadeClose: true,
                                        shade: 0.2,
                                        maxmin: false,
                                        skin: 'layui-layer-molv', //加上边框
                                        area: ['320px', '190px'], //宽高
                                        content: $('#addauthdiv')
                                    });

                                });

                                //删除当前节点（如果有子节点则一并删除）
                                $("#minus"+id).click(function(){
                                    var authname=$("#nowname").val();
                                    var id=$("#nowid").val();
                                    if(authname=="无")
                                    {
                                        alert("请先点击相应的节点，再进行操作！");
                                        return;
                                    }

                                    parent.layer.confirm('是否删除节点【'+authname+'】？',{
                                        icon: 0,
                                        btn:['取 消','确 定']
                                    },function(index){
                                        parent.layer.close(index);
                                    },function(index){
                                        $.ajax({
                                            url:'/roleauth/deleteByChild',
                                            type:'post',
                                            data:{id:id},
                                            async:true,//true为异步，false为同步
                                            success:function(){
                                                $("#nowid").val("无");
                                                $("#nowpid").val("无");
                                                $("#nowname").val("无");
                                                window.location.reload();
                                            }

                                        });
                                    });

                                });

                                m++;
                            }

                        }

                    });
                }
                //******加载权限树 End******


                showAuthTree();

                //保存相应权限勾选的权限
                $("#btn_saveRoleAuth").click(function(){
                    var seluuid=$('input:radio[name="radiorole"]:checked').val();
                    if(seluuid==null)
                    {
                        alert("请先选中对应的角色");
                        return;
                    }

                    var arrAuths=new Array()//存放勾选的所有权限id的数组
                    var m=0;
                    for(var i=0,num=arrModuleID.length;i<num;i++)
                    {
                        var zTreeObj = $.fn.zTree.getZTreeObj(arrModuleID[i]);
                        var checkedNodes = zTreeObj.getCheckedNodes();
                        for(var j=0,len=checkedNodes.length;j<len;j++)
                        {
                            arrAuths[m]=checkedNodes[j].id;
                            m++;
                        }
                    }

                    var strAuths=arrAuths.join('$');//转换成以'$'分割的字符串

                    //把角色对应的所有权限映射进行保存
                    $.ajax({
                        url:'/roleauth/editRole',
                        type:'post',
                        data:{uuid:seluuid,authinfo:strAuths},
                        async:true,//true为异步，false为同步
                        success:function(){
                            alert("角色对应的权限分配成功！");
                        }

                    });


                });






                //自定义功能块，EndRegion



            })
    });


