package com.backbase.openbank.service.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SuppressWarnings("unused")
public class TransactionServiceWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{TransactionServiceWebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
