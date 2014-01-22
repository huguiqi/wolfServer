package com.wolf.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

public class StartupListener implements ServletContextListener {


    private Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            System.setProperty("solr.solr.home", "c:/solr");
            logger.debug("========solr.home="+System.getProperty("solr.solr.home"));
       System.out.println(StartupListener.class.getResourceAsStream("/solrconfig-dep.zip"));
        ZipUitl.unzip(StartupListener.class.getResourceAsStream("/solrconfig-dep.zip"), "c:/solr");
        } catch (Exception e) {
           logger.error("",e);
           throw new RuntimeException(e);
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
