package com.photo.warehouse.model.backstage;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by CDZ on 2018/12/7.
 */
@Table(name = "photo_sort")
public class PhotoSort {
    @Id
    private Integer id;
    private String title;
    private Integer parentId;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
