package com.xdchen.netty.client.utils;

import com.xdchen.netty.client.dao.AccessTokenMapper;
import com.xdchen.netty.client.dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoUtil {
    @Autowired
    public AccessTokenMapper accessTokenMapper;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    private SqlSession sqlSession;

    // 获取通过这个类动态获取,比写上n多的mapper,在spring-test的时候会加载快点(不需要启动的时候全部创建)
    public <T> T getMapper(Class<T> mapper) {
        return sqlSession.getMapper(mapper);
    }
}
