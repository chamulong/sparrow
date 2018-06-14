/************
 * 有关角色权限相关的脚本控制都在这个文件中
 * 2018.06.01 江成军
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','ztree','layer','jqueryform','contextify'],
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

                //弹出主功能编辑窗口
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
                        onRightClick: onRightClick
                    }
                };

                //加载权限明细树
                $.ajax({
                    url:'/roleauth/listAuth',
                    type:'post',
                    datatype:'json',
                    async:true,//true为异步，false为同步
                    success:function(data){
                        var sysauths=eval(data);
                        var partauth=[];
                        var blStart=false;//是否开始建立树
                        var totalcount=sysauths.length-1;
                        var m=0;
                        var modulename="";
                        for(var i in sysauths)
                        {
                            if(sysauths[i].pid==0)
                            {
                                if(blStart)
                                {
                                    var id="model"+m;
                                    var str = $('<div class="col-md-3"><div class="panel panel-info" style="height:400px;overflow:auto"><div class="panel-heading">'+modulename+'</div><div class="panel-body" style="padding:2px"><div class="panel ztree" id="'+id+'"></div></div></div></div>');
                                    $("#allAuthbody").append(str);
                                    $.fn.zTree.init($("#"+id+""), setting, partauth);

                                    m++;
                                    partauth=[];
                                    var row={};
                                    modulename=sysauths[i].name;
                                    row.id=sysauths[i].id;
                                    row.pId=sysauths[i].pid;
                                    row.name=sysauths[i].treename;
                                    row.open=true;
                                    row.right=false;
                                    partauth.push(row);
                                    continue;
                                }
                                else
                                {
                                    modulename=sysauths[i].name;
                                    blStart=true;
                                    var row={};
                                    row.id=sysauths[i].id;
                                    row.pId=sysauths[i].pid;
                                    row.name=sysauths[i].treename;
                                    row.open=true;
                                    row.right=false;
                                    partauth.push(row);
                                    continue;
                                }

                            }

                            if(blStart)
                            {
                                var row={};
                                row.id=sysauths[i].id;
                                row.pId=sysauths[i].pid;
                                row.name=sysauths[i].treename;
                                row.open=true;
                                row.right=true;
                                partauth.push(row);

                                if(i==totalcount)
                                {
                                    var id="model"+m;
                                    var str = $('<div class="col-md-3"><div class="panel panel-info" style="height:400px;overflow:auto"><div class="panel-heading">'+modulename+'</div><div class="panel-body" style="padding:2px"><div class="panel ztree" id="'+id+'"></div></div></div></div>');
                                    $("#allAuthbody").append(str);
                                    $.fn.zTree.init($("#"+id+""), setting, partauth);
                                }
                            }

                        }

                    }

                });

                //右键进行节点的编辑(添加、改名、删除)
                function onRightClick(event, treeId, treeNode)
                {
                    if(treeNode.getParentNode().id!=0)
                    {
                        //借助contextify插件实现右键菜单
                        var options = {items:[
                                {header: 'Options'},
                                {text: 'First Link', href: '#'},
                                {text: 'Second Link', onclick: function(e) {
                                        alert('Hello ' + e.data.name);
                                    }},
                                {divider: true},
                                {text: 'Stuff', href: '#'}
                            ]}
                        $('.foo').contextify(options);
                    }
                }

                //自定义功能块，EndRegion



            })
    });


