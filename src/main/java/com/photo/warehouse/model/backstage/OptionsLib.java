package com.photo.warehouse.model.backstage;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/8.
 * 自定义选项
 */
@Table(name = "options_lib")
public class OptionsLib {

    //选项分类(1海关业务分类, 2拍摄地点, 3制服类型)
    @Column(name = "c_opti")
    private String cOpti;

    //选项描述
    @Column(name = "vc_describe")
    private String vcDescribe;

    @Column(name = "i_blank")
    private Integer iBlank;

    public String getcOpti() {
        if(cOpti == null){
            return cOpti;
        }
        return cOpti.trim();
    }

    public void setcOpti(String cOpti) {
        this.cOpti = cOpti;
    }

    public String getVcDescribe() {
        return vcDescribe;
    }

    public void setVcDescribe(String vcDescribe) {
        this.vcDescribe = vcDescribe;
    }

    public Integer getiBlank() {
        return iBlank;
    }

    public void setiBlank(Integer iBlank) {
        this.iBlank = iBlank;
    }
}
