//package com.tpadsz.after.socket;
//
//import com.tpadsz.after.utils.PropertiesUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//
///**
// * Created by hongjian.chen on 2019/1/22.
// */
//
////@Service
//public class StartService implements InitializingBean {
//
//    private Logger logger = Logger.getLogger(StartService.class);
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        new Thread(new StartRun()).start();
//    }
//
//    private class StartRun implements Runnable {
//        @Override
//        public void run() {
//            Class clz;
//            try {
//                clz = Class.forName(PropertiesUtils.getValue("className1"));
//                Method method = clz.getDeclaredMethod("service");
//                method.setAccessible(true);
//                Constructor constructor = clz.getConstructor();
//                Object object = constructor.newInstance();
//                method.invoke(object);
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//        }
//    }
//}
