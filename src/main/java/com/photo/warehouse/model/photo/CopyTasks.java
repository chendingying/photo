package com.photo.warehouse.model.photo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/8.
 * 图片新闻复制任务
 */
@Table(name = "copy_tasks")
public class CopyTasks {
    @Id
    private Integer id;

    //图片id
    @Column(name = "Pid")
    private String Pid;

    @Column(name = "Title")
    private String Title;

    @Column(name = "SmallPicture")
    private String SmallPicture;

    @Column(name = "BigPicture")
    private String BigPicture;

    @Column(name = "ChuShiID")
    private Integer ChuShiID;

    @Column(name = "Display")
    private Integer Display;

    @Column(name = "Type")
    private String Type;

    //处室名称
    @Column(name = "ChuShiName")
    private String ChuShiName;

    //源文件
    @Column(name = "src_file1")
    private String srcFile1;

    //源文件
    @Column(name = "src_file2")
    private String srcFile2;

    //目标文件
    @Column(name = "dst_file1")
    private String dstFile1;

    //目标文件
    @Column(name = "dst_file2")
    private String dstFile2;

    //任务创建时间
    @Column(name = "created_at")
    private Date createdAt;

    //任务完成时间
    @Column(name = "finished_at")
    private Date finishedAt;

    //0待处理,1已处理
    @Column(name = "status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSmallPicture() {
        return SmallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        SmallPicture = smallPicture;
    }

    public String getBigPicture() {
        return BigPicture;
    }

    public void setBigPicture(String bigPicture) {
        BigPicture = bigPicture;
    }

    public Integer getChuShiID() {
        return ChuShiID;
    }

    public void setChuShiID(Integer chuShiID) {
        ChuShiID = chuShiID;
    }

    public Integer getDisplay() {
        return Display;
    }

    public void setDisplay(Integer display) {
        Display = display;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getChuShiName() {
        return ChuShiName;
    }

    public void setChuShiName(String chuShiName) {
        ChuShiName = chuShiName;
    }

    public String getSrcFile1() {
        return srcFile1;
    }

    public void setSrcFile1(String srcFile1) {
        this.srcFile1 = srcFile1;
    }

    public String getSrcFile2() {
        return srcFile2;
    }

    public void setSrcFile2(String srcFile2) {
        this.srcFile2 = srcFile2;
    }

    public String getDstFile1() {
        return dstFile1;
    }

    public void setDstFile1(String dstFile1) {
        this.dstFile1 = dstFile1;
    }

    public String getDstFile2() {
        return dstFile2;
    }

    public void setDstFile2(String dstFile2) {
        this.dstFile2 = dstFile2;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
