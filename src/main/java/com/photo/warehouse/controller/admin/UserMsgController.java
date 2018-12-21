package com.photo.warehouse.controller.admin;

import com.photo.warehouse.biz.admin.UserMsgBiz;
import com.photo.warehouse.model.admin.UserMsg;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/admin/userMsg")
public class UserMsgController  extends BaseController<UserMsgBiz,UserMsg> {
}
