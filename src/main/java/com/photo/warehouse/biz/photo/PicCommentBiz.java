package com.photo.warehouse.biz.photo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.photo.warehouse.mapper.photo.PicCommentMapper;
import com.photo.warehouse.model.photo.DownloadForm;
import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.model.photo.PicComment;
import com.photo.warehouse.util.BaseBiz;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PicCommentBiz extends BaseBiz<PicCommentMapper,PicComment> {

    public Integer updateiBlankByUid(String uId,String status){
       return mapper.updateiBlankByUid(uId,status);
    }

    public TableResultResponse<PicComment> selectCommontAll(Query query){
        Class<PicComment> clazz = (Class<PicComment>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.setOrderByClause("pic_stick DESC,dt_commtime DESC");
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if (entry.getValue().equals("")) {
                    continue;
                }if(entry.getKey().equals("span")){
                    continue;
                }
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<PicComment> list  =mapper.selectByExample(example);
        return new TableResultResponse<PicComment>(result.getTotal(), list);
    }

    public TableResultResponse<PicComment> selectByQuery(Query query) {
        Class<PicComment> clazz = (Class<PicComment>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.setOrderByClause("dt_commtime DESC");
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<PicComment> list = mapper.selectByExample(example);
        return new TableResultResponse<PicComment>(result.getTotal(), list);
    }

    public Integer updateIBlankByUidAndPidAndCommont(PicComment picComment){
       return mapper.updateIBlankByUidAndPidAndCommont(picComment);
    }

    public Integer updatePicStick(PicComment picComment){
        picComment.setPicStickCreateTime(new Date());
        return mapper.updatePicStick(picComment);
    }

    public Integer deletePicComment(PicComment picComment){
        return mapper.deletePicComment(picComment);
    }

    public List<PicComment> selectCommontByPid(String Pid){
        return mapper.selectCommontByPid(Pid);
    }
}
