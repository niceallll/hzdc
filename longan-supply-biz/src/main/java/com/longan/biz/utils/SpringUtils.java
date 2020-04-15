package com.longan.biz.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	ContextUtils.setApplicationContext(arg0);
    }

    public static Object getBean(String name) throws BeansException {
	return ContextUtils.getBean(name);
    }

    static class ContextUtils {
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
	    ContextUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
	    return applicationContext;
	}

	public static Object getBean(String name) {
	    return getApplicationContext().getBean(name);
	}
    }
}
