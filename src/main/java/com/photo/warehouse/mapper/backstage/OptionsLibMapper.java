package com.photo.warehouse.mapper.backstage;

import com.photo.warehouse.model.backstage.OptionsLib;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 */
public interface OptionsLibMapper extends Mapper<OptionsLib> {
    public List<OptionsLib> selectByvcDescribe(@Param("vcDescribe") String vcDescribe, @Param("cOpti") String cOpti);
    public void updateByIBlank(@Param("optionsLib") OptionsLib optionsLib);
    public void deleteOptionsLib(@Param("optionsLib") OptionsLib optionsLib);
}
