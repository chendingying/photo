package com.photo.warehouse.mapper.photo;

import com.photo.warehouse.model.photo.PicAttrib;
import com.photo.warehouse.model.photo.PicInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 */
public interface PicInfoMapper extends Mapper<PicInfo> {
    PicInfo nextCluster(@Param("vcPid") String vcPid);
    PicInfo downCluster(@Param("vcPid") String vcPid);
    List<PicInfo> selectPicInfoByPid(@Param("vcPid") String vcPid);
    Integer deletePicInfo(@Param("picInfo") PicInfo picInfo);
    List<PicInfo> selectPicAttribList(@Param("vcName") String vcName);
}
