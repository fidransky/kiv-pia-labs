package cz.zcu.kiv.pia.labs;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class WebApp {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080); // start Tomcat on port 8080
        tomcat.getConnector(); // create the default connector

        // create "pia-labs" servlet context
        Context context = tomcat.addWebapp("/pia-labs", new File("pia-labs-web/src/main/webapp/").getAbsolutePath());

        // make Tomcat use compiled classes
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", new File("pia-labs-web/target/classes").getAbsolutePath(), "/"));
        context.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}