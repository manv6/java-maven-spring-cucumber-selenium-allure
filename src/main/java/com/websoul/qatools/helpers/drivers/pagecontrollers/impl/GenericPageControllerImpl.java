package com.websoul.qatools.helpers.drivers.pagecontrollers.impl;

import com.websoul.qatools.helpers.annotations.Page;
import com.websoul.qatools.helpers.context.cache.RuntimeCachedData;
import com.websoul.qatools.helpers.drivers.browsers.BrowserDriver;
import com.websoul.qatools.helpers.drivers.pagecontrollers.PageController;
import com.websoul.qatools.ui.AmazonHomePage;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Set;


/**
 * Page Controller for pageObjects
 */
@Controller("genericPageController")
public class GenericPageControllerImpl implements PageController, ApplicationContextAware {

    private final Logger slf4jLogger = LoggerFactory.getLogger(GenericPageControllerImpl.class);

    @Autowired
    BrowserDriver browserDriver;
    @Autowired
    RuntimeCachedData runtimeCachedData;
    @Autowired
    AmazonHomePage amazonHomePage;


    final private String WHITELIST_PACKAGE = "com.websoul.qatools.ui";
    private static ApplicationContext applicationContext;

    /**
     * Initializes elements of google home search pageObject using Selenium's PageFactory
     */


    public void initializeElementsForAllPages() throws IOException {

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Page.class));

        Set<BeanDefinition> beans = provider.findCandidateComponents(WHITELIST_PACKAGE);
        for (BeanDefinition bd : beans) {
            AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
            registry.registerBeanDefinition(bd.getBeanClassName(), BeanDefinitionBuilder.genericBeanDefinition(bd.getBeanClassName()).getBeanDefinition());
            PageFactory.initElements(browserDriver.getCurrentDriver(), applicationContext.getBean(bd.getBeanClassName()).getClass());
            slf4jLogger.info("Added to Page Factory: " + bd.getBeanClassName());
        }

//        final ScanResult scanResult = new FastClasspathScanner(WHITELIST_PACKAGE).scan();
//        for (String s : scanResult.getNamesOfClassesWithAnnotation(Page.class)) {
//            try {
//
//                Object clazz = Class.forName(s).newInstance();
//                removeExistingAndAddNewBean(s, clazz.getClass());
//                PageFactory.initElements(browserDriver.getCurrentDriver(), clazz);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            }
//        }

        //Old Way
//        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonHomePage);
//        slf4jLogger.info("Page Factory: AmazonHomePage object initialized successfully");
//        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonLoginPage);
//        slf4jLogger.info("Page Factory: AmazonLoginPage object initialized successfully");
//        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonProductPage);
//        slf4jLogger.info("Page Factory: AmazonProductPage object initialized successfully");
//        PageFactory.initElements(browserDriver.getCurrentDriver(), amazonSearchResultsPage);
//        slf4jLogger.info("Page Factory: AmazonSearchResultsPage object initialized successfully");
    }


    /**
     * @param beanId
     * @param beanClass
     * @param <T>
     */
    public static <T> void removeExistingAndAddNewBean(String beanId, Class<T> beanClass) {

        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        registry.removeBeanDefinition(beanId);

        //create newBeanObj through GenericBeanDefinition

        registry.registerBeanDefinition(beanId, BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition());

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
