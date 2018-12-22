package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.PicInfoBiz;
import com.photo.warehouse.log.ReturnCode;
import com.photo.warehouse.log.SystemControllerLog;
import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.model.photo.PicInfo;
import com.photo.warehouse.model.photo.response.PicInfoResponse;
import com.photo.warehouse.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/picInfo")
public class PicInfoController {


    @Autowired
    PicInfoBiz baseBiz;

    /**
     * 删除收藏
     */
    @Transactional
    @SystemControllerLog(descrption = "收藏夹")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<PicInfo> update(@RequestBody PicInfo entity){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("删除图片收藏");

        try{
            List<String> strings = new ArrayList<>();
            strings.add(entity.getVcPid());
            baseBiz.delete(entity);
            objectRestResponse.setStringList(strings);
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            objectRestResponse.setMessage("删除成功");
        }catch (Exception e){
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setMessage("删除失败");
        }

        return objectRestResponse;
    }

    /**
     * 添加收藏
     * @param entity
     * @return
     */
    @Transactional
    @SystemControllerLog(descrption = "收藏夹")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<PicInfo> add(@RequestBody PicInfo entity){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("新增图片收藏");
        try{
            List<String> stringId = new ArrayList<>();
            stringId.add(entity.getVcPid());
            entity.setDtStamp(new Date());
            baseBiz.insertSelective(entity);

            objectRestResponse.setStringList(stringId);
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            objectRestResponse.setMessage("新增成功");
        }catch (Exception e){
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setMessage("新增失败");
        }

        return objectRestResponse;
    }

    /**
     * 查看收藏
     * @param params
     * @return
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<PicInfo> list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectPicInfoByQuery(query);
    }

    /**
     * 上一组图
     * @param vcPid
     * @return
     */
    @RequestMapping(value = "/nextCluster/{vcPid}",method = RequestMethod.GET)
    public ObjectRestResponse<PicAttrib> nextCluster(@PathVariable("vcPid") String vcPid){
        ObjectRestResponse<PicAttrib> entityObjectRestResponse = new ObjectRestResponse<>();
        PicAttrib picAttrib = baseBiz.nextCluster(vcPid);
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
        entityObjectRestResponse.data((PicAttrib)picAttrib);
        return entityObjectRestResponse;
    }
}
