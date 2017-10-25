package com.websoul.qatools.helpers.utils;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.logging.Logger;
import java.lang.reflect.Method;

@Service
public class TestUtils {

    private static final Logger logger = Logger.getLogger("TestUtils");

    private static Method getMethod(Class<?> tClass, String methodName) {
        try {
            return tClass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runRule(TestRule rule, Object target, String methodName) {
        Class<?> clazz = target.getClass();
        Method method = TestUtils.getMethod(clazz, methodName);
        Description description = Description.createTestDescription(clazz, method.getName(), method.getDeclaredAnnotations());
        try {
            InvokeMethod invokeMethod = new InvokeMethod(new FrameworkMethod(method), target);
            rule.apply(invokeMethod, description).evaluate();
        } catch (Throwable throwable) {
            logger.warning(Arrays.toString(throwable.getStackTrace()));
        }
    }
}