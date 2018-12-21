package com.photo.warehouse.controller.photo;

import com.photo.warehouse.biz.photo.CommentResultsetBiz;
import com.photo.warehouse.model.photo.CommentResultset;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CDZ on 2018/12/8.
 */
@RestController
@RequestMapping("/api/photo/commentResultset")
public class CommentResultsetController extends BaseController<CommentResultsetBiz,CommentResultset> {
}
