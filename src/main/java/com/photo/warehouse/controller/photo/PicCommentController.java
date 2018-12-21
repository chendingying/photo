package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.PicCommentBiz;
import com.photo.warehouse.model.photo.PicComment;
import com.photo.warehouse.util.BaseController;
import com.photo.warehouse.util.ObjectRestResponse;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/picComment")
public class PicCommentController{

    @Autowired
    protected PicCommentBiz baseBiz;
    /**
     * 屏蔽用户所有的评论
     * @param vcUid
     */
    @RequestMapping(value = "/uId",method = RequestMethod.PUT)
    public void updateiBlankByUid(String vcUid,String iBlank){
        baseBiz.updateiBlankByUid(vcUid,iBlank);
    }

    /**
     * 屏蔽当条评论
     * @param picComment
     */
    @RequestMapping(value = "/iBlank",method = RequestMethod.PUT)
    @Transactional
    public ObjectRestResponse<PicComment> updateIBlankByUidAndPidAndCommont(@RequestBody List<PicComment> picComment){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse<PicComment>();
        for(PicComment pic : picComment){
            Integer type = baseBiz.updateIBlankByUidAndPidAndCommont(pic);
            if(type == 1){
                objectRestResponse.rel(true);
            }else{
                return objectRestResponse;
            }
        }
        return objectRestResponse;
    }

    /**
     * 查询所有评论
     * @return
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<PicComment> selectCommontAll(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectCommontAll(query);
    }

    /**
     * 评论管理分页查询
     * @return
     */
    @RequestMapping(value = "/selectByQuery/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<PicComment> selectByQuery(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }

    /**
     * 新增评论
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<PicComment> add(@RequestBody PicComment entity, HttpServletRequest request){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse<PicComment>();
        entity.setDtCommtime(new Date());
        entity.setVcIp(request.getRemoteAddr());
        Integer type = baseBiz.insertSelective(entity);
        if(type == 1){
            objectRestResponse.rel(true);
        }
        return objectRestResponse;
    }

    /**
     * 设置置顶
     * @param picComment
     */
    @RequestMapping(value = "/updatePicStick/{stick}/{picStickTime}",method = RequestMethod.PUT)
    public void updatePicStick(@PathVariable("stick") Integer stick,@PathVariable("picStickTime")Integer picStickTime, @RequestBody List<PicComment> picComment){
        for(PicComment pic : picComment){
            pic.setPicStickTime(picStickTime);
            pic.setPicStick(stick);
            baseBiz.updatePicStick(pic);
        }
    }

    /**
     * 删除评论
     */
    @RequestMapping(value = "/deleteList",method = RequestMethod.POST)
    public void deleteList(@RequestBody List<PicComment> picCommentList){
        for(PicComment picComment : picCommentList){
            baseBiz.deletePicComment(picComment);
        }
    }
}
