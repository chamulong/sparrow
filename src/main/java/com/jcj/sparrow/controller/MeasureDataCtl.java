package com.jcj.sparrow.controller;

import com.alibaba.fastjson.JSONObject;
import com.jcj.sparrow.domain.MeasureData;
import com.jcj.sparrow.service.MeasureDataService;
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
 * @Author: 江成军
 * @Date: 2019/02/03 16:02
 * @Description: 测量信息控制层，对应MongoDB
 */
@Controller
@RequestMapping("/measuredata")
public class MeasureDataCtl
{
    @Autowired
    private MeasureDataService measureDataService;

    //访问测量列表，跳转的页面,注意：返回的是页面的路径，不是方法
    @RequestMapping(value = "/listhtml")
    public String listredirect()
    {
        return "/measuredata/listMeasuredata";
    }

    @PostMapping("/basiclist")
    @ResponseBody
    public String basicPageList(@RequestBody Map<String,Object> reqMap)
    {
        /**
        *@Author 江成军
        *@Description 最基本的分页查询，不带动态查询条件
        *@Date 2019/02/03 16:25
        *@Param
        *@return
        **/

        //固定不变的两个分页参数
        int page=0;
        if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
        int size=3;
        if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}

        Sort sort=new Sort(Sort.Direction.ASC,"poiname");
        Page<MeasureData> pageinfo=measureDataService.basicPageList(PageRequest.of(page,size,sort));
        List<MeasureData> userInfos =pageinfo.getContent();
        JSONObject result = new JSONObject();
        result.put("rows", userInfos);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }

    @SystemAnnotationLog(actiondesc = "查询测量数据")
    @PostMapping("/list")
    @ResponseBody
    public String queryDynamic(@RequestBody Map<String,Object> reqMap)
    {
        /**
        *@Author 江成军
        *@Description 带动态查询条件的分页查询
        *@Date 2019/02/09 16:56
        *@Param
        *@return
        **/


        //固定不变的两个分页参数
        int page=0;
        if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
        int size=3;
        if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}


        Sort sort=new Sort(Sort.Direction.ASC,"poiname");
        Page<MeasureData> pageinfo=measureDataService.queryDynamic(reqMap,PageRequest.of(page,size,sort));
        List<MeasureData> pageinfos =pageinfo.getContent();
        JSONObject result = new JSONObject();
        result.put("rows", pageinfos);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }



}