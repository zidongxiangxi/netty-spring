package com.xdchen.netty.client.service.user;

import com.xdchen.netty.client.model.AccessToken;
import com.xdchen.netty.client.model.User;
import com.xdchen.netty.client.model.common.ApiRet;
import com.xdchen.netty.client.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService<UserService, User> implements IUserService {
    @Override
    public User findByUsername(String username) {
        User record = new User();
        record.setUsername(username);
        List<User> users = mapper.select(record);
        if (users.isEmpty()) {
            this.throwApiEx(ApiRet.ACCESS_DENIED_CODE);
        }
        return users.get(0);
    }

    @Override
    public String login(String username, String password) {
        User record = new User();
        record.setUsername(username);
        record.setPassword(password);
        List<User> users = mapper.select(record);
        if (users.isEmpty()) {
            this.throwApiEx(ApiRet.LOGIN_FAIL);
        }
        String accessTokenStr = this.generateAccessToken();
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(users.get(0).getUserId());
        accessToken.setTokenId(accessTokenStr);
        accessToken.setTokenExpiredSeconds(3600);
        accessToken.setCreateTime(new Date());
        daoUtil.accessTokenMapper.insert(accessToken);
        return accessTokenStr;
    }

    private String generateAccessToken() {
        String temp = UUID.randomUUID().toString();
        return UUID.fromString(UUID.nameUUIDFromBytes(temp.getBytes()).toString()).toString();
    }
}
