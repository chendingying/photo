package com.photo.warehouse.model.photo;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/8.
 * 下载申请
 */
@Table(name = "download_form")
public class DownloadForm {

    //图片id
    @Column(name = "vc_pid")
    private String vcPid;

    //类型(0海关,1其它)
    @Column(name = "c_type")
    private String cType;

    //申请人id
    @Column(name = "vc_uid")
    private String vcUid;

    //申请人类型(1单位部门用户,2个人注册用户)
    @Column(name = "c_utype")
    private String cUtype;

    //申请人姓名
    @Column(name = "vc_uname")
    private String vcUname;

    //申请理由
    @Column(name = "vc_reason")
    private String vcReason;

    //提出申请时间
    @Column(name = "dt_posted")
    private Date dtPosted;

    //图片摘要
    @Column(name = "vc_picinfo")
    private String vcPicinfo;

    //申请状态(0待审,1同意,2拒绝)
    @Column(name = "c_status")
    private String cStatus;

    //图片上传人id
    @Column(name = "vc_oid")
    private String vcOid;

    //上传人类型(1单位部门用户,2个人注册用户)
    @Column(name = "c_otype")
    private String cOtype;

    //上传人查看标识(0未阅,1已阅)
    @Column(name = "c_read")
    private String cRead;

    @Column(name = "vc_groupid")
    private String vcGroupid;

    public String getVcGroupid() {
        return vcGroupid;
    }

    public void setVcGroupid(String vcGroupid) {
        this.vcGroupid = vcGroupid;
    }

    public String getVcPid() {
        return vcPid;
    }

    public void setVcPid(String vcPid) {
        this.vcPid = vcPid;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getVcUid() {
        return vcUid;
    }

    public void setVcUid(String vcUid) {
        this.vcUid = vcUid;
    }

    public String getcUtype() {
        return cUtype;
    }

    public void setcUtype(String cUtype) {
        this.cUtype = cUtype;
    }

    public String getVcUname() {
        return vcUname;
    }

    public void setVcUname(String vcUname) {
        this.vcUname = vcUname;
    }

    public String getVcReason() {
        return vcReason;
    }

    public void setVcReason(String vcReason) {
        this.vcReason = vcReason;
    }

    public Date getDtPosted() {
        return dtPosted;
    }

    public void setDtPosted(Date dtPosted) {
        this.dtPosted = dtPosted;
    }

    public String getVcPicinfo() {
        return vcPicinfo;
    }

    public void setVcPicinfo(String vcPicinfo) {
        this.vcPicinfo = vcPicinfo;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getVcOid() {
        return vcOid;
    }

    public void setVcOid(String vcOid) {
        this.vcOid = vcOid;
    }

    public String getcOtype() {
        return cOtype;
    }

    public void setcOtype(String cOtype) {
        this.cOtype = cOtype;
    }

    public String getcRead() {
        return cRead;
    }

    public void setcRead(String cRead) {
        this.cRead = cRead;
    }
}
