package com.xdchen.netty.client.service.user;

import com.xdchen.netty.client.model.User;
import com.xdchen.netty.client.service.IBaseService;

public interface IUserService extends IBaseService<User> {
    User findByUsername(String username);
    String login(String username, String password);
}
