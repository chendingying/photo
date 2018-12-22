package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.backstage.OptionsLibBiz;
import com.photo.warehouse.biz.photo.*;
import com.photo.warehouse.controller.base.PicAttribBaseController;
import com.photo.warehouse.log.ReturnCode;
import com.photo.warehouse.log.SystemControllerLog;
import com.photo.warehouse.model.backstage.OptionsLib;
import com.photo.warehouse.model.photo.*;
import com.photo.warehouse.util.*;
import com.photo.warehouse.vo.Upload;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * 图片管理（Controller）
 * Created by CDZ on 2018/12/8.
 */
@Component
@PropertySource({"classpath:connection.properties"})
@RestController
@RequestMapping("/api/photo/picAttrib")
public class PicAttribController extends PicAttribBaseController<PicAttribBiz,PicAttrib> {

    @Autowired
    ClickResultsetBiz clickResultsetBiz;

    @Autowired
    PicCommentBiz picCommentBiz;

    @Autowired
    PicInfoBiz picInfoBiz;

    @Autowired
    Upload upload;

    @Autowired
    DownloadFormBiz downloadFormBiz;

    @Autowired
    OptionsLibBiz optionsLibBiz;

    //原图路径
    @Value("${hddUpath}")
    private  String hddUpath;

    //缩略图路径
    @Value("${hddTpath}")
    private String hddTpath;


    /**
     * 重新申请
     * @param entity
     * @return
     */
    @Transactional
    @SystemControllerLog(descrption = "图片管理")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<PicAttrib> update(@RequestBody PicAttrib entity){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("重新申请");
        try{
            List<String> stringId = new ArrayList<>();
            stringId.add(entity.getVcPid());
            baseBiz.updateSelectiveById(entity);
            objectRestResponse.setStringList(stringId);
            objectRestResponse.setMessage("重新申请成功");
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
        }catch (Exception e){
            objectRestResponse.setMessage("重新申请失败");
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
        }

        return objectRestResponse;
    }

    /**
     * 图片明细(点击率暂时注释掉)
     * @param params
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ObjectRestResponse<PicAttrib> get(@RequestParam Map<String, Object> params){

        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();

        //判断id是否为空
        if(params.get("vcPid") != null && !params.get("vcPid").equals("")){
            PicAttrib picAttrib = baseBiz.selectByVcPid(params.get("vcPid").toString());
            //初始化原图
            picAttrib.setVcThumb("");
            //初始化缩略图
            picAttrib.setVcUpload("");

            //查询组下所有的图片
            List<PicAttrib> groupPicAttribList = baseBiz.getPicAttribByGid(picAttrib.getVcGroupid());
            picAttrib.setPidList(groupPicAttribList);

            if(picAttrib == null){
                entityObjectRestResponse.setMessage("查无数据");
                return entityObjectRestResponse;
            }
            entityObjectRestResponse.data((PicAttrib)picAttrib);
//           暂时注释，待确认（点击次数）
//            ClickResultset clickResultset = new ClickResultset();
//            clickResultset.setVcPid(picAttrib.getVcPid());
//            clickResultset.setVcName(picAttrib.getVcName());
//            clickResultset.setVcAuthor(picAttrib.getVcAuthor());
//            clickResultset.setVcKeyword(picAttrib.getVcKeyword());
//            clickResultset.setVcPhotime(picAttrib.getVcPhotime());
//            //添加点击次数
//            clickResultsetBiz.insert(clickResultset);
        }

        return entityObjectRestResponse;
    }

    /**
     * 首页分页查询
     * @param params
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<PicAttrib> list(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException, ParseException {
      //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query,response);
    }

    /**
     * 审核图片管理分页查看
     * @param params
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/dudit/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<PicAttrib> PlicDuditlist(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException, ParseException {
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.PlicDuditlist(query,response);
    }

    /**
     * 图片查看
     * @param pid
     * @param type
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("图片查看")
    @GetMapping("/selectImg/{pid}/{type}")
    public void getCode(@PathVariable("pid") String pid,@PathVariable("type") String type,HttpServletRequest request,HttpServletResponse response) throws Exception{
        //查询出相关信息
        PicAttrib picAttrib = baseBiz.selectByVcPid(pid);
        if(picAttrib == null){
            return;
        }

        File originalFile = null;
        File directory = new File("");// 参数为空
        //工程的根目录
        String courseFile = directory.getCanonicalPath();
        if(type.equals("thumb")){
            //缩略图
             originalFile = new File(courseFile + "\\" + hddTpath + "\\" + picAttrib.getVcThumb());
        }if(type.equals("upload")){
            //原图
            originalFile = new File(courseFile + "\\" + hddUpath + "\\" + picAttrib.getVcUpload());
        }

        FileInputStream in = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(originalFile);
            BufferedImage image = ImageIO.read(in);
            response.setContentType("image/jpg");
             os = response.getOutputStream();
            ImageIO.write(image, "jpg", os);
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            os.close();
            in.close();
        }
    }

    /**
     * 上一组图
     * @param vcPid
     * @return
     */
    @RequestMapping(value = "/nextCluster/{vcPid}",method = RequestMethod.GET)
    public ObjectRestResponse<PicAttrib> nextCluster(@PathVariable("vcPid") String vcPid,@RequestParam Map<String, Object> params) throws ParseException {
        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();
        Query query = new Query(params);
        PicAttrib picAttrib = baseBiz.nextCluster(vcPid,query);
        if(picAttrib == null){
            entityObjectRestResponse.data((PicAttrib)picAttrib);
            return entityObjectRestResponse;
        }
        List<PicAttrib> picAttribs = baseBiz.getPicAttribByGid(picAttrib.getVcGroupid());
        picAttrib.setPidList(picAttribs);
        entityObjectRestResponse.data((PicAttrib)picAttrib);
        return entityObjectRestResponse;
    }

    /**
     * 下一组图
     * @param vcPid
     * @return
     */
    @RequestMapping(value = "/downCluster/{vcPid}",method = RequestMethod.GET)
    public ObjectRestResponse<PicAttrib> downCluster(@PathVariable("vcPid") String vcPid){
        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();
        PicAttrib picAttrib = baseBiz.downCluster(vcPid);
        if(picAttrib == null){
            entityObjectRestResponse.data((PicAttrib)picAttrib);
            return entityObjectRestResponse;
        }
        List<PicAttrib> picAttribs = baseBiz.getPicAttribByGid(picAttrib.getVcGroupid());
        picAttrib.setPidList(picAttribs);
        entityObjectRestResponse.data((PicAttrib)picAttrib);
        return entityObjectRestResponse;
    }

    /**
     * 查看组图下的所有图片
     * @param vcGroupid
     * @return
     */
    @RequestMapping(value = "/getPicAttribByGid/{vcGroupid}",method = RequestMethod.GET)
    public List<PicAttrib> getPicAttribByGid(@PathVariable("vcGroupid") String vcGroupid){
       return baseBiz.getPicAttribByGid(vcGroupid);
    }

    /**
     * 不知道有没有操作到，待前端确认
     * @param vcPid
     * @return
     */
    // 修改状态
    @RequestMapping(value = "/updateStatus",method = RequestMethod.PUT)
    public boolean updatePicAttribStatusById(String vcPid){
        PicAttrib picAttrib = baseBiz.selectByVcPid(vcPid);
        if(picAttrib == null){
            return false;
        }
        if(picAttrib.getcStatus().equals("1")){
            picAttrib.setcStatus("2");
        }else if(picAttrib.getcStatus().equals("2")){
            picAttrib.setcStatus("1");
        }
        baseBiz.updatePicById(picAttrib);
        return true;
    }

    /**
     * 我要上传->上传图片
     * 多文件以及带参数上传
     * @param file
     * @param StringBicAttrib
     * @throws IOException
     */
    @SystemControllerLog(descrption = "图片管理->上传图片")
    @Transactional
    @RequestMapping(value = "/upLoadImg",method = RequestMethod.POST)
    public ObjectRestResponse upLoadImg(@RequestParam("file") List<MultipartFile> file,@RequestParam("picAttrib") String StringBicAttrib) {

        ObjectRestResponse entityObjectRestResponse = new ObjectRestResponse<>();

        //将字符串转json格式
        JSONObject jsStr = JSONObject.fromObject(StringBicAttrib);
        //Json转对象
        PicAttrib picAttrib = (PicAttrib) JSONObject.toBean(jsStr,PicAttrib.class);

        //存Id
        List<String> stringList = new ArrayList<>();

        //获取时间戳，用来做组Id
        String gid = String.valueOf(System.currentTimeMillis());
        picAttrib.setVcGroupid(gid);

        try{
            //循环上传图片
            for(int i = 0;i<file.size();i++){
                if(file.size() >1){
                    ;picAttrib.setcGroup("1");
                }else{
                    picAttrib.setcGroup("0");
                }
                //第一张作为默认图
                if(i == 0){
                    String pid =  upload.uploadFile(file.get(i),picAttrib,1);
                    stringList.add(pid);
                }else{
                    String pid =  upload.uploadFile(file.get(i),picAttrib,0);
                    stringList.add(pid);
                }
            }
            entityObjectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            entityObjectRestResponse.setMessage("上传成功");
        }catch (Exception e){
            entityObjectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            entityObjectRestResponse.setMessage("上传失败");
            entityObjectRestResponse.setException(ExceptionUtil.getStackTrace(e));
        }
        entityObjectRestResponse.setActionType("上传图片");
        //存Id
        entityObjectRestResponse.setStringList(stringList);
        return entityObjectRestResponse;
    }

    /**
     * 设置图片置顶操作
     * @param stick
     * @param picStickTime
     * @param picAttrib
     * @return
     * @throws ParseException
     */
    @Transactional
    @SystemControllerLog(descrption = "图片管理->置顶操作")
    @RequestMapping(value = "/updateStick/{stick}/{picStickTime}",method = RequestMethod.PUT)
    public ObjectRestResponse updateStick(@PathVariable("stick") Integer stick, @PathVariable("picStickTime")Integer picStickTime, @RequestBody List<PicAttrib> picAttrib) throws ParseException {

        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();

        //存储操作的Id
        List<String> stringId = new ArrayList<>();

        //判断操作
        if(stick != null && stick == 1){
            entityObjectRestResponse.setActionType("设置置顶");
        }else if(stick != null && stick == 0){
            entityObjectRestResponse.setActionType("取消置顶");
        }else{
            entityObjectRestResponse.setActionType("异常处理");
        }

        try{
            //循环修改
            for(PicAttrib pic : picAttrib){
                stringId.add(pic.getVcPid());
                pic.setPicStickTime(picStickTime);
                pic.setPicStick(stick);
                baseBiz.updateStick(pic);
            }
            entityObjectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            entityObjectRestResponse.setMessage("设置成功");
            entityObjectRestResponse.setStringList(stringId);
        }catch (Exception e){
            entityObjectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            entityObjectRestResponse.setMessage("设置失败");
            entityObjectRestResponse.setException(ExceptionUtil.getStackTrace(e));
        }


        return entityObjectRestResponse;
    }

    /**
     * 删除整个组图
     * @param picAttribs
     * @throws IOException
     */
    @SystemControllerLog(descrption = "图片管理")
    @RequestMapping(value = "/deleteList",method = RequestMethod.POST)
    @Transactional
    public ObjectRestResponse deleteList(@RequestBody List<PicAttrib> picAttribs) throws IOException {

        ObjectRestResponse entityObjectRestResponse = new ObjectRestResponse<>();

        File directory = new File("");// 参数为空
        //工程的根目录
        String courseFile = directory.getCanonicalPath();

        List<String> stringId = new ArrayList<>();

        entityObjectRestResponse.setActionType("删除组图");
        try{
            for(PicAttrib picAttrib : picAttribs){
                stringId.add(picAttrib.getVcPid());
                PicComment picComment = new PicComment();
                picComment.setVcPid(picAttrib.getVcPid());
                picCommentBiz.deletePicComment(picComment);
                PicInfo picInfo = new PicInfo();
                picInfo.setVcPid(picAttrib.getVcPid());
                picInfoBiz.deletePicInfo(picInfo);
                DownloadForm downloadForm = new DownloadForm();
                downloadForm.setVcPid(picAttrib.getVcPid());
                downloadFormBiz.deleteDownloadForm(downloadForm);
                Integer i = baseBiz.deletePicAttribByGid(picAttrib);
                if(i != 0){
                    File file = new File(courseFile + "\\" + hddUpath + "\\" + picAttrib.getVcUpload());
                    File file2 = new File(courseFile + "\\" + hddTpath + "\\" + picAttrib.getVcThumb());
                    file.delete();
                    file2.delete();
                }
            }
            entityObjectRestResponse.setStringList(stringId);
            entityObjectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            entityObjectRestResponse.setMessage("删除组图成功");
        }catch (Exception e){
            entityObjectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            entityObjectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            entityObjectRestResponse.setMessage("删除组图失败");
        }
        return entityObjectRestResponse;
    }

    /**
     * 删除单个图片（审核操作里面的删除单个图片）
     * @param picAttribs
     * @return
     */
    @Transactional
    @SystemControllerLog(descrption = "审核管理")
    @RequestMapping(value = "/delete",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<PicAttrib> remove(@RequestBody List<PicAttrib> picAttribs){

        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();

        List<String> stringId = new ArrayList<>();

        entityObjectRestResponse.setActionType("删除图片");
        try{
            File directory = new File("");// 参数为空
            //工程的根目录
            String courseFile = directory.getCanonicalPath();
            for(PicAttrib picAttrib : picAttribs){
                stringId.add(picAttrib.getVcPid());
                baseBiz.deleteById(picAttrib);
                //如果是组图的话
                if(picAttrib.getcGroup().equals("1")){
                    //重新查询出最早上传的一张图片进行修改成默认图片
                    PicAttrib topPicAttrib =  baseBiz.selectTopPicAttrib(picAttrib);
                    if(topPicAttrib != null){
                        topPicAttrib.setcMain("1");
                        baseBiz.updateSelectiveById(topPicAttrib);
                    }
                    entityObjectRestResponse.data((PicAttrib)picAttrib);
                }
                File file = new File(courseFile + "\\" + hddUpath + "\\" + picAttrib.getVcUpload());
                File file2 = new File(courseFile + "\\" + hddTpath + "\\" + picAttrib.getVcThumb());
                file.delete();
                file2.delete();
            }
            entityObjectRestResponse.setStringList(stringId);
            entityObjectRestResponse.setMessage("删除单个图片成功");
            entityObjectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
        }catch (Exception e){
            entityObjectRestResponse.setMessage("删除单个图片失败");
            entityObjectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            entityObjectRestResponse.setException(ExceptionUtil.getStackTrace(e));
        }

        return entityObjectRestResponse;
    }

    /**
     * 批量审核操作（采用，拒绝）
     * @param status
     * @param reason
     * @param picAttribList
     */
    @RequestMapping(value = "/updateStatusList/{status}",method = RequestMethod.PUT)
    @Transactional
    @SystemControllerLog(descrption = "审核图片")
    public ObjectRestResponse updatePicAttribStatusList(@PathVariable("status") String status,@Param("reason") String reason,@RequestBody List<PicAttrib> picAttribList){

        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();

        //存Id
        List<String> stringId = new ArrayList<>();

        if(status.equals("1")){
            entityObjectRestResponse.setActionType("已采用");
        }else if(status.equals("2")){
            entityObjectRestResponse.setActionType("已拒绝");
        }

        try{
            //循环审批
            for(PicAttrib picAttrib : picAttribList){
                stringId.add(picAttrib.getVcPid());
                picAttrib =  baseBiz.selectById(picAttrib);
                picAttrib.setcStatus(status);
                picAttrib.setVcReason(reason);
                baseBiz.updateSelectiveById(picAttrib);
            }
            entityObjectRestResponse.setStringList(stringId);
            entityObjectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            entityObjectRestResponse.setMessage("审核操作成功");
        }catch (Exception e){
            entityObjectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            entityObjectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            entityObjectRestResponse.setMessage("审核操作失败");
        }

        return entityObjectRestResponse;
    }

}
