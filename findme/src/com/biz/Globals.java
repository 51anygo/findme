package com.biz;
import weibo4j.Status;
import weibo4j.Weibo; 

public class Globals {
    public double TranDMsToDegree(String strdfm)
    {
    	int pos1=strdfm.indexOf("°");
    	int pos2=strdfm.indexOf("'");
    	int pos3=strdfm.indexOf("''");
    	double du1=0;
    	double du2=0;
    	double du3=0;
    	if(pos1>0)
    	{
    		du1=Integer.parseInt(strdfm.substring(0,pos1));
    	}
    	if(pos2>0)
    	{
    	    double fen=Integer.parseInt(strdfm.substring(pos1+1,pos2));
    	    du2=fen/60.;
    	}
    	if(pos3>0)
    	{
	    	double miao=Integer.parseInt(strdfm.substring(pos2+1,pos3));
	    	du3=miao/3600.;
    	}
	    double du=du1+du2+du3;	    
    	return du;
    }
    
    public void PushToSinaWeiBo(String strmsg,String strusername,String strpassword)
    {
    	System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
       try {
       	Weibo weibo = getWeibo(false,strusername,strpassword);
       	//Status status = weibo.updateStatus("测试发表微博");
       	Status status = weibo.updateStatus(strmsg);
       	System.out.println(status.getId() + " : "+ status.getText()+"  "+status.getCreatedAt());
       	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

	private static Weibo getWeibo(boolean isOauth,String strusername,String strpassword) {
		Weibo weibo = new Weibo();
		if(isOauth) {//oauth验证方式 args[0]:访问的token；args[1]:访问的密匙
			weibo.setToken(strusername, strpassword);
		}else {//用户登录方式
    		weibo.setUserId(strusername);//用户名/ID
   		weibo.setPassword(strpassword);//密码
		}
		return weibo;
	}
	
    public String TranDegreeToDMs(double dfm)
    {
    	String param=String.valueOf(dfm);  
        return  getCoordinate(param);   
    }
    
    public   String   getCoordinate(String   param){   
        //System.out.println("param="+param);   
        if   (param==null   ||   !checkNum(param)){   
            System.out.println("参数为空或不是有效的数字字符串！");   
            return   null;   
        }   
        String   degree=null,minute=null;   
        String[]   store=null;   
        if(param.indexOf(".")>=0){   
            store=param.split("\\.");   
        }else{   
            store=new   String[1];   
            store[0]=param;   
        }   
        if   (store.length   ==2){   
            minute=getMinute(store[1]);   
            if(minute!=null){   
                degree=store[0]+"°"+minute;   
            }else{   
                degree=store[0]+"°";   
            }   
        }else{   
            degree=store[0]+"°";   
        }   
        System.out.println(degree);   
        return     degree;   
    }   
    /**   
      *给定字符获得分的信息   
      *   @param   min   String   
      *   @return   String   
      */   
    private   String   getMinute(String   min){   
        if   (min==null){   
            return   null;   
        }   
        String   temp=null;   
        String   mins=null,secs=null;   
        float   fmin=Float.parseFloat("0."+min)*60;   
        String[]   store=null;   
        if   (fmin>0){   
            temp=Float.toString(fmin);   
            if   (temp.indexOf(".")>=0){   
                store=temp.split("\\.");   
                if   (store.length   ==2)   
                    secs=getSecond(store[1]);   
            }else{   
                store=new   String[1];   
                store[0]=temp;   
            }   
            mins=store[0]+"′";   
        }   
        if   (secs!=null){   
            temp=mins+secs;   
        }else{   
            temp=mins;   
        }   
        return   temp;   
    }   
    /**   
      *   给定字符串获得秒的信息   
      *   @param   sec   
      *   @return   
      */   
    private   String   getSecond(String   sec){   
        if   (sec==null){   
            return   null;   
        }   
        float   fsec=Float.parseFloat("0."+sec)*60;   
        int   second=Math.round(fsec);   
        String   temp=null;   
        if   (second   >0){   
            temp=Integer.toString(second)+"″";   
        }   
        return   temp;   
    }   
    /**   
      *   检测输入的字符串是否有效   
      *   @param   parm   String     
      *   @return   boolean   
      */   
    private   boolean   checkNum(String   parm){   
        String   numbers="0123456789.";   
        int   dot=0;   
        //判断是否包含dot   char   
        char   chars;   
        for(int   i=0;i<parm.length();i++){   
            chars=parm.charAt(i);   
            if(numbers.indexOf(chars)==-1){   
                return   false;   
            }else   if(chars=='.'){   
                ++dot;   
                if   (dot>1){   
                    return   false;   
                }   
            }   
        }   
        return   true;   
    }   
}
