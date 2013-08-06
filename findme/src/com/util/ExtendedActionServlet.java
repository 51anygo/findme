package com.util;
import org.apache.struts.action.*; 
import org.apache.commons.logging.LogFactory; 
import org.apache.commons.logging.Log; 
import javax.servlet.ServletException; 
import java.util.Properties; 
import org.apache.log4j.PropertyConfigurator; 
import java.io.FileInputStream; 
import java.io.IOException; 
public class ExtendedActionServlet  extends ActionServlet {
	 private static final long serialVersionUID = -5543643811008717484L;

	 private Log log = LogFactory.getLog(this.getClass().getName()); 

     public ExtendedActionServlet() {} 

     public void init() throws ServletException { 
         log.info( 
                 "Initializing, My MyActionServlet init this System's Const Variable"); 
         String prefix = this.getServletConfig().getServletContext().getRealPath( 
                 "/"); 
         String file = this.getServletConfig().getInitParameter("log4j"); 
         String filePath = prefix + file; 
         Properties props = new Properties(); 
         System.out.println(prefix); 
         System.out.println(file); 
         System.out.println(filePath); 

         try { 
             FileInputStream log4jStream = new FileInputStream(filePath); 
             props.load(log4jStream); 
             log4jStream.close(); 
             String logFile = prefix + 
                              props.getProperty("log4j.appender.A1.File"); //设置路径 

             System.out.println(logFile); 
             props.setProperty("log4j.appender.A1.File", logFile); 
             PropertyConfigurator.configure(props); //装入log4j配置信息 
         } catch (IOException e) { 
             e.printStackTrace(); 
         } 
         log.info("Initializing, end My Init"); 
         super.init();//应用了struts,此方法不能省，ActionServlet覆盖了的此方法中有很多重要操作 
     } 
}
