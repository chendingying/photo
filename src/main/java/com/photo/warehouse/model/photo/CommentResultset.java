package com.photo.warehouse.model.photo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/8.
 * 关注率排行
 */
@Table(name = "comment_resultset")
public class CommentResultset {

    //图片id
    @Column(name = "vc_pid")
    private String vcPid;

    //图片标题
    @Column(name = "vc_name")
    private String vcName;

    //作者
    @Column(name = "vc_author")
    private String vcAuthor;

    //关键词
    @Column(name = "vc_keyword")
    private String vcKeyword;

    //拍摄时间
    @Column(name = "vc_photime")
    private String vcPhotime;

    //评论次数
    @Column(name = "i_comms")
    private Integer iComms;

    public String getVcPid() {
        return vcPid;
    }

    public void setVcPid(String vcPid) {
        this.vcPid = vcPid;
    }

    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
    }

    public String getVcAuthor() {
        return vcAuthor;
    }

    public void setVcAuthor(String vcAuthor) {
        this.vcAuthor = vcAuthor;
    }

    public String getVcKeyword() {
        return vcKeyword;
    }

    public void setVcKeyword(String vcKeyword) {
        this.vcKeyword = vcKeyword;
    }

    public String getVcPhotime() {
        return vcPhotime;
    }

    public void setVcPhotime(String vcPhotime) {
        this.vcPhotime = vcPhotime;
    }

    public Integer getiComms() {
        return iComms;
    }

    public void setiComms(Integer iComms) {
        this.iComms = iComms;
    }
}
