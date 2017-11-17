package com.xdchen.netty.client.service;

import com.xdchen.netty.client.exception.ApiException;
import com.xdchen.netty.client.utils.DaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseService<T, K> implements IBaseService<K> {
    @Autowired
    protected Mapper<K> mapper;

    @Autowired
    protected DaoUtil daoUtil;

    protected Logger logger = null;
    private Class<T> clazzT;


    public BaseService() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        clazzT =  (Class<T>) type.getActualTypeArguments()[0];
        logger = LoggerFactory.getLogger(clazzT);
    }

    protected void throwApiEx(int errCode) {
        throw new ApiException(errCode);
    }

    protected void throwApiEx(int errCode, String errMsg) {
        throw new ApiException(errCode, errMsg, null);
    }

    protected void throwApiEx(int errCode, String errMsg, Object data) {
        throw new ApiException(errCode, errMsg, data);
    }

    /**
     * 当Service遇到强制需要捕获的异常,或者自己想捕获的时候使用
     *
     * @param e
     * @param msg
     * @author xdchen
     */
    protected void throwRuntimeEx(Exception e, String msg) {
        String err = e.getMessage();
        msg = msg == null ? "" : msg;
        throw new RuntimeException("Controller运行时异常:" + msg + "|" + err, e);
    }

    @Override
    public K selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(K entity) {
        return mapper.insert(entity);
    }

    @Override
    public int saveNotNull(K entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int updateAll(K entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(K entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<K> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int countByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public Mapper<K> getMapper() {
        return mapper;
    }
}
