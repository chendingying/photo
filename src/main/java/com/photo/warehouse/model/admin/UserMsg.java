package com.photo.warehouse.model.admin;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/8.
 */
@Table(name = "usr_msg")
public class UserMsg {

    //消息序号
    @Column(name = "vc_msgid")
    private String vcMsgid;

    //发件人id
    @Column(name = "vcSid")
    private String vc_sid;

    //收件人id
    @Column(name = "vcRid")
    private String vc_rid;

    //消息标题
    @Column(name = "vc_title")
    private String vcTitle;

    //短消息
    @Column(name = "vc_msg")
    private String vcMsg;

    //发出时间
    @Column(name = "dt_sedtime")
    private Date dtSedtime;

    //ip地址
    @Column(name = "vc_ip")
    private String vcIp;

    //查看标识(0未阅,1已阅)
    @Column(name = "c_read")
    private String cRead;

    public String getVcMsgid() {
        return vcMsgid;
    }

    public void setVcMsgid(String vcMsgid) {
        this.vcMsgid = vcMsgid;
    }

    public String getVc_sid() {
        return vc_sid;
    }

    public void setVc_sid(String vc_sid) {
        this.vc_sid = vc_sid;
    }

    public String getVc_rid() {
        return vc_rid;
    }

    public void setVc_rid(String vc_rid) {
        this.vc_rid = vc_rid;
    }

    public String getVcTitle() {
        return vcTitle;
    }

    public void setVcTitle(String vcTitle) {
        this.vcTitle = vcTitle;
    }

    public String getVcMsg() {
        return vcMsg;
    }

    public void setVcMsg(String vcMsg) {
        this.vcMsg = vcMsg;
    }

    public Date getDtSedtime() {
        return dtSedtime;
    }

    public void setDtSedtime(Date dtSedtime) {
        this.dtSedtime = dtSedtime;
    }

    public String getVcIp() {
        return vcIp;
    }

    public void setVcIp(String vcIp) {
        this.vcIp = vcIp;
    }

    public String getcRead() {
        return cRead;
    }

    public void setcRead(String cRead) {
        this.cRead = cRead;
    }
}
