/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

import com.hotwire.test.common.AppiumDriverFactory;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Utils;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import io.appium.java_client.AppiumDriver;
import org.junit.Test;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Spring context configuration integrity test.
 */
public class ContextConfigurationTest {

    private GenericXmlApplicationContext createContext(String profile, String springProfile) {
        System.setProperty("mobileOS", profile);
        System.setProperty("app_dir", "");
        System.setProperty("app_name", "");
        System.setProperty("device_name", "");
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles(springProfile);
        context.load("classpath*:cucumber/runtime/java/spring/cucumber-glue.xml");
        context.load("classpath:cucumber.xml");
        context.registerBeanDefinition("appiumDriverFactory",
                BeanDefinitionBuilder.genericBeanDefinition(NullAppiumDriverFactory.class).getBeanDefinition());
        context.refresh();
        context.start();
        return context;
    }

    private void testContextConfiguration(String profile, String springProfile) {
        GenericXmlApplicationContext context = createContext(profile, springProfile);
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        Collection<Class<?>> descendants = classFinder.getDescendants(Object.class, "com.hotwire.test.steps");

        List<Throwable> throwables = new ArrayList<>();
        for (Class<?> clazz : descendants) {
            if (Utils.isInstantiable(clazz) & !clazz.isEnum()) {
                context.registerBeanDefinition(clazz.getName(),
                        BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition());
                try {
                    factory.getBean(clazz.getName());
                }
                catch (Throwable t) {
                    while (t.getCause() != null) {
                        t = t.getCause();
                    }
                    System.err.println(t.getMessage());
                    throwables.add(t);
                }
            }
        }
        assertThat(throwables)
                .as("List of throwables should be empty!")
                .isEmpty();
    }

    @Test
    public void testIPadContextConfiguration() {
        testContextConfiguration("ios", "iPad");
    }

    @Test
    public void testIPhoneContextConfiguration() {
        testContextConfiguration("ios", "iPhone");
    }

    /**
     * AppiumDriverFactory which always returns null.
     */
    private static class NullAppiumDriverFactory extends AppiumDriverFactory {

        @Override
        public AppiumDriver getAppiumDriver() {
            return null;
        }
    }
}
