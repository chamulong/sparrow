//RequireJS全局配置（包括css）
require.config({
    paths:{
        jquery:['/lib/Hplus/js/jquery.min'],
        bootstrap:['/lib/Hplus/js/bootstrap.min'],
        metismenu:['/lib/Hplus/js/plugins/metisMenu/jquery.metisMenu'],
        slimscroll:['/lib/Hplus/js/plugins/slimscroll/jquery.slimscroll.min'],
        hplus:'/lib/Hplus/js/hplus',
        contabs:'/lib/Hplus/js/contabs',
        pace:'/lib/Hplus/js/plugins/pace/pace.min',
        bootstraptable:['/lib/Hplus/js/plugins/bootstrap-table/bootstrap-table.min'],
        bootstraptableCN:['/lib/Hplus/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN'],
        layer:['/lib/Hplus/js/plugins/layer-v3.1.1/layer/layer'],
        jqueryform:['/lib/Hplus/js/jquery.form.min'],
        jqueryupload:'/lib/jQuery.upload.min',
        ztree:'/lib/zTree3/js/jquery.ztree.all.min'
    },
    shim:{
        bootstrap:{
            deps:['jquery','css!/lib/Hplus/css/bootstrap.min.css','css!/lib/Hplus/css/font-awesome.min.css','css!/lib/Hplus/css/animate.css','css!/lib/Hplus/css/style.css']
        },
        metismenu:{
            deps:['jquery']
        },
        slimscroll:{
            deps:['jquery']
        },
        hplus:{
            deps:['jquery','bootstrap']
        },
        contabs:{
            deps:['jquery','bootstrap','hplus']
        },
        pace:{
            deps:['jquery','bootstrap','hplus']
        },
        bootstraptable:{
            deps:['jquery','bootstrap','css!/lib/Hplus/css/plugins/bootstrap-table/bootstrap-table.min.css']
        },
        bootstraptableCN:{
            deps:['jquery','bootstrap','bootstraptable']
        },
        layer:{
            deps:['jquery']
        },
        jqueryform:{
            deps:'jquery'
        },
        jqueryupload:{
            deps:['jquery','css!/lib/upload.css']
        },
        ztree:{
            deps:['jquery','css!/lib/zTree3/css/zTreeStyle/zTreeStyle.css']
        }
    },
    map:{
        '*':{
            css:'/lib/css.min.js'}
    }
})