package org.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        String[] beanDefinitionNames = factory.getBeanDefinitionNames();
        for(String name:beanDefinitionNames){
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);

        }
    }
}
