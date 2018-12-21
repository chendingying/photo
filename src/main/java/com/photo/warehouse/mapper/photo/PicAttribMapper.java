package com.photo.warehouse.mapper.photo;

import com.photo.warehouse.model.photo.PicAttrib;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by CDZ on 2018/12/8.
 */
public interface PicAttribMapper extends Mapper<PicAttrib>{
    PicAttrib selectByVcPid(@Param("vcPid") String vcPid);
    void updatePicById(@Param("picAttrib") PicAttrib picAttrib);
    List<PicAttrib> getAllPicAttrib(String type);
    void updateStick(@Param("picAttrib") PicAttrib picAttrib);
    List<PicAttrib> getPicAttribByGid(@Param("vcGroupid") String vcGroupid);
    PicAttrib selectTopPicAttrib(@Param("picAttrib") PicAttrib picAttrib);
    PicAttrib nextCluster(@Param("vcPid") String vcPid);
    PicAttrib downCluster(@Param("vcPid") String vcPid);
    Integer deletePicAttribByGid(@Param("picAttrib") PicAttrib picAttrib);
    List<PicAttrib> selectForPicStickEndTime();
}
