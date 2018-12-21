package com.photo.warehouse.model.backstage;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/7.
 */
@Table(name = "photo_info")
public class PhotoInfo {
    @Id
    private Integer id;
    private String title;
    private String content;
    private byte[] resource;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getResource() {
        return resource;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
