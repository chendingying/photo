package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.PicInfoBiz;
import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.model.photo.PicInfo;
import com.photo.warehouse.model.photo.response.PicInfoResponse;
import com.photo.warehouse.util.BaseController;
import com.photo.warehouse.util.ObjectRestResponse;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/picInfo")
public class PicInfoController {

    /**
     * 取消收藏
     */
    @Autowired
    PicInfoBiz baseBiz;
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<PicInfo> update(@RequestBody PicInfo entity){
        baseBiz.delete(entity);
        return new ObjectRestResponse<PicInfo>();
    }

    /**
     * 添加收藏
     * @param entity
     * @return
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<PicInfo> add(@RequestBody PicInfo entity){
        entity.setDtStamp(new Date());
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<PicInfo>();
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
