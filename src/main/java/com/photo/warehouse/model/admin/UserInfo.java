package com.photo.warehouse.model.admin;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/7.
 */
@Table(name = "usr_info")
public class UserInfo {
    //工号
    @Column(name = "vc_uid")
    private String vcUid;

    //登录密码
    @Column(name = "vc_pwd")
    private String vcPwd;

    //姓名
    @Column(name = "vc_name")
    private String vcName;

    //性别
    @Column(name = "c_sex")
    private String cSex;

    //单位部门
    @Column(name = "vc_dept")
    private String vcDept;

    //直线电话
    @Column(name = "vc_phonea")
    private String vcPhonea;

    //专网电话
    @Column(name = "vc_phoneb")
    private String vcPhoneb;

    public String getVcUid() {
        return vcUid;
    }

    public void setVcUid(String vcUid) {
        this.vcUid = vcUid;
    }

    public String getVcPwd() {
        return vcPwd;
    }

    public void setVcPwd(String vcPwd) {
        this.vcPwd = vcPwd;
    }

    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
    }

    public String getcSex() {
        return cSex;
    }

    public void setcSex(String cSex) {
        this.cSex = cSex;
    }

    public String getVcDept() {
        return vcDept;
    }

    public void setVcDept(String vcDept) {
        this.vcDept = vcDept;
    }

    public String getVcPhonea() {
        return vcPhonea;
    }

    public void setVcPhonea(String vcPhonea) {
        this.vcPhonea = vcPhonea;
    }

    public String getVcPhoneb() {
        return vcPhoneb;
    }

    public void setVcPhoneb(String vcPhoneb) {
        this.vcPhoneb = vcPhoneb;
    }
}
