package org.myspring.beans.factory.annotation;

import org.myspring.beans.BeansException;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.AutowiredCapableBeanFactory;
import org.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.myspring.core.annotation.AnnotationUtils;
import org.myspring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午9:09
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowiredCapableBeanFactory beanFactory;

    private String requiredParameterName = "required";

    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes =
            new LinkedHashSet<>();

    public AutowiredAnnotationProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        List<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;

        do {
            List<InjectionElement> currElements = new LinkedList<>();
            for (Field field : targetClass.getDeclaredFields()) {
                Annotation ann = findAutowiredAnnotation(field);
                if(ann != null) {
                    if(Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }
            for (Method method : targetClass.getDeclaredMethods()) {
                // TODO 处理方法注入
            }
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        } while(targetClass != null && targetClass != Object.class);
        return new InjectionMetadata(clazz, elements);
    }

    public void setBeanFactory(AutowiredCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation annotation = AnnotationUtils.getAnnotation(ao, type);
            if(annotation != null) {
                return annotation;
            }
        }
        return null;
    }

    protected boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        }
        catch (Exception ex) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public void postProcessorPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed");
        }
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
