package com.photo.warehouse.model.photo;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/8.
 * 用户图片资料
 */
@Table(name = "pic_info")
public class PicInfo  {

    //图片id
    @Column(name = "vc_pid")
    private String vcPid;

    //用户id
    @Column(name = "vc_uid")
    private String vcUid;

    //图片分类(0收藏夹,1下载成功)
    @Column(name = "c_kind")
    private String cKind;

    //加入时间
    @Column(name = "dt_stamp")
    private Date dtStamp;

    @Transient
    private String vcName;

    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
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

    public String getcKind() {
        return cKind;
    }

    public void setcKind(String cKind) {
        this.cKind = cKind;
    }

    public Date getDtStamp() {
        return dtStamp;
    }

    public void setDtStamp(Date dtStamp) {
        this.dtStamp = dtStamp;
    }
}
