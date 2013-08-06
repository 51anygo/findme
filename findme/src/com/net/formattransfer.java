package com.net;
/**  
* 通信格式转换  
*  
* Java和一些windows编程语言如c、c++、delphi所写的网络程序进行通讯时，需要进行相应的转换  
* 高、低字节之间的转换  
* windows的字节序为低字节开头  
* linux,unix的字节序为高字节开头  
* java则无论平台变化，都是高字节开头  
*/  
  
public class formattransfer {   
/**  
  * 将int转为低字节在前，高字节在后的byte数组  
  * @param n int  
  * @return byte[]  
  */  
public static byte[] toLH(int n) {   
  byte[] b = new byte[4];   
  b[0] = (byte) (n & 0xff);   
  b[1] = (byte) (n >> 8 & 0xff);   
  b[2] = (byte) (n >> 16 & 0xff);   
  b[3] = (byte) (n >> 24 & 0xff);   
  return b;   
}   
  
/**  
  * 将int转为高字节在前，低字节在后的byte数组  
  * @param n int  
  * @return byte[]  
  */  
public static byte[] toHH(int n) {   
  byte[] b = new byte[4];   
  b[3] = (byte) (n & 0xff);   
  b[2] = (byte) (n >> 8 & 0xff);   
  b[1] = (byte) (n >> 16 & 0xff);   
  b[0] = (byte) (n >> 24 & 0xff);   
  return b;   
}   
  
/**  
  * 将short转为低字节在前，高字节在后的byte数组  
  * @param n short  
  * @return byte[]  
  */  
public static byte[] toLH(short n) {   
  byte[] b = new byte[2];   
  b[0] = (byte) (n & 0xff);   
  b[1] = (byte) (n >> 8 & 0xff);   
  return b;   
}   
  
/**  
  * 将short转为高字节在前，低字节在后的byte数组  
  * @param n short  
  * @return byte[]  
  */  
public static byte[] toHH(short n) {   
  byte[] b = new byte[2];   
  b[1] = (byte) (n & 0xff);   
  b[0] = (byte) (n >> 8 & 0xff);   
  return b;   
}   
  
    
  
/**  
  * 将将int转为高字节在前，低字节在后的byte数组  
 
public static byte[] toHH(int number) {  
  int temp = number;  
  byte[] b = new byte[4];  
  for (int i = b.length - 1; i > -1; i--) {  
    b = new Integer(temp & 0xff).byteValue();  
    temp = temp >> 8;  
  }  
  return b;  
}  
 
public static byte[] IntToByteArray(int i) {  
    byte[] abyte0 = new byte[4];  
    abyte0[3] = (byte) (0xff & i);  
    abyte0[2] = (byte) ((0xff00 & i) >> 8);  
    abyte0[1] = (byte) ((0xff0000 & i) >> 16);  
    abyte0[0] = (byte) ((0xff000000 & i) >> 24);  
    return abyte0;  
}  
 
 
*/  
  
/**  
  * 将float转为低字节在前，高字节在后的byte数组  
  */  
public static byte[] toLH(float f) {   
  return toLH(Float.floatToRawIntBits(f));   
}   
  
/**  
  * 将float转为高字节在前，低字节在后的byte数组  
  */  
public static byte[] toHH(float f) {   
  return toHH(Float.floatToRawIntBits(f));   
}   
  
/**  
  * 将String转为byte数组  
  */  
public static byte[] stringToBytes(String s, int length) {   
  while (s.getBytes().length < length) {   
    s += " ";   
  }   
  return s.getBytes();   
}   
  
  
/**  
  * 将字节数组转换为String  
  * @param b byte[]  
  * @return String  
  */  
public static String bytesToString(byte[] b) {   
  StringBuffer result = new StringBuffer("");   
  int length = b.length;   
  for (int i=0; i<length; i++) {   
    result.append((char)(b[i] & 0xff));   
  }   
  return result.toString();   
}   
  
/**  
  * 将字符串转换为byte数组  
  * @param s String  
  * @return byte[]  
  */  
public static byte[] stringToBytes(String s) {   
  return s.getBytes();   
}   
  
/**  
  * 将高字节数组转换为int  
  * @param b byte[]  
  * @return int  
  */  
public static int lBytesToInt(byte[] b) {   
	   int accum = 0; 
	   for ( int shiftBy = 0; shiftBy < 4; shiftBy++ ) { 
	           accum |= (b[shiftBy] & 0xff) << shiftBy * 8; 
	   } 
	   return accum;    
}   
  
/**  
  * 将低字节数组转换为int  
  * @param b byte[]  
  * @return int  
  */  
public static int hBytesToInt(byte[] b) {   
	   int accum = 0; 
	   for ( int shiftBy = 0; shiftBy < 4; shiftBy++ ) { 
	           accum |= (b[4-shiftBy-1] & 0xff) << shiftBy * 8; 
	   } 
	   return accum; 
}   
  

public static long lBytesToLong(byte[] b){

	long accum = 0; 
    for ( int shiftBy = 0; shiftBy < 4; shiftBy++ ) { 
            accum |= ((long)(b[shiftBy] & 0xff)) << shiftBy * 8; 
    } 
    return accum;

}


public static long hBytesToLong(byte[] b){
	long accum = 0; 
    for ( int shiftBy = 0; shiftBy < 4; shiftBy++ ) { 
            accum |= ((long)(b[8-shiftBy-1] & 0xff)) << shiftBy * 8; 
    } 
    return accum;
}

  
/**  
  * 高字节数组到short的转换  
  * @param b byte[]  
  * @return short  
  */  
public static short lBytesToShort(byte[] b) {   
	   short accum = 0; 
	   for ( int shiftBy = 0; shiftBy < 2; shiftBy++ ) { 
	           accum |= (b[shiftBy] & 0xff) << shiftBy * 8; 
	   } 
	   return accum;     
}   
  
/**  
  * 低字节数组到short的转换  
  * @param b byte[]  
  * @return short  
  */  
public static short hBytesToShort(byte[] b) {   
	   short accum = 0; 
	   for ( int shiftBy = 0; shiftBy < 2; shiftBy++ ) { 
	           accum |= (b[2-shiftBy-1] & 0xff) << shiftBy * 8; 
	   } 
	   return accum;   
}   
  

/**  
  * 低字节数组转换为float  
  * @param b byte[]  
  * @return float  
  */  
public static float lBytesToFloat(byte[] b) {   
	   int accum = 0; 
	   for ( int shiftBy = 0; shiftBy < 4; shiftBy++ ) { 
	           accum |= (b[shiftBy] & 0xff) << shiftBy * 8; 
	   } 
	   return Float.intBitsToFloat(accum); 
}   

/**  
  * 高字节数组转换为float  
  * @param b byte[]  
  * @return float  
  */  
public static float hBytesToFloat(byte[] b) {   
	   int accum = 0; 
	   for ( int shiftBy = 0; shiftBy < 4; shiftBy++ ) { 
	           accum |= (b[4-shiftBy-1] & 0xff) << shiftBy * 8; 
	   } 
	   return Float.intBitsToFloat(accum);   
}   

  

public static double lBytesToDouble(byte[] b){

	   long accum = 0; 
       for ( int shiftBy = 0; shiftBy < 8; shiftBy++ ) { 
               accum |= ((long)(b[shiftBy] & 0xff)) << shiftBy * 8; 
       } 
       return Double.longBitsToDouble(accum);

}


public static double hBytesToDouble(byte[] b){
	   long accum = 0; 
       for ( int shiftBy = 0; shiftBy < 8; shiftBy++ ) { 
               accum |= ((long)(b[8-shiftBy-1] & 0xff)) << shiftBy * 8; 
       } 
       return Double.longBitsToDouble(accum);
}


/**  
  * 将byte数组中的元素倒序排列  
  */  
public static byte[] bytesReverseOrder(byte[] b) {   
  int length = b.length;   
  byte[] result = new byte[length];   
  for(int i=0; i<length; i++) {   
    result[length-i-1] = b[i];   
  }   
  return result;   
}   
  
/**  
  * 打印byte数组  
  */  
public static void printBytes(byte[] bb) {   
  int length = bb.length;   
  for (int i=0; i<length; i++) {   
    System.out.print(bb + " ");   
  }   
  System.out.println("");   
}   
  
/*public static void logBytes(byte[] bb) {   
  int length = bb.length;   
  String ut = "";   
  for (int i=0; i<length; i++) {   
    ut = out + bb[i] + " ";   
  }   
  
} */  
  
/**  
  * 将int类型的值转换为字节序颠倒过来对应的int值  
  * @param i int  
  * @return int  
  */  
public static int reverseInt(int i) {   
  int result = formattransfer.hBytesToInt(formattransfer.toLH(i));   
  return result;   
}   
  
/**  
  * 将short类型的值转换为字节序颠倒过来对应的short值  
  * @param s short  
  * @return short  
  */  
public static short reverseShort(short s) {   
  short result = formattransfer.hBytesToShort(formattransfer.toLH(s));   
  return result;   
}   
  
/**  
  * 将float类型的值转换为字节序颠倒过来对应的float值  
  * @param f float  
  * @return float  
  */  
public static float reverseFloat(float f) {   
  float result = formattransfer.hBytesToFloat(formattransfer.toLH(f));   
  return result;   
}   
  
}  
