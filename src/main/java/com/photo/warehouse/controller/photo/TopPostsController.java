package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.TopPostsBiz;
import com.photo.warehouse.model.photo.TopPosts;
import com.photo.warehouse.util.BaseController;
import com.photo.warehouse.util.ObjectRestResponse;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/topPosts")
public class TopPostsController{

    @Autowired
    TopPostsBiz topPostsBiz;

    @RequestMapping(value = "/getAllTopPost/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<TopPosts> GetAllTopPost(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return topPostsBiz.GetAllTopPost(query);
    }

    @RequestMapping(value = "/getAllTopPost2/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<TopPosts> GetAllTopPost2(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return topPostsBiz.GetAllTopPost2(query);
    }

    @RequestMapping(value = "/{vcId}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<TopPosts> get(@PathVariable String vcId){
        ObjectRestResponse<TopPosts> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = topPostsBiz.GetTopPostById(vcId);
        entityObjectRestResponse.data((TopPosts)o);
        return entityObjectRestResponse;
    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<TopPosts> add(@RequestBody TopPosts entity){
        topPostsBiz.insertSelective(entity);
        return new ObjectRestResponse<TopPosts>();
    }
}
