package com.jcj.sparrow.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @Author：江成军
 * @Description:接收上传的文件
 * @Date:Create in 2018/5/15 15:02
 */
@Component
public class UploadFile
{
    /**
     * 接收上传的文件，文件接收成功后，反馈回文件的保存路径,fileprefix:保存文件的前缀
     */
    public String receiveFile(MultipartFile file,String fileprefix)
    {
        if (file.isEmpty())
        {
            return "文件为空";
        }

        //获取文件的后缀名，不能是exe/dll/com/bat
        String fileName = file.getOriginalFilename(); // 获取文件名
        String suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();// 获取文件的后缀名,并转化为小写
        if(suffixName.equals("exe")||suffixName.equals("dll")||suffixName.equals("com")||suffixName.equals("bat"))
        {
            return "文件格式错误";
        }

        //获取当前的年月（作为文件夹名）
        SimpleDateFormat formater_YM=new SimpleDateFormat("yyyyMM");
        String strFolderPath=formater_YM.format(new Date());

        //判断文件夹是否存在，不存在则创建
        if(isOSLinux()){strFolderPath="/home/uploadfile/"+strFolderPath;}//linux环境
        else{strFolderPath="D:\\uploadfile\\"+strFolderPath;}//Windows环境
        File dir=new File(strFolderPath);
        if(!dir.exists()){dir.mkdirs();}

        //获取当前的时间（到毫秒，作为保存文件的名称）
        SimpleDateFormat formater_time=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strSaveFileName=formater_time.format(new Date());

        //保存文件
        try
        {
            File dest=new File(strFolderPath+strSaveFileName);
            file.transferTo(dest);
            return "文件上传成功";
        }
        catch (Exception e){e.printStackTrace();}

        return "文件上传失败";

    }

    /**
     * 判断当前的环境是否是linux，如是是linux则为true，不是（是windows）则为false
     */
    private boolean isOSLinux()
    {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1){return true;}
        else {return false;}
    }
}
