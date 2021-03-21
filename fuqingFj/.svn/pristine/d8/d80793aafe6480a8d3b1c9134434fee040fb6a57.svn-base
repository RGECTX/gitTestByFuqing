package com.greathack.homlin.serviceinterface.system;

import com.greathack.homlin.exception.ServiceException;

import java.util.List;

/**
 * @author zhanghb
 */
public interface IBaseService<T, PK> {

    T add(T instance) throws ServiceException;

    void delete(PK id);

    void update(T instance);

    T get(PK id);

    List<T> findByExample(T instance);

    List<T> findAll();

}
