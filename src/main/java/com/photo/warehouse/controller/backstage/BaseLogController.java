package com.photo.warehouse.controller.backstage;

import com.photo.warehouse.biz.backstage.BaseLogBiz;
import com.photo.warehouse.model.backstage.BaseLog;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CDZ on 2018/12/20.
 */
@RestController
@RequestMapping("/api/photo/baseLog")
public class BaseLogController extends BaseController<BaseLogBiz,BaseLog> {
}
