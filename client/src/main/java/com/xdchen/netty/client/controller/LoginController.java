package com.xdchen.netty.client.controller;

import com.xdchen.netty.client.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends BaseController<LoginController> {
    @Autowired
    private IUserService userService;

    @RequestMapping("user/login")
    public Object login(@RequestParam String username, @RequestParam String password) {
        return this.getAjaxRet(userService.login(username, password));
    }
}
