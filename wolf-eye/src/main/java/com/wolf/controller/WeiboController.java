package com.wolf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weibo4j.Account;
import weibo4j.Comments;
import weibo4j.Oauth;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/test/weibo")
public class WeiboController {
    private static Map<String, String> map = new HashMap<String, String>();

    @RequestMapping(value = "/getRequestToken/{guid}", method = RequestMethod.GET)
    public String getWeiboRequestToken(@PathVariable("guid") String guid, Model model) throws WeiboException {
        Oauth oauth = new Oauth();
        try {
            String authorizeURL = oauth.authorize("code");
            model.addAttribute("guid", guid);
            model.addAttribute("authorizeUrl", authorizeURL);
        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return "/weibo/testWeibo";
    }

    @RequestMapping(value = "/weiboAuthorize",method = RequestMethod.GET)
    public String weiboAuthorize(@RequestParam("code") String code, Model model) throws WeiboException {
        Oauth oauth = new Oauth();
        AccessToken accessToken = null;
        try {
            //得到授权码
            accessToken = oauth.getAccessTokenByCode(code);
            map.put("accessToken",accessToken.getAccessToken());
            model.addAttribute("accessToken",accessToken.getAccessToken());
            System.out.println("======the accessToken: " + accessToken.getAccessToken() + "=========");
        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return "/weibo/sendWeibo";
    }
  
    @RequestMapping(value = "/sendWeibo",method = RequestMethod.GET)
    public String sendWeibo(@RequestParam(value="msg",required = false) String msg){
        Weibo weibo = new Weibo();
        String accessToken = map.get("accessToken");
        weibo.setToken(accessToken);
        Comments cm = new Comments();
        Account am = new Account();
        try {
            JSONObject id = am.getUid();
            Comment comment = cm.createComment(msg, id.toString());
            System.out.println("coment is "+comment.toString()+"------------");
        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return "/weibo/testWeibo";
    }
}

