package com.xdchen.netty.client.model;

import java.util.Date;
import javax.persistence.*;

public class User {
    /**
     * 用户id
     */
    @Id
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 帐号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String salt;

    /**
     * 真实姓名
     */
    @Column(name = "true_name")
    private String trueName;

    private String linkman;

    @Column(name = "contact_info")
    private String contactInfo;

    /**
     * IC系统SRC标识
     */
    @Column(name = "ic_src")
    private String icSrc;

    private String memo;

    /**
     * 帐号状态，-1-删除，0-冻结，1-激活
     */
    private Short status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @Column(name = "last_mod_time")
    private Date lastModTime;

    /**
     * 获取用户id
     *
     * @return channel_id - 用户id
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置用户id
     *
     * @param channelId 用户id
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取帐号
     *
     * @return username - 帐号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置帐号
     *
     * @param username 帐号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取真实姓名
     *
     * @return true_name - 真实姓名
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * 设置真实姓名
     *
     * @param trueName 真实姓名
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    /**
     * @return linkman
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * @param linkman
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * @return contact_info
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * @param contactInfo
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * 获取IC系统SRC标识
     *
     * @return ic_src - IC系统SRC标识
     */
    public String getIcSrc() {
        return icSrc;
    }

    /**
     * 设置IC系统SRC标识
     *
     * @param icSrc IC系统SRC标识
     */
    public void setIcSrc(String icSrc) {
        this.icSrc = icSrc;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取帐号状态，-1-删除，0-冻结，1-激活
     *
     * @return status - 帐号状态，-1-删除，0-冻结，1-激活
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置帐号状态，-1-删除，0-冻结，1-激活
     *
     * @param status 帐号状态，-1-删除，0-冻结，1-激活
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后修改时间
     *
     * @return last_mod_time - 最后修改时间
     */
    public Date getLastModTime() {
        return lastModTime;
    }

    /**
     * 设置最后修改时间
     *
     * @param lastModTime 最后修改时间
     */
    public void setLastModTime(Date lastModTime) {
        this.lastModTime = lastModTime;
    }
}