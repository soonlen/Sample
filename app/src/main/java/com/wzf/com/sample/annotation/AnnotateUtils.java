package com.wzf.com.sample.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by soonlen on 2017/2/7.
 */

public class AnnotateUtils {

    private final static String SET_CONTENT_VIEW = "setContentView";
    private final static String FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    /**
     * 设置监听事件
     *
     * @param activity
     */
    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //获取方法上的注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //拿到注解上的注解
                EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
                if (eventBaseAnnotation != null) {
                    //取出设置监听器的名称，监听器的类型，调用的方法名
                    String listenerSetter = eventBaseAnnotation.listenerSetter();
                    Class<?> listenerType = eventBaseAnnotation.listenerType();
                    String methodName = eventBaseAnnotation.methodName();
                    try {
                        /*OnClick m = method.getAnnotation(OnClick.class);
                        int[] tids = m.value();
                        Log.e("annotation", "数组大小：" + tids.length);*///上面这个方法也可以直接获取
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        int[] ids = (int[]) aMethod.invoke(annotation, new  Object[]{});
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                        //遍历所有的view，设置事件
                        for (int viewId : ids) {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //注入contentView
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int contentViewResId = contentView.value();
            if (contentViewResId != -1) {
                try {
                    Method method = clazz.getMethod(SET_CONTENT_VIEW, int.class);
                    method.setAccessible(true);
                    method.invoke(activity, contentViewResId);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //注入控件绑定事件
    private static void injectViews(Activity activity) {
        //获取Class
        Class<? extends Activity> clazz = activity.getClass();
        //获取所有的字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //获取注解
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                //获取控件id
                int viewId = viewInject.value();
                if (viewId == -1) {
                    Log.e("annotation", "view res id is null...");
                    continue;
                }
                try {
                    //获取类中的findviewbyid方法，参数为int
                    Method method = clazz.getMethod(FIND_VIEW_BY_ID, int.class);
                    //执行方法，返回一个object类型的view实例
                    Object resView = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    //把字段的值设置为该view的实例
                    field.set(activity, resView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
