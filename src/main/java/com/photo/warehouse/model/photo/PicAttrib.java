package com.photo.warehouse.model.photo;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 * 图片基本属性
 */
@Table(name = "pic_attrib")
public class PicAttrib {

    //图片id
    @Id
    @Column(name = "vc_pid")
    private String vcPid;

    //原图位置
    @Column(name = "vc_upload")
    private String vcUpload;


    //缩略图位置
    @Column(name = "vc_thumb")
    private String vcThumb;

    //图片标题
    @Column(name = "vc_name")
    private String vcName;

    //类型(0海关,1其它)
    @Column(name = "c_type")
    private String cType;

    //状态(0待审,1采用,2拒绝)
    @Column(name = "c_status")
    private String cStatus;

    //审核意见
    @Column(name = "vc_reason")
    private String vcReason;

    //作者
    @Column(name = "vc_author")
    private String vcAuthor;

    //报送单位
    @Column(name = "vc_dept")
    private String vcDept;

    //报送人员
    @Column(name = "vc_post")
    private String vcPost;

    //原图尺寸
    @Column(name = "vc_size")
    private String vcSize;

    //原图大小
    @Column(name = "vc_bytes")
    private String vcBytes;

    //关键词
    @Column(name = "vc_keyword")
    private String vcKeyword;

    //报送时间
    @Column(name = "dt_postime")
    private Date dtPostime;

    //拍摄地点
    @Column(name = "vc_phoposi")
    private String vcPhoposi;

    //拍摄时间
    @Column(name = "vc_photime")
    private String vcPhotime;

    //拍摄人物
    @Column(name = "vc_creator")
    private String vcCreator;

    //制服类型
    @Column(name = "vc_togae")
    private String vcTogae;

    //业务分类
    @Column(name = "vc_kind")
    private String vcKind;

    //拍摄机器
    @Column(name = "vc_camera")
    private String vcCamera;

    //使用镜头
    @Column(name = "vc_glass")
    private String vcGlass;

    //原图文件格式
    @Column(name = "vc_format")
    private String vcFormat;

    //图片说明
    @Column(name = "vc_memo")
    private String vcMemo;

    //入库时间
    @Column(name = "dt_stamp")
    private Date dtStamp;

    //序列号
    @Column(name = "vc_seqno")
    private String vcSeqno;

    //点击次数
    @Column(name = "i_click")
    private Integer iClick;

    //备份标识
    @Column(name = "c_isbackup")
    private String cIsbackup;

    //组图标识(0单图,1组图)
    @Column(name = "c_group")
    private String cGroup;

    //组图默认图(0子图,1默认图)
    @Column(name = "c_main")
    private String cMain;

    //组图id(guid)
    @Column(name = "vc_groupid")
    private String vcGroupid;

    //置顶（0：不，1：是）
    @Column(name = "pic_stick")
    private Integer picStick;

    //置顶创建时间
    @Column(name = "pic_stick_create_time")
    private Date picStickCreateTime;

    //置顶天数
    @Column(name = "pic_stick_time")
    private Integer picStickTime;

    //置顶结束时间
    @Column(name = "pic_stick_end_time")
    private Date picStickEndTime;

    //组图所有PId
    @Transient
    private List<PicAttrib> pidList;

    @Transient
    private Date dtPostimeBegin;

    @Transient
    private Date dtPostimeEnd;

    @Transient
    private Date vcPhotimeBegin;

    @Transient
    private Date vcPhotimeEnd;

    public Date getDtPostimeBegin() {
        return dtPostimeBegin;
    }

    public void setDtPostimeBegin(Date dtPostimeBegin) {
        this.dtPostimeBegin = dtPostimeBegin;
    }

    public Date getDtPostimeEnd() {
        return dtPostimeEnd;
    }

    public void setDtPostimeEnd(Date dtPostimeEnd) {
        this.dtPostimeEnd = dtPostimeEnd;
    }

    public Date getVcPhotimeBegin() {
        return vcPhotimeBegin;
    }

    public void setVcPhotimeBegin(Date vcPhotimeBegin) {
        this.vcPhotimeBegin = vcPhotimeBegin;
    }

    public Date getVcPhotimeEnd() {
        return vcPhotimeEnd;
    }

    public void setVcPhotimeEnd(Date vcPhotimeEnd) {
        this.vcPhotimeEnd = vcPhotimeEnd;
    }

    public Date getPicStickEndTime() {
        return picStickEndTime;
    }

    public void setPicStickEndTime(Date picStickEndTime) {
        this.picStickEndTime = picStickEndTime;
    }

    public List<PicAttrib> getPidList() {
        return pidList;
    }

    public void setPidList(List<PicAttrib> pidList) {
        this.pidList = pidList;
    }

    public Date getPicStickCreateTime() {
        return picStickCreateTime;
    }

    public void setPicStickCreateTime(Date picStickCreateTime) {
        this.picStickCreateTime = picStickCreateTime;
    }

    public Integer getPicStickTime() {
        return picStickTime;
    }

    public void setPicStickTime(Integer picStickTime) {
        this.picStickTime = picStickTime;
    }

    public Integer getPicStick() {
        return picStick;
    }

    public void setPicStick(Integer picStick) {
        this.picStick = picStick;
    }

    public String getVcPid() {
        return vcPid;
    }

    public void setVcPid(String vcPid) {
        this.vcPid = vcPid;
    }

    public String getVcUpload() {
        return vcUpload;
    }

    public void setVcUpload(String vcUpload) {
        this.vcUpload = vcUpload;
    }

    public String getVcThumb() {
        return vcThumb;
    }

    public void setVcThumb(String vcThumb) {
        this.vcThumb = vcThumb;
    }

    public String getVcName() {
        return vcName;
    }

    public void setVcName(String vcName) {
        this.vcName = vcName;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getVcReason() {
        return vcReason;
    }

    public void setVcReason(String vcReason) {
        this.vcReason = vcReason;
    }

    public String getVcAuthor() {
        return vcAuthor;
    }

    public void setVcAuthor(String vcAuthor) {
        this.vcAuthor = vcAuthor;
    }

    public String getVcDept() {
        return vcDept;
    }

    public void setVcDept(String vcDept) {
        this.vcDept = vcDept;
    }

    public String getVcPost() {
        return vcPost;
    }

    public void setVcPost(String vcPost) {
        this.vcPost = vcPost;
    }

    public String getVcSize() {
        return vcSize;
    }

    public void setVcSize(String vcSize) {
        this.vcSize = vcSize;
    }

    public String getVcBytes() {
        return vcBytes;
    }

    public void setVcBytes(String vcBytes) {
        this.vcBytes = vcBytes;
    }

    public String getVcKeyword() {
        return vcKeyword;
    }

    public void setVcKeyword(String vcKeyword) {
        this.vcKeyword = vcKeyword;
    }

    public Date getDtPostime() {
        return dtPostime;
    }

    public void setDtPostime(Date dtPostime) {
        this.dtPostime = dtPostime;
    }

    public String getVcPhoposi() {
        return vcPhoposi;
    }

    public void setVcPhoposi(String vcPhoposi) {
        this.vcPhoposi = vcPhoposi;
    }

    public String getVcPhotime() {
        return vcPhotime;
    }

    public void setVcPhotime(String vcPhotime) {
        this.vcPhotime = vcPhotime;
    }

    public String getVcCreator() {
        return vcCreator;
    }

    public void setVcCreator(String vcCreator) {
        this.vcCreator = vcCreator;
    }

    public String getVcTogae() {
        return vcTogae;
    }

    public void setVcTogae(String vcTogae) {
        this.vcTogae = vcTogae;
    }

    public String getVcKind() {
        return vcKind;
    }

    public void setVcKind(String vcKind) {
        this.vcKind = vcKind;
    }

    public String getVcCamera() {
        return vcCamera;
    }

    public void setVcCamera(String vcCamera) {
        this.vcCamera = vcCamera;
    }

    public String getVcGlass() {
        return vcGlass;
    }

    public void setVcGlass(String vcGlass) {
        this.vcGlass = vcGlass;
    }

    public String getVcFormat() {
        return vcFormat;
    }

    public void setVcFormat(String vcFormat) {
        this.vcFormat = vcFormat;
    }

    public String getVcMemo() {
        return vcMemo;
    }

    public void setVcMemo(String vcMemo) {
        this.vcMemo = vcMemo;
    }

    public Date getDtStamp() {
        return dtStamp;
    }

    public void setDtStamp(Date dtStamp) {
        this.dtStamp = dtStamp;
    }

    public String getVcSeqno() {
        return vcSeqno;
    }

    public void setVcSeqno(String vcSeqno) {
        this.vcSeqno = vcSeqno;
    }

    public Integer getiClick() {
        return iClick;
    }

    public void setiClick(Integer iClick) {
        this.iClick = iClick;
    }

    public String getcIsbackup() {
        return cIsbackup;
    }

    public void setcIsbackup(String cIsbackup) {
        this.cIsbackup = cIsbackup;
    }

    public String getcGroup() {
        return cGroup;
    }

    public void setcGroup(String cGroup) {
        this.cGroup = cGroup;
    }

    public String getcMain() {
        return cMain;
    }

    public void setcMain(String cMain) {
        this.cMain = cMain;
    }

    public String getVcGroupid() {
        return vcGroupid;
    }
    public void setVcGroupid(String vcGroupid) {
        this.vcGroupid = vcGroupid;
    }


}
