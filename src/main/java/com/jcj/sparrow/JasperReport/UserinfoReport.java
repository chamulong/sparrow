package com.jcj.sparrow.JasperReport;

import com.jcj.sparrow.domain.DepartUser;
import com.jcj.sparrow.domain.Department;
import com.jcj.sparrow.domain.UserInfo;
import com.jcj.sparrow.service.DepartmentService;
import com.jcj.sparrow.service.UserinfoService;
import com.jcj.sparrow.utils.JasperExportUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 *用户
 */
@Controller
public class UserinfoReport
{
    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private DepartmentService departmentService;


    /**
     * 向页面输出html页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/userinforeport")
    public void reportHtml(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException
    {
        JRDataSource datasource=new JRBeanCollectionDataSource(userinfoService.findAll());
        Map<String, Object> map = new HashMap<>();
        JasperExportUtils.exportToHtml("/jaspertemplate/UserinfoReport.jasper", map, datasource, request, response);

    }

    /**
     * 向页面输出PDF报表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/userinforeport_PDF")
    public void reportPDF(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException
    {
        JRDataSource datasource=new JRBeanCollectionDataSource(userinfoService.findAll());
        Map<String, Object> map = new HashMap<>();
        JasperExportUtils.exportToPdf("/jaspertemplate/UserinfoReport.jasper","报表", map, datasource, response);

    }

     /** 向页面输出Excel报表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/userinforeport_xls")
    public void reportExcel(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException
    {
        JRDataSource datasource=new JRBeanCollectionDataSource(userinfoService.findAll());
        Map<String, Object> map = new HashMap<>();
        JasperExportUtils.exportToXls("/jaspertemplate/UserinfoReport.jasper","报表", map, datasource, response);

    }


    /**
     * 向页面输出html页面(基于JavaBean数据源方式)
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/userinforeport_javabean")
    public void reportHtml_javabean(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException
    {
        List<UserInfo> listUserInfo=userinfoService.findAll();
        List<Department> listDepartment=departmentService.findAll();
        List<DepartUser> listDU=new ArrayList<DepartUser>();
        for (UserInfo userinfo:listUserInfo)
        {
            DepartUser departUser=new DepartUser();
            departUser.setUsername(userinfo.getUsername());
            departUser.setRealname(userinfo.getRealname());
            departUser.setEmail(userinfo.getEmail());
            departUser.setHomeaddress(userinfo.getHomeaddress());
            departUser.setDepname(userinfo.getDepname());
            for (Department department:listDepartment)
            {
                if(department.getDepname().equals(userinfo.getDepname()))
                {
                    departUser.setDepbrief(department.getDepbrief());
                    break;
                }
            }

            listDU.add(departUser);
        }


        JRDataSource datasource=new JRBeanCollectionDataSource(listDU);
        Map<String, Object> map = new HashMap<>();
        JasperExportUtils.exportToHtml("/jaspertemplate/depart_user.jasper", map, datasource, request, response);

    }


}
