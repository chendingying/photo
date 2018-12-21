package com.photo.warehouse.biz.admin;

import com.photo.warehouse.mapper.admin.UserInfoMapper;
import com.photo.warehouse.model.admin.UserInfo;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CDZ on 2018/12/7.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoBiz extends BaseBiz<UserInfoMapper,UserInfo> {
}
