package com.xdchen.netty.model.db;

import java.util.Date;
import javax.persistence.*;

@Table(name = "access_token")
public class AccessToken {
    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "token_expired_seconds")
    private Integer tokenExpiredSeconds;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return token_id
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * @param tokenId
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return token_expired_seconds
     */
    public Integer getTokenExpiredSeconds() {
        return tokenExpiredSeconds;
    }

    /**
     * @param tokenExpiredSeconds
     */
    public void setTokenExpiredSeconds(Integer tokenExpiredSeconds) {
        this.tokenExpiredSeconds = tokenExpiredSeconds;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}