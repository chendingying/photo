package com.photo.warehouse.mapper.photo;

import com.photo.warehouse.model.photo.DownloadForm;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by CDZ on 2018/12/8.
 */
public interface DownloadFormMapper extends Mapper<DownloadForm> {
    Integer updateDownloadFormStatus(@Param("cStatus") String cStatus,@Param("vcPid") String vcPid);

    Integer deleteDownloadForm(@Param("downloadForm")DownloadForm downloadForm);
}
