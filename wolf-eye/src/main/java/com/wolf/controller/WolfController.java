package com.wolf.controller;

import com.wolf.common.utils.HttpRequester;
import com.wolf.common.utils.HttpResponser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller(value = "wolfController")
@RequestMapping(value = "/wolf")
public class WolfController {

    private static String kingBatteryUrl = "http://www.baidu.com/s?wd=%E4%B8%9C%E8%8E%9E&rsv_bp=0&ch=&tn=baidu&bar=&rsv_spt=3&ie=utf-8&rsv_sug3=4&rsv_sug4=59&rsv_sug1=4&oq=%E4%B8%9C&rsv_sug2=0&f=3&rsp=0&inputT=5";

    @Autowired
    private HttpRequester httpRequester;

    @RequestMapping(value = "/fullcalendViews",method = RequestMethod.GET)
    public String basicViews(){

        return "/resources/wolf/fullcalend/basicViews";
    }

    @RequestMapping(value = "/webWorkersViews",method = RequestMethod.GET)
    public String webWorkersViews(){
        
        return "/resources/wolf/webworkers/webWorkersView";
    }

    @RequestMapping(value = "/cpaSave",method = RequestMethod.GET)
    public String saveCpa(){

        try {
            HttpResponser responser = httpRequester.sendGet(kingBatteryUrl, null);
            System.out.println("code:"+responser.getCode());
        } catch (IOException e) {

        }
        return "/resources/wolf/webworkers/webWorkersView";
    }

}
