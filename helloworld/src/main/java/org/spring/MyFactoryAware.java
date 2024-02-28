package org.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class MyFactoryAware implements BeanFactoryAware {

    private BeanFactory factory;

    /**
     * 通过实现aware接口来获取spring相关属性
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }
}
