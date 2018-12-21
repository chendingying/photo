package com.photo.warehouse.biz.photo;

import com.photo.warehouse.mapper.photo.CommentResultsetMapper;
import com.photo.warehouse.model.photo.CommentResultset;
import com.photo.warehouse.util.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CDZ on 2018/12/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentResultsetBiz extends BaseBiz<CommentResultsetMapper,CommentResultset> {
}
