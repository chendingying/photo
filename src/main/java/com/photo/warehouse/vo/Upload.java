package com.photo.warehouse.vo;

import com.photo.warehouse.biz.photo.PicAttribBiz;
import com.photo.warehouse.model.photo.PicAttrib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 上传文件
     * @param multipartFile
     * @param picAttrib
     * @param main
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile multipartFile,PicAttrib picAttrib,int main) throws IOException {

        //以时间戳作为pid
        Random rnd = new Random();
        String pid = String.valueOf(System.currentTimeMillis() + rnd.nextInt(1000));
        picAttrib.setVcPid(pid);

        //获取文件后缀名
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

        // 参数为空
        File directory = new File("");
        //工程的根目录
        String courseFile = directory.getCanonicalPath();

        //原图url
        String filerl = courseFile + "\\" + hddUpath + "\\" + pid + suffix;
        //缩略图url
        String rfileUrl =courseFile + "\\" + hddTpath + "\\" + pid + ".jpg";

        //将文件转化为二进制
        byte[] bytes = multipartFile.getBytes();

        //判断是否有文件夹，若没有则自动创建
        File updirFile = new File(hddUpath);
        File tpdirFile = new File(hddTpath);
        if(!updirFile.exists()){
            updirFile.mkdirs();
        }if(!tpdirFile.exists()){
            tpdirFile.mkdirs();
        }

        //写入原图文件和缩略图文件
        FileOutputStream out = new FileOutputStream(filerl);
        out.write(bytes);
        out.flush();
        out.close();
        FileOutputStream out1 = new FileOutputStream(rfileUrl);
        out1.write(bytes);
        out1.flush();
        out1.close();

        //填充数据
        //原图后缀
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
}
