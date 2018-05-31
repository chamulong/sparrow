package com.jcj.sparrow.controller;

import com.alibaba.fastjson.JSONObject;
import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.security.SysUser;
import com.jcj.sparrow.service.UserinfoService;
import com.jcj.sparrow.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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
    public String saveEmployee(UserInfo userInfo)
    {
        userinfoService.save(userInfo);
        return "OK";
    }

    /*
      批量删除信息
     */
    @GetMapping("/delete")
    @ResponseBody
    public String deleteEmployee(@RequestParam String uuids)
    {
        String[] arrUUID=uuids.split("_");
        for (String uuid:arrUUID)
        {
            System.out.println("uuid："+uuid);
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

    /*
        实现动态查询、分页
     */
    @PostMapping("/list")
    @ResponseBody
    public String queryDynamic(@RequestBody Map<String,Object> reqMap,HttpSession session)
    {
        //测试一下session
        UserInfo userInfo=(UserInfo)session.getAttribute("userinfo");
        System.out.println("登录用户的所属部门："+userInfo.getDepname());


        //固定不变的两个分页参数
        int page=0;
        if(reqMap.get("page").toString()!=null){page= Integer.parseInt(reqMap.get("page").toString());}
        int size=3;
        if(reqMap.get("size").toString()!=null){size= Integer.parseInt(reqMap.get("size").toString());}


        Sort sort=new Sort(Sort.Direction.DESC,"birthdate");
        Page<UserInfo> pageinfo=userinfoService.queryDynamic(reqMap,new PageRequest(page,size,sort));
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



    @PostMapping("/comquery")
    @ResponseBody
    public List<UserInfo> myQuery(@RequestParam(defaultValue = "张三") String username)
    {
        return userinfoService.findByJPQL(username);
    }

    @GetMapping("/PageUserInfosSearch")
    @ResponseBody
    public Page<UserInfo> getData(@RequestParam(defaultValue = "0") int page, String username, @RequestParam(defaultValue = "4") int size)
    {
        Sort sort=new Sort(Sort.Direction.DESC,"birthdate");
        return userinfoService.queryData(username,new PageRequest(page,size,sort));
    }

    @GetMapping("/d1")
    public String showDIndex(Model model)
    {
        List<UserInfo> list=userinfoService.findAll();
        model.addAttribute("employee",list);
        return "userinfo/index";
    }

    @GetMapping("/index")
    public String showIndex()
    {
        return "index";
    }
}
