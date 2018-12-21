package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.ClickResultsetBiz;
import com.photo.warehouse.model.photo.ClickResultset;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/clickResultset")
public class ClickResultsetController extends BaseController<ClickResultsetBiz,ClickResultset> {
}
