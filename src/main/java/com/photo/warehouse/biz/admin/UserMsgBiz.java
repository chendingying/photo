package com.photo.warehouse.biz.admin;

import com.photo.warehouse.mapper.admin.UserMsgMapper;
import com.photo.warehouse.model.admin.UserMsg;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserMsgBiz extends BaseBiz<UserMsgMapper,UserMsg> {
}
