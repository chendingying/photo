package com.photo.warehouse.controller.admin;

import com.photo.warehouse.biz.admin.UserInfoBiz;
import com.photo.warehouse.model.admin.UserInfo;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CDZ on 2018/12/7.
 */
@RestController
@RequestMapping("/api/admin/userInfo")
public class UserInfoController  extends BaseController<UserInfoBiz,UserInfo> {
}
