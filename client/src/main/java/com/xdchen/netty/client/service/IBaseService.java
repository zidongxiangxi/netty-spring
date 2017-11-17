package com.xdchen.netty.client.service;

import java.util.List;

public interface IBaseService<T> {
    T selectByKey(Object key);

    int save(T entity);

    int saveNotNull(T entity);

    int delete(Object key);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Object example);

    int countByExample(Object example);
}
