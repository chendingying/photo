package com.photo.warehouse.biz.photo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.photo.warehouse.mapper.photo.TopPostsMapper;
import com.photo.warehouse.model.photo.TopPosts;
import com.photo.warehouse.util.BaseBiz;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TopPostsBiz  extends BaseBiz<TopPostsMapper,TopPosts> {

    public TableResultResponse<TopPosts> GetAllTopPost(Query query){
        int year = Integer.valueOf(query.get("year").toString());
        List<TopPosts> list = mapper.GetAllTopPost(year);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        return new TableResultResponse<TopPosts>(result.getTotal(), list);
    }

    public TableResultResponse<TopPosts> GetAllTopPost2(Query query){
        int year = Integer.valueOf(query.get("year").toString());
        List<TopPosts> list = mapper.GetAllTopPost2(year);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        return new TableResultResponse<TopPosts>(result.getTotal(), list);
    }
    public TopPosts GetTopPostById(String vcId){
        return mapper.GetTopPostById(vcId);
    }
}
