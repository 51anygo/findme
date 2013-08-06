package com.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class AuthImage extends HttpServlet   
{   
  
    private static final String CONTENT_TYPE = "text/html; charset=utf-8";   
    //设置字母的大小,大小   
    private Font mFont = new Font("Times New Roman", Font.PLAIN, 17);   
    public void init() throws ServletException   
    {   
        super.init();   
    }   
    
    Color getRandColor(int fc,int bc)   
    {   
        Random random = new Random();   
        if(fc>255) fc=255;   
        if(bc>255) bc=255;   
        int r=fc+random.nextInt(bc-fc);   
        int g=fc+random.nextInt(bc-fc);   
        int b=fc+random.nextInt(bc-fc);   
        return new Color(r,g,b);   
    }   
  
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException   
    {   
        response.setHeader("Pragma","No-cache");   
        response.setHeader("Cache-Control","no-cache");   
        response.setDateHeader("Expires", 0);   
        //表明生成的响应是图片   
        response.setContentType("image/jpeg");
    	
    	response.addHeader("pragma","NO-cache");
    	
    	response.addHeader("Cache-Control","no-cache");
    	
    	response.addDateHeader("Expries",0);
    	
    	int width=80, height=20;
    	
    	BufferedImage image = new BufferedImage(width, height, 
    	BufferedImage.TYPE_INT_RGB);
    	
    	Graphics g = image.getGraphics();
    	
    	//以下填充背景颜色
    	
    	g.setColor(getRandColor(200, 250));
    	
    	g.fillRect(0, 0, width, height);
    	
    	//设置字体颜色
    	
    	g.setColor(Color.RED);
    	
    	Font font=new Font("Times New Roman", Font.PLAIN, 18);
    	
    	g.setFont(font);
    	String chose="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	char display[]={'0',' ','0',' ','0',' '},ran[]={'0','0','0'},temp;
    	Random rand=new Random();
    	for(int i=0;i<3;i++)    	
    	{
    		temp=chose.charAt(rand.nextInt(chose.length()));
    		display[i*2]=temp;
    		ran[i]=temp;
    	
    	} 	    	
    	String random=String.valueOf(display);
        System.out.println(random);
    	HttpSession session = request.getSession(true);   
        session.setAttribute("random",String.valueOf(ran));
    	//g.drawString(random,2,14);
    	// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
    	g.setColor(getRandColor(160, 200));
    	for (int i = 0; i < 155; i++) {
    	    int x = rand.nextInt(width);
    	    int y = rand.nextInt(height);
    	    int xl = rand.nextInt(12);
    	    int yl = rand.nextInt(12);
    	    g.drawLine(x,y,x+xl,y+yl);
    	}
    	g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));
    	g.drawString(random,15,15);
    	
    	g.dispose();
    	
    	ServletOutputStream outStream = response.getOutputStream();
    	
    	JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(outStream);
    	
    	encoder.encode(image); 
    	outStream.close();
    }   
    public void destroy()   
    {   
    }   
}  