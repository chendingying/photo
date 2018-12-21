package com.photo.warehouse.biz.photo;

import com.photo.warehouse.mapper.photo.ClickResultsetMapper;
import com.photo.warehouse.model.photo.ClickResultset;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ClickResultsetBiz extends BaseBiz<ClickResultsetMapper,ClickResultset> {
    public void insert(ClickResultset clickResultset){
        ClickResultset click =  mapper.selectClickResultsetByPid(clickResultset.getVcPid());
        if(click == null){
            clickResultset.setiClicks(1);
            mapper.insert(clickResultset);
        }else{
            click.setiClicks(click.getiClicks()+1);
            mapper.updateClickResult(click);
        }

    }
}
