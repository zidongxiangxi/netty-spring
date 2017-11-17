package com.xdchen.netty.model.db;

import java.util.Date;
import javax.persistence.*;

public class User {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private String userId;

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
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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