package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.PicCommentBiz;
import com.photo.warehouse.log.ReturnCode;
import com.photo.warehouse.log.SystemControllerLog;
import com.photo.warehouse.model.photo.PicComment;
import com.photo.warehouse.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/picComment")
public class PicCommentController{

    @Autowired
    protected PicCommentBiz baseBiz;

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
    @Transactional
    @SystemControllerLog(descrption = "评论管理")
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<PicComment> add(@RequestBody PicComment entity, HttpServletRequest request){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse<PicComment>();

        entity.setDtCommtime(new Date());
        entity.setVcIp(request.getRemoteAddr());
        //以时间戳作为pid
        Random rnd = new Random();
        String pid = String.valueOf(System.currentTimeMillis() + rnd.nextInt(1000));
        entity.setId(pid);
        List<String> stringId = new ArrayList<>();
        stringId.add(pid);
        objectRestResponse.setStringList(stringId);

        try{
            Integer type = baseBiz.insertSelective(entity);
            if(type == 1){
                objectRestResponse.rel(true);
            }
            if(entity.getVcType().equals("1")){
                objectRestResponse.setActionType("新增普通评论");
            }else if(entity.getVcType().equals("2")){
                objectRestResponse.setActionType("新增领导批示");
            }
            objectRestResponse.setMessage("新增成功");
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
        }catch (Exception e){
            objectRestResponse.setMessage("新增失败");
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
        }

        return objectRestResponse;
    }

    /**
     * 设置置顶
     * @param picComment
     */
    @Transactional
    @SystemControllerLog(descrption = "评论管理")
    @RequestMapping(value = "/updatePicStick/{stick}/{picStickTime}",method = RequestMethod.PUT)
    public ObjectRestResponse updatePicStick(@PathVariable("stick") Integer stick,@PathVariable("picStickTime")Integer picStickTime, @RequestBody List<PicComment> picComment){
       ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        if(stick == 0){
            objectRestResponse.setActionType("取消置顶");
        }else if(stick == 1){
            objectRestResponse.setActionType("设置置顶");
        }
        List<String> stringId = new ArrayList<>();
        try{
            for(PicComment pic : picComment){
                stringId.add(pic.getVcPid());
                pic.setPicStickTime(picStickTime);
                pic.setPicStick(stick);
                baseBiz.updatePicStick(pic);
            }
            objectRestResponse.setStringList(stringId);
            objectRestResponse.setMessage("设置成功");
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
         }catch (Exception e){
            objectRestResponse.setMessage("设置失败");
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
         }
         return objectRestResponse;
    }

    /**
     * 删除评论
     */
    @Transactional
    @SystemControllerLog(descrption = "评论管理")
    @RequestMapping(value = "/deleteList",method = RequestMethod.POST)
    public ObjectRestResponse deleteList(@RequestBody List<PicComment> picCommentList){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.setActionType("删除评论");
        List<String> stringId = new ArrayList<>();
        try{
            for(PicComment picComment : picCommentList){
                stringId.add(picComment.getId());
                baseBiz.deletePicComment(picComment);
            }
            objectRestResponse.setStringList(stringId);
            objectRestResponse.setResultCode(ReturnCode.RES_SUCCESS);
            objectRestResponse.setMessage("删除成功");
        }catch (Exception e){
            objectRestResponse.setMessage("删除失败");
            objectRestResponse.setResultCode(ReturnCode.RES_FAILED);
            objectRestResponse.setException(ExceptionUtil.getStackTrace(e));
        }
        return objectRestResponse;
    }


    //    /**
//     * 屏蔽用户所有的评论
//     * @param vcUid
//     */
//    @RequestMapping(value = "/uId",method = RequestMethod.PUT)
//    public void updateiBlankByUid(String vcUid,String iBlank){
//        baseBiz.updateiBlankByUid(vcUid,iBlank);
//    }
//
//    /**
//     * 屏蔽当条评论
//     * @param picComment
//     */
//    @RequestMapping(value = "/iBlank",method = RequestMethod.PUT)
//    @Transactional
//    public ObjectRestResponse<PicComment> updateIBlankByUidAndPidAndCommont(@RequestBody List<PicComment> picComment){
//        ObjectRestResponse objectRestResponse = new ObjectRestResponse<PicComment>();
//        for(PicComment pic : picComment){
//            Integer type = baseBiz.updateIBlankByUidAndPidAndCommont(pic);
//            if(type == 1){
//                objectRestResponse.rel(true);
//            }else{
//                return objectRestResponse;
//            }
//        }
//        return objectRestResponse;
//    }
}
