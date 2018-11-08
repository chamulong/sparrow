package com.jcj.sparrow.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 通过字节数组的方式读取本地图片
 */
@Controller
public class showImg
{
    @RequestMapping("/showimg")
    @ResponseBody
    public String createFolw(HttpServletResponse response, @RequestParam String imgpath)
    {
        FileInputStream fis = null;
        OutputStream os = null;
        try
        {
            fis = new FileInputStream(imgpath);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1)
            {
                os.write(buffer, 0, count);
                os.flush();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //关闭文件
        try
        {
            fis.close();
            os.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return "ok";
    }
}
