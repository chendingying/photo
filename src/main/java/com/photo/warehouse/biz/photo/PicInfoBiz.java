package com.photo.warehouse.biz.photo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.photo.warehouse.mapper.photo.PicInfoMapper;
import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.model.photo.PicInfo;
import com.photo.warehouse.model.photo.response.PicInfoResponse;
import com.photo.warehouse.util.BaseBiz;
import com.photo.warehouse.util.Query;
import com.photo.warehouse.util.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PicInfoBiz extends BaseBiz<PicInfoMapper,PicInfo> {

    @Autowired
    PicAttribBiz picAttribBiz;

    public TableResultResponse<PicInfo> selectPicInfoByQuery(Query query) {
        Class<PicInfo> clazz = (Class<PicInfo>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        example.setOrderByClause("dt_stamp DESC");
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        if(query.entrySet().size()>0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                if(entry.getKey().equals("span")){
                    continue;
                }
                if(entry.getKey().equals("vcName")){
                    List<PicInfo> list =   mapper.selectPicAttribList(entry.getValue().toString());
                    for(PicInfo picInfo : list){
                        PicAttrib picAttrib = picAttribBiz.selectByVcPid(picInfo.getVcPid());
                        if(picAttrib != null){
                            picInfo.setVcName(picAttrib.getVcName());
                        }
                    }
                    return new TableResultResponse<PicInfo>(result.getTotal(), list);
                }
            }
        }

        List<PicInfo> list = mapper.selectByExample(example);
        for(PicInfo picInfo : list){
            PicAttrib picAttrib = picAttribBiz.selectByVcPid(picInfo.getVcPid());
            if(picAttrib != null){
                picInfo.setVcName(picAttrib.getVcName());
            }
        }
        return new TableResultResponse<PicInfo>(result.getTotal(), list);
    }
    public PicAttrib nextCluster(String vcPid){
        PicInfo picInfo =  mapper.nextCluster(vcPid);
        if(picInfo == null){
            return null;
        }
        PicAttrib picAttrib = picAttribBiz.selectByVcPid(picInfo.getVcPid());
        List<PicAttrib> picAttribs = picAttribBiz.getPicAttribByGid(picAttrib.getVcGroupid());
        picAttrib.setPidList(picAttribs);
        return picAttrib;
    }

    public PicAttrib downCluster(String vcPid){
        PicInfo picInfo =  mapper.downCluster(vcPid);
        if(picInfo == null){
            return null;
        }
        PicAttrib picAttrib = picAttribBiz.selectByVcPid(picInfo.getVcPid());
        List<PicAttrib> picAttribs = picAttribBiz.getPicAttribByGid(picAttrib.getVcGroupid());
        picAttrib.setPidList(picAttribs);
        return picAttrib;
    }

    public List<PicInfo> selectPicInfoByPid(String vcPid){
        return mapper.selectPicInfoByPid(vcPid);
    }

    public Integer deletePicInfo(PicInfo picInfo){
        return mapper.deletePicInfo(picInfo);
    }

}
