package com.photo.warehouse.model.admin;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/7.
 */
@Table(name = "usr_role")
public class UserRole {

    //用户id
    @Column(name = "vc_uid")
    private String vcUid;

    //角色(0管理员,1单位用户,2个人用户)
    @Column(name = "c_role")
    private String cRole;

    //角色菜单
    @Column(name = "vcMenu")
    private String vc_menu;

    public String getVcUid() {
        return vcUid;
    }

    public void setVcUid(String vcUid) {
        this.vcUid = vcUid;
    }

    public String getcRole() {
        return cRole;
    }

    public void setcRole(String cRole) {
        this.cRole = cRole;
    }

    public String getVc_menu() {
        return vc_menu;
    }

    public void setVc_menu(String vc_menu) {
        this.vc_menu = vc_menu;
    }
}
