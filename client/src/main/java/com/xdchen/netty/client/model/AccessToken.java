package com.xdchen.netty.client.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "access_token")
public class AccessToken {
    @Id
    @Column(name = "token_id")
    private String tokenId;

    private String username;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "token_expired_seconds")
    private Integer tokenExpiredSeconds;

    @Column(name = "refresh_token_expired_seconds")
    private Integer refreshTokenExpiredSeconds;

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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return authentication_id
     */
    public String getAuthenticationId() {
        return authenticationId;
    }

    /**
     * @param authenticationId
     */
    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    /**
     * @return refresh_token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return token_type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenType
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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
     * @return refresh_token_expired_seconds
     */
    public Integer getRefreshTokenExpiredSeconds() {
        return refreshTokenExpiredSeconds;
    }

    /**
     * @param refreshTokenExpiredSeconds
     */
    public void setRefreshTokenExpiredSeconds(Integer refreshTokenExpiredSeconds) {
        this.refreshTokenExpiredSeconds = refreshTokenExpiredSeconds;
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