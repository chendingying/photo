package com.photo.warehouse.model.admin;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by CDZ on 2018/12/8.
 * 我的联系人
 */
@Table(name = "contact")
public class Contact {

    @Column(name = "vc_uid")
    private String vcUid;

    @Column(name = "vc_sid")
    private String vcSid;

    @Column(name = "dt_addtime")
    private Date dtAddtime;
}
