package com.photo.warehouse.biz.backstage;

import com.photo.warehouse.mapper.backstage.OptionsLibMapper;
import com.photo.warehouse.model.backstage.OptionsLib;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OptionsLibBiz extends BaseBiz<OptionsLibMapper,OptionsLib> {

   public List<OptionsLib> selectByvcDescribe(String vcDescribe, String cOpti){
        return mapper.selectByvcDescribe(vcDescribe,cOpti);
    }

    public void updateByIBlank(OptionsLib optionsLib){
         mapper.updateByIBlank(optionsLib);
    }

    public void deleteOptionsLib(OptionsLib optionsLib){
        mapper.deleteOptionsLib(optionsLib);
    }
}
