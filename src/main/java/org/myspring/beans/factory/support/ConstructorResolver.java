package org.myspring.beans.factory.support;

import org.apache.log4j.Logger;
import org.myspring.beans.BeanDefinition;
import org.myspring.beans.ConstructorArgument;
import org.myspring.beans.SimpleTypeConverter;
import org.myspring.beans.TypeConverter;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author yujiangtao
 * @date 2020/8/20 下午2:24
 */
public class ConstructorResolver {

    private ConfigurableBeanFactory factory;

    protected final Logger logger = Logger.getLogger(getClass());

    public ConstructorResolver(ConfigurableBeanFactory factory) {
        this.factory = factory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass;
        try {
            beanClass = factory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getId(), "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(factory);
        ConstructorArgument constructorArgument = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if(constructorArgument.getArgumentCount() != parameterTypes.length) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];

            boolean result = this.valueMatchTypes(parameterTypes, constructorArgument.getArgumentValues(),
                    argsToUse, valueResolver, typeConverter);

            if(result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if(constructorToUse == null) {
            throw new BeanCreationException(bd.getId(), "can't find apporiate constructor.");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getId(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valueMatchTypes(Class<?>[] parameterTypes, List<ConstructorArgument.ValueHolder> argumentValues,
                                    Object[] argsToUse, BeanDefinitionValueResolver valueResolver, TypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            // 获取参数的值，可能是 TypedStringValue，也可能是 RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try {
                // 获得真正的值
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                // 如果参数类型是 int，但是值是字符串，例如 “3”，还需要转型
                // 如果转型失败，则抛出异常。说明这个构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                // 转型成功，记录下来
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }
}
