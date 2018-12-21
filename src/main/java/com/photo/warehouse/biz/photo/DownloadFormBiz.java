package com.photo.warehouse.biz.photo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.photo.warehouse.mapper.photo.DownloadFormMapper;
import com.photo.warehouse.model.photo.DownloadForm;
import com.photo.warehouse.util.BaseBiz;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DownloadFormBiz extends BaseBiz<DownloadFormMapper,DownloadForm> {
    public Integer updateDownloadFormStatus(String cStatus,String vcPid){
        return mapper.updateDownloadFormStatus(cStatus,vcPid);
    }

    public Integer deleteDownloadForm(DownloadForm downloadForm){
        return mapper.deleteDownloadForm(downloadForm);
    }

    public TableResultResponse<DownloadForm> selectByQuery(Query query) {
        Class<DownloadForm> clazz = (Class<DownloadForm>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.setOrderByClause("dt_posted DESC");
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<DownloadForm> list = mapper.selectByExample(example);
        return new TableResultResponse<DownloadForm>(result.getTotal(), list);
    }
}
