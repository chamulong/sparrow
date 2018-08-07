package com.jcj.sparrow.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JasperReport 报表工具辅助类
 */
public class JasperExportUtils {

    private static final String ENCODING = "utf-8";
    private static final String PDF_CONTENT_TYPE = "application/octet-stream";
    private static final String XLS_CONTENT_TYPE = "application/x-msdownload";

    /**
     * 导出html页面
     * @param jasperPath 模版路径
     * @param datasource 数据源
     * @param request
     * @param response
     * @throws JRException
     * @throws IOException
     */
    public static void exportToHtml(String jasperPath, JRDataSource datasource, HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
        exportToHtml(jasperPath, new HashMap<String, Object>(), datasource, request, response);
    }

    /**
     * 导出html页面
     * @param jasperPath 模版路径
     * @param params 参数
     * @param datasource 数据源
     * @param request
     * @param response
     * @throws JRException
     * @throws IOException
     */
    public static void exportToHtml(String jasperPath, Map<String, Object> params, JRDataSource datasource, HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
        //指定模板文件
        File reportFile = new File(new ClassPathResource(jasperPath).getURI());
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, datasource);
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        exporter.exportReport();
    }

    /**
     * 导出pdf
     * @param jasperPath 模版路径
     * @param fileName 文件名
     * @param params
     * @param datasource
     * @param response
     * @throws IOException
     * @throws JRException
     */
    public static void exportToPdf(String jasperPath, String fileName, Map<String, Object> params, JRDataSource datasource, HttpServletResponse response) throws IOException, JRException {
        //指定模板文件
        JasperPrint jasperPrint = fillReport(jasperPath, params, datasource);
        response.setCharacterEncoding(ENCODING);
        response.setContentType(PDF_CONTENT_TYPE);
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".pdf");
        exportReport(new JRPdfExporter(), jasperPrint, response);
    }

    /**
     * 导出excel
     * @param jasperPath 模版路径
     * @param fileName 文件名
     * @param params
     * @param datasource
     * @param response
     * @throws IOException
     * @throws JRException
     */
    public static void exportToXls(String jasperPath, String fileName, Map<String, Object> params, JRDataSource datasource, HttpServletResponse response) throws IOException, JRException {
        //指定模板文件
        JasperPrint jasperPrint = fillReport(jasperPath, params, datasource);
        response.setCharacterEncoding(ENCODING);
        response.setContentType(XLS_CONTENT_TYPE);
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
        exportReport(new JRXlsExporter(), jasperPrint, response);
    }

    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, HttpServletResponse response) throws JRException, IOException {
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }

    private static JasperPrint fillReport(String jasperPath, Map<String, Object> params, JRDataSource datasource) throws JRException, IOException {
        File reportFile = new File(new ClassPathResource(jasperPath).getURI());
        return JasperFillManager.fillReport(reportFile.getPath(), params, datasource);
    }
}
