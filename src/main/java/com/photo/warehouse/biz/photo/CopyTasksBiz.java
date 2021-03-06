package com.photo.warehouse.biz.photo;

import com.photo.warehouse.mapper.photo.CopyTasksMapper;
import com.photo.warehouse.model.photo.CopyTasks;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CopyTasksBiz extends BaseBiz<CopyTasksMapper,CopyTasks> {
}
