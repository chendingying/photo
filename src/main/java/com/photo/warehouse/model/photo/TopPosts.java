package com.photo.warehouse.model.photo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/8.
 * 报送排行
 */
@Table(name = "top_posts")
public class TopPosts {

    //报送者id
    @Column(name = "vc_id")
    private String vcId;

    //1月份次数
    @Column(name = "i_m1")
    private Integer iM1;

    //2月份次数
    @Column(name = "i_m2")
    private Integer iM2;

    //3月份次数
    @Column(name = "i_m3")
    private Integer iM3;

    //4月份次数
    @Column(name = "i_m4")
    private Integer iM4;

    //5月份次数
    @Column(name = "i_m5")
    private Integer iM5;

    //6月份次数
    @Column(name = "i_m6")
    private Integer iM6;

    //7月份次数
    @Column(name = "i_m7")
    private Integer iM7;

    //8月份次数
    @Column(name = "i_m8")
    private Integer iM8;

    //9月份次数
    @Column(name = "i_m9")
    private Integer iM9;

    //10月份次数
    @Column(name = "i_m10")
    private Integer iM10;

    //11月份次数
    @Column(name = "i_m11")
    private Integer iM11;

    //12月份次数
    @Column(name = "i_m12")
    private Integer iM12;

    //全年汇总
    @Column(name = "i_m13")
    private Integer iM13;

    public String getVcId() {
        return vcId;
    }

    public void setVcId(String vcId) {
        this.vcId = vcId;
    }

    public Integer getiM1() {
        return iM1;
    }

    public void setiM1(Integer iM1) {
        this.iM1 = iM1;
    }

    public Integer getiM2() {
        return iM2;
    }

    public void setiM2(Integer iM2) {
        this.iM2 = iM2;
    }

    public Integer getiM3() {
        return iM3;
    }

    public void setiM3(Integer iM3) {
        this.iM3 = iM3;
    }

    public Integer getiM4() {
        return iM4;
    }

    public void setiM4(Integer iM4) {
        this.iM4 = iM4;
    }

    public Integer getiM5() {
        return iM5;
    }

    public void setiM5(Integer iM5) {
        this.iM5 = iM5;
    }

    public Integer getiM6() {
        return iM6;
    }

    public void setiM6(Integer iM6) {
        this.iM6 = iM6;
    }

    public Integer getiM7() {
        return iM7;
    }

    public void setiM7(Integer iM7) {
        this.iM7 = iM7;
    }

    public Integer getiM8() {
        return iM8;
    }

    public void setiM8(Integer iM8) {
        this.iM8 = iM8;
    }

    public Integer getiM9() {
        return iM9;
    }

    public void setiM9(Integer iM9) {
        this.iM9 = iM9;
    }

    public Integer getiM10() {
        return iM10;
    }

    public void setiM10(Integer iM10) {
        this.iM10 = iM10;
    }

    public Integer getiM11() {
        return iM11;
    }

    public void setiM11(Integer iM11) {
        this.iM11 = iM11;
    }

    public Integer getiM12() {
        return iM12;
    }

    public void setiM12(Integer iM12) {
        this.iM12 = iM12;
    }

    public Integer getiM13() {
        return iM13;
    }

    public void setiM13(Integer iM13) {
        this.iM13 = iM13;
    }
}
