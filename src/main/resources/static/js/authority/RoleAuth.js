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

                //加载权限树
                var setting = {
                    check: {
                        enable: true
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
                    async:true,//true为异步，false为同步
                    success:function(map){
                        var m=0;
                        for(var key in map)
                        {
                            m++;
                            var id="model"+m;
                            var str = $('<div class="col-md-3"><div class="panel panel-info" style="height:400px;overflow:auto"><div class="panel-heading">'+key+' <button id="plus'+id+'" class="btn btn-warning btn-xs pull-right" type="button"><i class="fa fa-plus-square"></i></button><button id="minus'+id+'" class="btn btn-warning btn-xs pull-right" type="button"><i class="fa fa-minus-square"></i></button></div><div class="panel-body" style="padding:2px"><div class="panel ztree" id="'+id+'"></div></div></div></div>');
                            $("#allAuthbody").append(str);
                            $.fn.zTree.init($("#"+id), setting, map[key]);

                            //在当前节点下增加子节点
                            $("#plus"+id).click(function(){

                                var authname=$("#nowname"+id).val();
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
                                //alert($("#nowid"+id).val()+";"+$("#nowpid"+id).val()+";"+$("#nowname"+id).val());
                            });
                        }

                    }

                });


                //自定义功能块，EndRegion



            })
    });


