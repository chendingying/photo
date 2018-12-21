package com.photo.warehouse.model.photo;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/8.
 * 图片评论
 */
@Table(name = "pic_comment")
public class PicComment {
    //图片id
    @Column(name = "vc_pid")
    private String vcPid;

    //用户id
    @Column(name = "vc_uid")
    private String vcUid;
    @Transient
    private String vcUName;

    //评论时间
    @Column(name = "dt_commtime")
    private Date dtCommtime;

    //ip地址
    @Column(name = "vc_ip")
    private String vcIp;

    //评论内容
    @Column(name = "vc_comment")
    private String vcComment;

    //回复id(nil发起评论,<>nil回复评论)
    @Column(name = "vc_replyid")
    private String vcReplyid;

    //是否屏蔽
    @Column(name = "i_blank")
    private Integer iBlank;

    @Column(name = "vc_type")
    private String vcType;

    //置顶（0：不，1：是）
    @Column(name = "pic_stick")
    private Integer picStick;

    //置顶创建时间
    @Column(name = "pic_stick_create_time")
    private Date picStickCreateTime;

    //置顶天数
    @Column(name = "pic_stick_time")
    private Integer picStickTime;

    public Integer getPicStick() {
        return picStick;
    }

    public void setPicStick(Integer picStick) {
        this.picStick = picStick;
    }

    public Date getPicStickCreateTime() {
        return picStickCreateTime;
    }

    public void setPicStickCreateTime(Date picStickCreateTime) {
        this.picStickCreateTime = picStickCreateTime;
    }

    public Integer getPicStickTime() {
        return picStickTime;
    }

    public void setPicStickTime(Integer picStickTime) {
        this.picStickTime = picStickTime;
    }

    public String getVcType() {
        return vcType;
    }

    public void setVcType(String vcType) {
        this.vcType = vcType;
    }

    public String getVcPid() {
        return vcPid;
    }

    public void setVcPid(String vcPid) {
        this.vcPid = vcPid;
    }

    public String getVcUid() {
        return vcUid;
    }

    public void setVcUid(String vcUid) {
        this.vcUid = vcUid;
    }

    public Date getDtCommtime() {
        return dtCommtime;
    }

    public void setDtCommtime(Date dtCommtime) {
        this.dtCommtime = dtCommtime;
    }

    public String getVcIp() {
        return vcIp;
    }

    public void setVcIp(String vcIp) {
        this.vcIp = vcIp;
    }

    public String getVcComment() {
        return vcComment;
    }

    public void setVcComment(String vcComment) {
        this.vcComment = vcComment;
    }

    public String getVcReplyid() {
        return vcReplyid;
    }

    public void setVcReplyid(String vcReplyid) {
        this.vcReplyid = vcReplyid;
    }

    public Integer getiBlank() {
        return iBlank;
    }

    public void setiBlank(Integer iBlank) {
        this.iBlank = iBlank;
    }

    public String getVcUName() {
        return vcUName;
    }

    public void setVcUName(String vcUName) {
        this.vcUName = vcUName;
    }
}
