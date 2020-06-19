package com.jeesite.modules.history.form;



import com.jeesite.modules.common.ApplicationContextProvider;
import com.jeesite.modules.history.entity.HistoryForm;
import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.form.constructor.FormConstructor;
//import org.reflections.Reflections;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class HistoryFormConstructor {


    private static Class<?> superStrategy = FormConstructor.class;//接口类class 用于过滤 可以不要

    private static Map<String,FormConstructor> eleStrategyMap = new HashMap<>();

    private static ClassLoader classLoader = HistoryFormConstructor.class.getClassLoader();//默认使用的类加载器

    private static final String STARATEGY_PATH = "com.jeesite.modules.history";//需要扫描的策略包名

    static {
        addClass();
    }


    public Map<String,FormConstructor> getFormConstructor(){
        return eleStrategyMap;
    }


    public HistoryForm build(HtHistory htHistory){
        if(htHistory==null)
            return null;
        FormConstructor formConstructor = eleStrategyMap.get(htHistory.getFormType());
        FormData formData = null;
        if(formConstructor !=null)
            formData = formConstructor.build(htHistory);
        return new HistoryForm(htHistory,formData);
    }


    /**
     * 获取包下所有实现了superStrategy的类并加入list
     */

    private static void addClass(){
        init(STARATEGY_PATH);
    }


    public static void init(String packageName) {
        Reflections f = new Reflections(packageName);
        Set<Class<?>> set = f.getTypesAnnotatedWith(FormConfig.class);
        for (Class<?> clazz : set) {
            FormConfig formConfig = clazz.getAnnotation(FormConfig.class);
            if(superStrategy.isAssignableFrom(clazz))
                eleStrategyMap.put(formConfig.value(), ApplicationContextProvider.getBean((Class<FormConstructor>) clazz));

        }
    }
}
