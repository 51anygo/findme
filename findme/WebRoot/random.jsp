<%@ page isELIgnored="false" autoFlush="false"   import="java.util.*,java.awt.*,
java.awt.image.*,com.sun.image.codec.jpeg.*,java.util.*" pageEncoding="GBK"%>
<%!
// 给定范围获得随机颜色
Color getRandColor(int fc,int bc) {
    Random random = new Random();
    if(fc > 255) {
        fc = 255;
    }
    if(bc > 255) {
        bc = 255;
    }
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
}
%>
<%

	//set Chinese Char 
	
	//Cody by JarryLi@gmail.com;
	
	//homepage:jiarry.126.com
	
	request.setCharacterEncoding("GBK");
	
	response.setCharacterEncoding("GBK");
	
	response.setContentType("text/html; charset=GBK");

%>

<%

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
	session.setAttribute("random",String.valueOf(ran));

%>

<%
	out.clear();
	
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
	out.clear();
	out = pageContext.pushBody();

%>

