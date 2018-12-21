package com.photo.warehouse.model.photo.response;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/11.
 */
public class PicInfoResponse {
    //图片id
    private String vcPid;

    //用户id
    private String vcUid;

    //图片分类(0收藏夹,1下载成功)
    private String cKind;

    //加入时间
    private Date dtStamp;

    private String vcName;

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

    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
    }
}
