package com.jcj.sparrow.JasperReport;

import com.jcj.sparrow.service.UserinfoService;
import com.jcj.sparrow.utils.JasperExportUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *用户
 */
@Controller
public class UserinfoReport
{
    @Autowired
    private UserinfoService userinfoService;

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
        JasperExportUtils.exportToHtml("/jaspertemplate/sUserinfoReport.jasper", map, datasource, request, response);

    }
}
