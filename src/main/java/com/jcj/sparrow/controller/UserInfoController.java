package com.jcj.sparrow.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.service.UserinfoService;
import com.jcj.sparrow.systemaop.SystemAnnotationLog;
import com.jcj.sparrow.utils.FilterPureEntity;
import com.jcj.sparrow.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @Author:江成军
 * @Description:控制类，员工
 * @Date:Created on 2018/4/21 10:45
 */
@Controller
@RequestMapping("/userinfo")
public class UserInfoController
{
    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private UploadFile uploadFile;

    /*
      保存信息
     */
    @RequestMapping("/save")
    @ResponseBody
    public String saveUserinfo(UserInfo userInfo)
    {
        if(userInfo.getUpload()==null||userInfo.getUpload().equals(""))
        {
            userInfo.setUpload("无");
        }
        userinfoService.save(userInfo);
        return "OK";
    }

    /*
      修改信息
     */
    @RequestMapping("/modify")
    @ResponseBody
    public String modifyUserinfo(UserInfo userInfo)
    {
        if(userInfo.getUpload()==null||userInfo.getUpload().equals(""))
        {
            userInfo.setUpload("无");
        }
        userinfoService.modify(userInfo);
        return "OK";
    }

    //批量删除信息(逻辑删除)
    @PostMapping("/deleteLogic")
    @ResponseBody
    public String deleteLogic(@RequestParam String uuids)
    {
        System.out.println("uuids:"+uuids);
        String[] arrUUID=uuids.split("_");
        for (String uuid:arrUUID)
        {
            userinfoService.deleteByUuid(uuid);
        }
        return "OK";
    }

    /*
       接收上传的文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file)
    {
        return uploadFile.receiveFile(file,"img");
    }

    //访问用户列表，跳转的页面
    @RequestMapping(value = "/listhtml")
    public String listemployee()
    {
        return "/userinfo/listUserInfo";
    }


    /*
        实现动态查询、分页
     */
    @PostMapping("/list")
    @ResponseBody
    public String queryDynamic(@RequestBody Map<String,Object> reqMap)
    {
        //固定不变的两个分页参数
        int page=0;
        if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
        int size=3;
        if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}


        Sort sort=new Sort(Sort.Direction.DESC,"birthdate");
        Page<UserInfo> pageinfo=userinfoService.queryDynamic(reqMap,PageRequest.of(page,size,sort));
        List<UserInfo> userInfos =pageinfo.getContent();
        JSONObject result = new JSONObject();
        result.put("rows", userInfos);
        result.put("total",pageinfo.getTotalElements());
        return result.toJSONString();
    }

    @GetMapping("/userinfos")
    @ResponseBody
    public List<UserInfo> getAll()
    {
      return userinfoService.findAll();
    }

    //用户名唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateUsername")
    @ResponseBody
    public String validateUsername(@RequestParam String username)
    {
        boolean blStatus=userinfoService.validateUsername(username);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //手机号唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateMobile")
    @ResponseBody
    public String validateMobile(@RequestParam String mobile)
    {
        boolean blStatus=userinfoService.validateMobile(mobile);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //邮箱号唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateEmail")
    @ResponseBody
    public String validateEmail(@RequestParam String email)
    {
        boolean blStatus=userinfoService.validateEmail(email);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //用于修改,手机号唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateMobileModify")
    @ResponseBody
    public String validateMobileModify(@RequestParam String mobile,String uuid)
    {
        boolean blStatus=userinfoService.validateMobileModify(mobile,uuid);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //用于修改,邮箱号唯一性验证(如果已经存在，返回false，否则返回true；返回json数据，格式为{"valid",true})
    @PostMapping("/validateEmailModify")
    @ResponseBody
    public String validateEmailModify(@RequestParam String email,String uuid)
    {
        boolean blStatus=userinfoService.validateEmailModify(email,uuid);
        JSONObject result = new JSONObject();
        result.put("valid", blStatus);
        return result.toJSONString();
    }

    //根据UUID删除，物理删除用户信息
    @PostMapping("/deletePhysics")
    @ResponseBody
    public String deletePhysics(String uuid)
    {
        System.out.println("uuid:"+uuid);
        userinfoService.delete(uuid);
        return "OK";
    }

    //根据UUID删除，查询用户信息
    @PostMapping("/findByUuid")
    @ResponseBody
    public String findByUuid(String uuid)
    {
        UserInfo userInfo=userinfoService.findByUuid(uuid);
        Map map = FilterPureEntity.getKeyAndValue(userInfo);
        return JSON.toJSONString(map);
    }

}
