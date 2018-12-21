package com.photo.warehouse.vo;

import com.photo.warehouse.biz.photo.PicAttribBiz;
import com.photo.warehouse.model.photo.PicAttrib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by CDZ on 2018/12/12.
 */
@Component
@PropertySource({"classpath:connection.properties"})
public class Upload {
    //获取存储的物理路径
    @Value("${hddUpath}")
    private  String hddUpath;

    @Value("${hddTpath}")
    private  String hddTpath;

    @Autowired
    private PicAttribBiz picAttribBiz;

    public  String uploadFile(MultipartFile multipartFile,PicAttrib picAttrib,int main) throws IOException {

        Random rnd = new Random();
        String pid = String.valueOf(System.currentTimeMillis() + rnd.nextInt(1000));
        //获取文件后缀名
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //映射物理路径

        File directory = new File("");// 参数为空
        //工程的根目录
        String courseFile = directory.getCanonicalPath();

        String file = courseFile + "\\" + hddUpath + "\\" + pid + suffix;
        String rfile =courseFile + "\\" + hddTpath + "\\" + pid + ".jpg";
        byte[] bytes = multipartFile.getBytes();
        File updirFile = new File(hddUpath);
        File tpdirFile = new File(hddTpath);
        if(!updirFile.exists()){
            updirFile.mkdirs();
        }if(!tpdirFile.exists()){
            tpdirFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(file);
        out.write(bytes);
        out.flush();
        out.close();
        FileOutputStream out1 = new FileOutputStream(rfile);
        out1.write(bytes);
        out1.flush();
        out1.close();
        picAttrib.setVcPid(pid);
        picAttrib.setVcFormat(suffix.replace(".",""));
        picAttrib.setVcUpload(pid + suffix);
        picAttrib.setVcThumb(pid + ".jpg");
        picAttrib.setcType("0");
        picAttrib.setcStatus("0");
        picAttrib.setcMain(String.valueOf(main));
        picAttrib.setVcBytes(String.valueOf(multipartFile.getSize()));
        picAttrib.setDtPostime(new Date());
        picAttrib.setDtStamp(new Date());
        picAttribBiz.insertSelective(picAttrib);
        return pid;
    }

    public Boolean checkPicId(String picId){
        PicAttrib pa = picAttribBiz.selectByVcPid(picId);
        if(pa != null)
            return true;
        else
            return false;
    }
}
