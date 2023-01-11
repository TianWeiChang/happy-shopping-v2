package com.tian.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月10日 21:33
 */
public class SystemMessageDto implements Serializable {
    private Long id;

    private String title;

    private Date createTime;

    private Date updateTime;

    private Integer sendType;

    private Integer deleted;

    private String link;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SystemMessageDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sendType=" + sendType +
                ", deleted=" + deleted +
                ", link='" + link + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
