package com.jcj.sparrow.controller;

import com.alibaba.fastjson.JSONObject;
import com.jcj.sparrow.domain.Systemlog;
import com.jcj.sparrow.service.SystemlogService;
import com.jcj.sparrow.systemaop.SystemAnnotationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author:江成军
 * @Description:控制类，系统日志
 * @Date:Created on 2018/09/12 10:41
 */
@Controller
@RequestMapping("/systemlog")
public class SystemlogController
{
    @Autowired
    private SystemlogService systemlogService;

    //指定系统日志列表页面
    @RequestMapping(value = "/listhtml")
    public String listemployee()
    {
        return "/systembasic/listSystemLog";//对应的是页面文件("systembasic"文件夹下的listSystemLog.html文件)
    }

    //实现动态查询、分页
    @PostMapping("/list")
    @SystemAnnotationLog(actiondesc = "查询系统日志信息")
    @ResponseBody
    public String queryDynamic(@RequestBody Map<String,Object> reqMap)
    {
        //固定不变的两个分页参数
        int page=0;
        if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
        int size=3;
        if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}


        Sort sort=new Sort(Sort.Direction.DESC,"operatetime");
        Page<Systemlog> pageinfo=systemlogService.queryDynamic(reqMap, PageRequest.of(page,size,sort));
        List<Systemlog> systemlogs =pageinfo.getContent();
        JSONObject result = new JSONObject();
        result.put("rows", systemlogs);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }
}
