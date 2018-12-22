package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.backstage.BaseLogBiz;
import com.photo.warehouse.biz.photo.DownloadFormBiz;
import com.photo.warehouse.biz.photo.PicAttribBiz;
import com.photo.warehouse.log.ReturnCode;
import com.photo.warehouse.log.SystemControllerLog;
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

    /**
     * 查询单个申请下载明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> get(@PathVariable int id){
        ObjectRestResponse<DownloadForm> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.selectById(id);
        entityObjectRestResponse.data((DownloadForm)o);
        return entityObjectRestResponse;
    }

    /**
     * 修改下载
     * @param entity
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> update(@RequestBody DownloadForm entity){
        baseBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<DownloadForm>();
    }

    /**
     * 删除下载
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> remove(@PathVariable int id){
        baseBiz.deleteById(id);
        return new ObjectRestResponse<DownloadForm>();
    }


    /**
     * 删除下载申请
     * @param downloadFormList
     * @return
     */
    @Transactional
    @SystemControllerLog(descrption = "下载管理")
    @RequestMapping(value = "/delete",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> delete(@RequestBody List<DownloadForm> downloadFormList){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("删除下载申请");
        try{
            List<String> stringId = new ArrayList<>();
            for(DownloadForm downloadForm : downloadFormList){
                stringId.add(downloadForm.getId());
                baseBiz.deleteDownloadForm(downloadForm);
            }
            objectRestResponse.setStringList(stringId);
            objectRestResponse.setMessage("删除成功");
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
        }catch (Exception e){
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setMessage("删除失败");
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
        }

        return objectRestResponse;
    }

    /**
     * 新增下载申请
     * @param downloadForm
     * @return
     */
    @Transactional
    @SystemControllerLog(descrption = "下载管理")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<DownloadForm> add(@RequestBody DownloadForm downloadForm){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("下载申请");
        try{
            //以时间戳作为pid
            Random rnd = new Random();
            String pid = String.valueOf(System.currentTimeMillis() + rnd.nextInt(1000));
            downloadForm.setId(pid);
            List<String> stringId = new ArrayList<>();
            stringId.add(pid);
            downloadForm.setDtPosted(new Date());
            PicAttrib picAttrib = picAttribBiz.selectByVcPid(downloadForm.getVcPid());
            downloadForm.setVcGroupid(picAttrib.getVcGroupid());
            baseBiz.insertSelective(downloadForm);
            objectRestResponse.setStringList(stringId);
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            objectRestResponse.setMessage("申请成功");
        }catch (Exception e){
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setMessage("申请失败");
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
        }

        return objectRestResponse;
    }


    /**
     * 分页查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<DownloadForm> list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }

    /**
     * 批量下载()
     * @param
     * @param request
     * @param response
     * @throws IOException
     */
    @SystemControllerLog(descrption = "下载管理")
    @Transactional
    @RequestMapping(value = "/downloadPic",method = RequestMethod.GET)
    public ObjectRestResponse download(@Param("pidList") String pidList, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("图片下载");
        List<String> stringId = new ArrayList<>();

        List<PicAttrib> picAttribList = new ArrayList<>();
        String[] strs=pidList.split(",");
        for(String gid : strs){
            List<PicAttrib> picAttribByGid =  picAttribBiz.getPicAttribByGid(gid);
            for(PicAttrib picAttrib : picAttribByGid){
                picAttribList.add(picAttrib);
                stringId.add(picAttrib.getVcPid());
            }
        }
        objectRestResponse.setStringList(stringId);
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
            }
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            objectRestResponse.setMessage("下载成功");
        } catch (Exception e) {
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setMessage("下载失败");
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            inputStream.close();
            zos.flush();
            zos.close();
        }
        return objectRestResponse;
    }

    /**
     * 图片下载
     * @param gId
     * @param request
     * @param response
     * @throws IOException
     */
    @Transactional
    @SystemControllerLog(descrption = "下载管理")
    @RequestMapping(value = "/downloadPic/{vcGroupid}",method = RequestMethod.GET)
    public ObjectRestResponse downloadPicList(@PathVariable("vcGroupid") String gId, HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("图片下载");
        List<String> stringId = new ArrayList<>();

        List<PicAttrib> picAttribList = picAttribBiz.getPicAttribByGid(gId);
        for(PicAttrib picId : picAttribList){
            stringId.add(picId.getVcPid());
        }
        objectRestResponse.setStringList(stringId);

        File directory = new File("");// 参数为空
        //工程的根目录
        String courseFile = directory.getCanonicalPath();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        ZipOutputStream zos = null;
        try{
            if(picAttribList.size() == 1){
                for(PicAttrib picAttrib : picAttribList){
                    String filePath = courseFile + "\\" + hddUpath  + "\\" + picAttrib.getVcUpload();
                    //获取后缀
                    String suffix = filePath.substring(filePath.lastIndexOf("."));
                    //拼接文件名  标题 + 作者 + 拍摄时间
                    String fileName = picAttrib.getVcName() + "_" + picAttrib.getVcAuthor() + "_" + picAttrib.getVcPhotime().substring(0,picAttrib.getVcPhotime().indexOf(" "));
                    response.setContentType("text/html;charset=UTF-8");

                    File file = new File(filePath);
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    outputStream = response.getOutputStream();
                    response.setContentType("application/x-download");
                    encodeFileName(fileName,request);
                    response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1")  + "_" + picAttrib.getVcUpload());
                    IOUtils.copy(inputStream, outputStream);
                    }
            }if(picAttribList.size() > 1) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                String downloadFilename = df.format(new Date()) + "_图片打包.zip";//文件的名称
                downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
                response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
                response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
                zos = new ZipOutputStream(response.getOutputStream());

                for (PicAttrib picAttrib : picAttribList) {
                    String fileName = picAttrib.getVcName() + "_" + picAttrib.getVcAuthor() + "_" + picAttrib.getVcPhotime().substring(0, picAttrib.getVcPhotime().indexOf(" "));
                    encodeFileName(fileName, request);
                    File file = new File(courseFile + "\\" + hddUpath + "\\" + picAttrib.getVcUpload());
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
            }
            objectRestResponse.setMessage("下载成功");
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
        }catch (Exception e){
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setMessage("下载失败");
        }finally {
        inputStream.close();
        outputStream.close();
    }

        return objectRestResponse;

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

    /**
     * 下载管理审批
     * @param status
     * @param downloadFormList
     */
    @SystemControllerLog(descrption = "下载审批")
    @RequestMapping(value = "/downloadFormList/{status}",method = RequestMethod.PUT)
    @Transactional
    public ObjectRestResponse updateDownloadFormList(@PathVariable("status") String status, @RequestBody List<DownloadForm> downloadFormList){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if(status.equals("2")){
            objectRestResponse.setActionType("拒绝下载");
        }else if(status.equals("1")){
            objectRestResponse.setActionType("同意下载");
        }
        try{
            List<String> stringId = new ArrayList<>();
            for(DownloadForm downloadForm : downloadFormList){
                stringId.add(downloadForm.getId());
                baseBiz.updateDownloadFormStatus(status,downloadForm.getVcPid());
            }
            objectRestResponse.setStringList(stringId);
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            objectRestResponse.setMessage("审批成功");
        }catch (Exception e){
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setMessage("审批失败");
        }
        return objectRestResponse;
    }
}
