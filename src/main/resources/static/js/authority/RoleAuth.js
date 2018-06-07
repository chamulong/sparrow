/************
 * 有关角色权限相关的脚本控制都在这个文件中
 * 2018.06.01 江成军
 ***********/
require(
    ['/js/GlobleConfig.js'],
    function(){
        requirejs(
            ['jquery','bootstrap','ztree','layer'],
            function($){
                //自定义功能块，region

                //增加角色
                $("#btn_addrole").click(function(){
                    layer.open({
                        type: 1,
                        title: '新增角色',
                        shadeClose: false,
                        skin: 'layui-layer-molv', //加上边框
                        area: ['320px', '190px'], //宽高
                        content: $('#addrolediv')
                    });

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








                var setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };

                var zNodes1 =[
                    { id:1, pId:0, name:"随意勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"},
                    { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
                    { id:21, pId:2, name:"随意勾选 2-1"},
                    { id:22, pId:2, name:"随意勾选 2-2", open:true},
                    { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
                    { id:222, pId:22, name:"随意勾选 2-2-2"},
                    { id:23, pId:2, name:"随意勾选 2-3"}
                ];

                var zNodes2 =[
                    { id:1, pId:0, name:"随意勾选勾选勾选勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选沃尔特违法的普及率放得开 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选沃尔特违法的普及率放得开 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"}

                ];

                var zNodes3 =[
                    { id:1, pId:0, name:"随意勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:113, pId:11, name:"随意勾选 1-1-2"},
                    { id:114, pId:11, name:"系统管理"},
                    { id:115, pId:11, name:"路面破损调查详情表"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"},
                    { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
                    { id:21, pId:2, name:"随意勾选 2-1"},
                    { id:22, pId:2, name:"随意勾选 2-2", open:true},
                    { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
                    { id:222, pId:22, name:"随意勾选 2-2-2"},
                    { id:23, pId:2, name:"随意勾选 2-3"}
                ];

                var zNodes4 =[
                    { id:1, pId:0, name:"随意勾选勾选勾选勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选沃尔特违法的普及率放得开 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选沃尔特违法的普及率放得开 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true}
                ];

                var zNodes5 =[
                    { id:1, pId:0, name:"随意勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"},
                    { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
                    { id:21, pId:2, name:"随意勾选 2-1"},
                    { id:22, pId:2, name:"随意勾选 2-2", open:true},
                    { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
                    { id:222, pId:22, name:"随意勾选 2-2-2"},
                    { id:23, pId:2, name:"随意勾选 2-3"}
                ];

                var zNodes6 =[
                    { id:1, pId:0, name:"随意勾选勾选勾选勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选沃尔特违法的普及率放得开 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选沃尔特违法的普及率放得开 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"}

                ];

                var zNodes7 =[
                    { id:1, pId:0, name:"随意勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:113, pId:11, name:"随意勾选 1-1-2"},
                    { id:114, pId:11, name:"系统管理"},
                    { id:115, pId:11, name:"路面破损调查详情表"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"},
                    { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
                    { id:21, pId:2, name:"随意勾选 2-1"},
                    { id:22, pId:2, name:"随意勾选 2-2", open:true},
                    { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
                    { id:222, pId:22, name:"随意勾选 2-2-2"},
                    { id:23, pId:2, name:"随意勾选 2-3"}
                ];

                $.fn.zTree.init($("#treedemo1"), setting, zNodes1);
                $.fn.zTree.init($("#treedemo2"), setting, zNodes2);
                $.fn.zTree.init($("#treedemo3"), setting, zNodes3);
                $.fn.zTree.init($("#treedemo4"), setting, zNodes4);
                $.fn.zTree.init($("#treedemo5"), setting, zNodes5);
                $.fn.zTree.init($("#treedemo6"), setting, zNodes6);
                $.fn.zTree.init($("#treedemo7"), setting, zNodes7);

                //自定义功能块，EndRegion



            })
    });

