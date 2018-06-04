//RequireJS全局配置（包括css）
require.config({
    paths:{
        jquery:['http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min','/Hplus/js/jquery.min'],
        bootstrap:['https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap','/Hplus/js/bootstrap.min'],
        metismenu:['https://cdn.bootcss.com/metisMenu/2.7.7/metisMenu.min','/Hplus/js/plugins/metisMenu/jquery.metisMenu'],
        slimscroll:['https://cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min','/Hplus/js/plugins/slimscroll/jquery.slimscroll.min'],
        hplus:'/Hplus/js/hplus',
        contabs:'/Hplus/js/contabs',
        pace:'/Hplus/js/plugins/pace/pace.min',
        bootstraptable:['https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min','/Hplus/js/plugins/bootstrap-table/bootstrap-table.min'],
        bootstraptableCN:['https://cdn.bootcss.com/bootstrap-table/1.12.1/locale/bootstrap-table-zh-CN.min','/Hplus/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN'],
        layer:['https://cdn.bootcss.com/layer/3.1.0/layer','/Hplus/js/plugins/layer-v3.1.1/layer/layer'],
        jqueryform:['https://cdn.bootcss.com/jquery.form/4.2.2/jquery.form.min','/Hplus/js/jquery.form.min'],
        jqueryupload:'/custom/jQuery.upload.min',
        ztree:'/zTree3/js/jquery.ztree.all.min'
    },
    shim:{
        bootstrap:{
            deps:['jquery','css!/Hplus/css/bootstrap.min.css','css!/Hplus/css/font-awesome.min.css','css!/Hplus/css/animate.css','css!/Hplus/css/style.css']
        },
        metismenu:{
            deps:'jquery'
        },
        slimscroll:{
            deps:'jquery'
        },
        hplus:{
            deps:'jquery'
        },
        contabs:{
            deps:'jquery'
        },
        pace:{
            deps:'jquery'
        },
        bootstraptable:{
            deps:['bootstrap','css!/Hplus/css/plugins/bootstrap-table/bootstrap-table.min.css']
        },
        bootstraptableCN:{
            deps:'bootstraptable'
        },
        layer:{
            deps:['jquery']
        },
        jqueryform:{
            deps:'jquery'
        },
        jqueryupload:{
            deps:'jquery'
        },
        ztree:{
            deps:['jquery','/zTree3/css/zTreeStyle/zTreeStyle.css']
        }
    },
    map:{
        '*':{
            css:'/custom/css.min.js'}
    }
})