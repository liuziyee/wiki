package com.dorohedoro.wiki.bean;

import lombok.Data;

import java.util.List;

@Data
public class Category extends PageBean {
    private Long id;

    private Long parentId;

    private String name;

    private Integer sortFlag;

    private Long deleted;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(Integer sortFlag) {
        this.sortFlag = sortFlag;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }
}