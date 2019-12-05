package com.xiaodou.ms.web.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UTFStringHttpMessage implements BeanPostProcessor {
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof StringHttpMessageConverter) {
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(new MediaType("text", "plain", Charset.forName("UTF-8")));
			types.add(new MediaType("application", "json", Charset
					.forName("UTF-8")));
			((StringHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}
