package com.photo.warehouse.model.backstage;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/20.
 */
@Table(name = "base_log")
public class BaseLog {
    @Id
    private Integer logid;

    @Column(name = "logtype")
    private String logtype;

    @Column(name = "logcat")
    private String logcat;

    @Column(name = "message")
    private String message;

    @Column(name = "exception")
    private String exception;

    @Column(name = "userid")
    private String userid;

    @Column(name = "username")
    private String username;

    @Column(name = "allPathName")
    private String allPathName;

    @Column(name = "clientip")
    private String clientip;

    @Column(name = "operateurl")
    private String operateurl;

    @Column(name = "operatemodel")
    private String operatemodel;

    @Column(name = "operateact")
    private String operateact;

    @Column(name = "operatecomtent")
    private String operatecomtent;

    @Column(name = "operatedate")
    private Date operatedate;

    @Column(name = "operatemark")
    private String operatemark;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogcat() {
        return logcat;
    }

    public void setLogcat(String logcat) {
        this.logcat = logcat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAllPathName() {
        return allPathName;
    }

    public void setAllPathName(String allPathName) {
        this.allPathName = allPathName;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getOperateurl() {
        return operateurl;
    }

    public void setOperateurl(String operateurl) {
        this.operateurl = operateurl;
    }

    public String getOperatemodel() {
        return operatemodel;
    }

    public void setOperatemodel(String operatemodel) {
        this.operatemodel = operatemodel;
    }

    public String getOperateact() {
        return operateact;
    }

    public void setOperateact(String operateact) {
        this.operateact = operateact;
    }

    public String getOperatecomtent() {
        return operatecomtent;
    }

    public void setOperatecomtent(String operatecomtent) {
        this.operatecomtent = operatecomtent;
    }

    public Date getOperatedate() {
        return operatedate;
    }

    public void setOperatedate(Date operatedate) {
        this.operatedate = operatedate;
    }

    public String getOperatemark() {
        return operatemark;
    }

    public void setOperatemark(String operatemark) {
        this.operatemark = operatemark;
    }
}
