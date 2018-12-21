package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.backstage.BaseLogBiz;
import com.photo.warehouse.biz.photo.DownloadFormBiz;
import com.photo.warehouse.biz.photo.PicAttribBiz;
import com.photo.warehouse.model.backstage.BaseLog;
import com.photo.warehouse.model.photo.DownloadForm;
import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.util.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 下载申请
 * Created by CDZ on 2018/12/8.
 */
@Component
@PropertySource({"classpath:connection.properties"})
@RestController
@RequestMapping("/api/photo/downloadForm")
public class DownloadFormController  {

    @Autowired
    PicAttribBiz picAttribBiz;
    @Autowired
    DownloadFormBiz baseBiz;

    @Value("${hddUpath}")
    private  String hddUpath;

    @Autowired
    BaseLogBiz baseLogBiz;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> get(@PathVariable int id){
        ObjectRestResponse<DownloadForm> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.selectById(id);
        entityObjectRestResponse.data((DownloadForm)o);
        return entityObjectRestResponse;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> update(@RequestBody DownloadForm entity){
        baseBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<DownloadForm>();
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> remove(@PathVariable int id){
        baseBiz.deleteById(id);
        return new ObjectRestResponse<DownloadForm>();
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<DownloadForm> list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> delete(@RequestBody List<DownloadForm> downloadFormList){
        for(DownloadForm downloadForm : downloadFormList){
            baseBiz.deleteDownloadForm(downloadForm);
        }
        return new ObjectRestResponse<DownloadForm>();
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> add(@RequestBody DownloadForm downloadForm){
        downloadForm.setDtPosted(new Date());
        PicAttrib picAttrib = picAttribBiz.selectByVcPid(downloadForm.getVcPid());
        downloadForm.setVcGroupid(picAttrib.getVcGroupid());
        baseBiz.insertSelective(downloadForm);
        return new ObjectRestResponse<DownloadForm>();
    }


    /**
     * 批量下载()
     * @param
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadPic",method = RequestMethod.GET)
    public void download(@Param("pidList") String pidList, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        List<PicAttrib> picAttribList = new ArrayList<>();
        String[] strs=pidList.split(",");
        for(String gid : strs){
            List<PicAttrib> picAttribByGid =  picAttribBiz.getPicAttribByGid(gid);
            for(PicAttrib picAttrib : picAttribByGid){
                picAttribList.add(picAttrib);
            }
        }

        File directory = new File("");// 参数为空
        //工程的根目录
        String courseFile = directory.getCanonicalPath();
        InputStream inputStream = null;
        ZipOutputStream zos = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String downloadFilename = df.format(new Date()) + "_图片打包.zip";//文件的名称
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            zos = new ZipOutputStream(response.getOutputStream());
            for(PicAttrib picAttrib : picAttribList){
                String fileName = picAttrib.getVcName() + "_" + picAttrib.getVcAuthor() + "_" + picAttrib.getVcPhotime().substring(0,picAttrib.getVcPhotime().indexOf(" "));
                encodeFileName(fileName,request);
                File file = new File(courseFile + "\\" + hddUpath  + "\\" + picAttrib.getVcUpload());
                zos.putNextEntry(new ZipEntry(fileName + "_" + picAttrib.getVcUpload()));
                inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024];
                int r = 0;
                while ((r = inputStream.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
                inputStream.close();
            }
            zos.flush();
            zos.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            inputStream.close();
            zos.flush();
            zos.close();
        }
    }

    /**
     * 图片下载
     * @param gId
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadPic/{vcGroupid}",method = RequestMethod.GET)
    public void downloadPicList(@PathVariable("vcGroupid") String gId, HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        List<PicAttrib> picAttribList = picAttribBiz.getPicAttribByGid(gId);
        File directory = new File("");// 参数为空
        //工程的根目录
        String courseFile = directory.getCanonicalPath();
        if(picAttribList.size() == 1){
            for(PicAttrib picAttrib : picAttribList){
                String filePath = courseFile + "\\" + hddUpath  + "\\" + picAttrib.getVcUpload();
                //获取后缀
                String suffix = filePath.substring(filePath.lastIndexOf("."));
                //拼接文件名  标题 + 作者 + 拍摄时间
                String fileName = picAttrib.getVcName() + "_" + picAttrib.getVcAuthor() + "_" + picAttrib.getVcPhotime().substring(0,picAttrib.getVcPhotime().indexOf(" "));
                response.setContentType("text/html;charset=UTF-8");
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    File file = new File(filePath);
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    outputStream = response.getOutputStream();
                    response.setContentType("application/x-download");
                    encodeFileName(fileName,request);
                    response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1")  + "_" + picAttrib.getVcUpload());
                    IOUtils.copy(inputStream, outputStream);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    outputStream.flush();
                    inputStream.close();
                    outputStream.close();
                }
            }

        }if(picAttribList.size() > 1){
            InputStream inputStream = null;
            ZipOutputStream zos = null;
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                String downloadFilename = df.format(new Date()) + "_图片打包.zip";//文件的名称
                downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
                response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
                response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
                zos = new ZipOutputStream(response.getOutputStream());

                for(PicAttrib picAttrib : picAttribList){
                    String fileName = picAttrib.getVcName() + "_" + picAttrib.getVcAuthor() + "_" + picAttrib.getVcPhotime().substring(0,picAttrib.getVcPhotime().indexOf(" "));
                    encodeFileName(fileName,request);
                    File file = new File(courseFile + "\\" + hddUpath  + "\\" + picAttrib.getVcUpload());
                    zos.putNextEntry(new ZipEntry(fileName + "_" + picAttrib.getVcUpload()));
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    byte[] buffer = new byte[1024];
                    int r = 0;
                    while ((r = inputStream.read(buffer)) != -1) {
                        zos.write(buffer, 0, r);
                    }
                    inputStream.close();
                }
                zos.flush();
                zos.close();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                inputStream.close();
                zos.flush();
                zos.close();
            }
        }else{
            return;
        }

    }

    public String encodeFileName( String fileName,HttpServletRequest request) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie")) {// IE
            try{
                fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("%20", "\\+").replaceAll("%28", "\\(")
                        .replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
                        .replaceAll("%26", "\\&").replaceAll("%2C", "\\,").replaceAll("%24", "\\$")
                        .replaceAll("%25", "\\%").replaceAll("%5E", "\\^").replaceAll("%3D", "\\=")
                        .replaceAll("%2B", "\\+");
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (userAgent.contains("like gecko")) {// 谷歌
            try {
                fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("%20", "\\+").replaceAll("%28", "\\(")
                        .replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
                        .replaceAll("%26", "\\&").replaceAll("%2C", "\\,").replaceAll("%24", "\\$")
                        .replaceAll("%25", "\\%").replaceAll("%5E", "\\^").replaceAll("%3D", "\\=")
                        .replaceAll("%2B", "\\+").replaceAll("%5B", "\\[").replaceAll("%5D", "\\]")
                        .replaceAll("%7B", "\\{").replaceAll("%7D", "\\}");
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (userAgent.contains("firefox")) {// 火狐
            try{
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1").replaceAll("%20", "\\+")
                        .replaceAll("%28", "\\(").replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@")
                        .replaceAll("%23", "\\#").replaceAll("%26", "\\&").replaceAll("%2C", "\\,")
                        .replaceAll("%24", "\\$");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return fileName;
    }

    @RequestMapping(value = "/downloadFormList/{status}",method = RequestMethod.PUT)
    @Transactional
    public void updateDownloadFormList(@PathVariable("status") String status, @RequestBody List<DownloadForm> downloadFormList){
        for(DownloadForm downloadForm : downloadFormList){
           baseBiz.updateDownloadFormStatus(status,downloadForm.getVcPid());
        }
    }
}
