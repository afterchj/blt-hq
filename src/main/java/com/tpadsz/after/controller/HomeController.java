package com.tpadsz.after.controller;


import com.tpadsz.after.entity.User;
import com.tpadsz.after.service.UserService;
import com.tpadsz.after.utils.Constants;
import com.tpadsz.after.utils.WSClientUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjian.chen on 2018/8/1.
 */
@Controller
public class HomeController {

    @Resource
    private UserService userService;
    private Logger log = Logger.getLogger(HomeController.class);

    @ResponseBody
    @RequestMapping(value = "/webchat/{username}")
    public String webchat(@PathVariable String username, HttpSession session) {
        session.setAttribute(Constants.SESSION_USERNAME.value(), username);
        String msg = "{\"result\":\"000\",\"msg\":\"成功\"}";
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/switch", produces = "application/json; charset=utf-8")
    private String switchController(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        String params = req.getParameter("switchFlag");
        System.out.println("params=" + params);
        String msg = "{\"result\":\"000\",\"msg\":\"成功\"}";
        if (params == null) {
            params = msg;
        }
        final String finalParams = params;
        new Thread(new Runnable() {
            public void run() {
                WSClientUtil.sendMsg(finalParams);
            }
        }).start();
        log.info("show-->" + params);
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    public String test(HttpServletResponse response, @RequestBody String params) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return params;
    }

    @RequestMapping(value = "/toChat")
    public String toChat() {
        return "websocket";
    }

    @RequestMapping(value = "/login")
    public String login(HttpSession session) {
        List<User> users = userService.selectAll();
        session.setAttribute("users", users);
        return "userInfo";
    }

    @RequestMapping(value = "/meshSystem")
    public String toMeshSystem(ModelMap modelMap) {
        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 3; i++) {
            Map map = new HashMap();
            map.put("mid", "100" + (i + 1));
            map.put("mName", "test" + i);
            items.add(map);
        }
        modelMap.put("items", items);
        return "meshSystem";
    }

    @RequestMapping(value = "/productInfo")
    public String toProductInfo() {
        return "productInfo";
    }

    @RequestMapping(value = "/lightInfo")
    public String toLightInfo() {
        return "lightInfo";
    }

    @RequestMapping(value = "/meshInfo")
    public String toMeshInfo() {
        return "meshInfo";
    }

    @RequestMapping(value = "/userInfo")
    public String toUserInfo() {
        return "userInfo";
    }

    @RequestMapping(value = "/loginOut")
    public String loginOut() {
        return "forward:/toChat";
    }
}
