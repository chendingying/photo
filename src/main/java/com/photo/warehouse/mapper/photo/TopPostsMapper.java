package com.photo.warehouse.mapper.photo;

import com.photo.warehouse.model.photo.TopPosts;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 */
public interface TopPostsMapper extends Mapper<TopPosts> {
    List<TopPosts> GetAllTopPost(@Param("year") int year);
    List<TopPosts> GetAllTopPost2(@Param("year") int year);
    TopPosts GetTopPostById(@Param("vcId") String vcId);
    void InsertTopPost(@Param("topPosts") TopPosts topPosts);
    void DeleteTopPost(@Param("vcId") String vcId);
    void UpdateTopPost(@Param("topPosts") TopPosts topPosts);
}
