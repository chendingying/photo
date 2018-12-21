package com.photo.warehouse.controller.admin;

import com.photo.warehouse.biz.admin.DeptInfoBiz;
import com.photo.warehouse.model.admin.DeptInfo;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/admin/deptInfo")
public class DeptInfoController  extends BaseController<DeptInfoBiz,DeptInfo> {
}
