package com.jcj.sparrow.JasperReport;

import com.jcj.sparrow.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

/**
 *用户
 */
@Controller
public class UserinfoReport
{
    @Autowired
    private UserinfoService userinfoService;

    @RequestMapping("/userinforeport")
    private ModelAndView report()
    {
        JasperReportsPdfView view=new JasperReportsPdfView ();
        view.setUrl("classpath:/jaspertemplate/UserinfoReport.jrxml");

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", carService.findAll());

        return new ModelAndView(view, params);
    }
}
