package com.photo.warehouse.model.admin;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/8.
 */
@Table(name = "dept_info")
public class DeptInfo {

    //单位部门用户id
    @Column(name = "vc_uid")
    private String vcUid;

    //登录密码
    @Column(name = "vc_pwd")
    private String vcPwd;

    //负责人
    @Column(name = "vc_keyman")
    private String vcKeyman;

    //负责人联系电话
    @Column(name = "vc_keymanphone")
    private String vcKeymanphone;

    //管理员
    @Column(name = "vc_admin")
    private String vcAdmin;

    //管理员联系电话
    @Column(name = "vc_adminphone")
    private String vcAdminphone;

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

    public String getVcKeyman() {
        return vcKeyman;
    }

    public void setVcKeyman(String vcKeyman) {
        this.vcKeyman = vcKeyman;
    }

    public String getVcKeymanphone() {
        return vcKeymanphone;
    }

    public void setVcKeymanphone(String vcKeymanphone) {
        this.vcKeymanphone = vcKeymanphone;
    }

    public String getVcAdmin() {
        return vcAdmin;
    }

    public void setVcAdmin(String vcAdmin) {
        this.vcAdmin = vcAdmin;
    }

    public String getVcAdminphone() {
        return vcAdminphone;
    }

    public void setVcAdminphone(String vcAdminphone) {
        this.vcAdminphone = vcAdminphone;
    }
}
