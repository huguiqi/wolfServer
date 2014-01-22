package com.wolf.controller;

import com.wolf.bean.DemoSolrBook;
import com.wolf.bean.SolrOperterType;
import com.wolf.common.utils.RestClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "solrController")
@RequestMapping(value = "/solr")
public class SolrController {

     private static Logger logger = Logger.getLogger(SolrController.class);
    
    @Autowired
    RestClient restClient;

     @RequestMapping(value = "/solrIndex", method = RequestMethod.GET)
    public String index() {
        logger.debug("Received request to show solr page");
        return "solrpage/solrindex";

    }

    @RequestMapping(value = "/sendJson", method = RequestMethod.GET)
    public String sendJson() {
        DemoSolrBook demoSolrBook = new DemoSolrBook();
        demoSolrBook.setId(1L);
        demoSolrBook.setTitle("一伙骚B");
        demoSolrBook.setUser("祝英台");
        demoSolrBook.setContent("小黑嫖妓，小白看片,小黄吃药,小二玩勺子棒");
        System.out.println("===============进来了。。。。====");
        restClient.post("http://localhost:8087/wolf-solr/update/json", SolrOperterType.ADD.converToJsonString(demoSolrBook), String.class);
        return "solrpage/solrindex";
    }

}
