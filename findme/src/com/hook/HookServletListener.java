package com.hook;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
//这个文件是我简化过的，只有一个文件。
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.servlet.ServletRequest;
import java.lang.reflect.*;




public class HookServletListener implements ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		return;
		/*try {
            ServletRequest r = arg0.getServletRequest();
            //检查是否运行hook?
            if(r.getParameter("hookSocket")==null)
            {
            	return;
            }
           //反射，反射，还是反射
            Object request = Reflector.getAccessibleField(r, "request");
            Object coyoteRequest =  Reflector.getAccessibleField(request, "coyoteRequest");
            Object hook = Reflector.getAccessibleField(coyoteRequest, "hook");
            Object socket = Reflector.getAccessibleField(hook, "socket");
            Socket sk = Socket.class.cast(socket);
           //得到socket了。下面进行交互……
            PrintStream ps = new PrintStream(sk.getOutputStream());
            Scanner sc = new Scanner(sk.getInputStream());
            ps.print("Hello guy, are you happy?");
            if(sc.nextLine().equalsIgnoreCase("no"))
            {
                ps.println("do not be so sad guy^_^,Life is good!");
            }
            else
            {
            	ps.println("I'm happy to hear that. :-)");
            }
            ps.println("Good buy~~");
            ps.flush();
            //ps.close();
            //sk.close();
        } 
		catch (Exception ex)
		{
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }*/
	}
}
