package com.photo.warehouse.biz.backstage;

import com.photo.warehouse.mapper.backstage.BaseLogMapper;
import com.photo.warehouse.model.backstage.BaseLog;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CDZ on 2018/12/20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseLogBiz extends BaseBiz<BaseLogMapper,BaseLog> {
}
