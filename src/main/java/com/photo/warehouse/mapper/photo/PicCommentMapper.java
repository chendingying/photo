package com.photo.warehouse.mapper.photo;

import com.photo.warehouse.model.photo.PicComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 */
public interface PicCommentMapper extends Mapper<PicComment> {
    int updateiBlankByUid(@Param("uid") String uid,@Param("status") String status);
    List<PicComment> selectCommontAll();
    int updateIBlankByUidAndPidAndCommont(@Param("picComment")PicComment picComment);
    int updatePicStick(@Param("picComment") PicComment picComment);
    int deletePicComment(@Param("picComment") PicComment picComment);
    List<PicComment> selectCommontByPid(@Param("vcPid")String  vcPid);
}
