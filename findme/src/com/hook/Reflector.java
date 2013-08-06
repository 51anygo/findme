package com.hook;

import java.lang.reflect.Field;

public class Reflector {
	 public static Object getAccessibleField(Object obj,String name) throws Exception
	    {
	        Class c = obj.getClass();
	        Field f = c.getDeclaredField(name);
	        f.setAccessible(true);
	        return f.get(obj);
	    }
}
