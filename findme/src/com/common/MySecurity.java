package com.common;

public class MySecurity {
	/** 
	 * 将指定字符串加密 
	 * @param pwd String 要加密的字符串 
	 * @return 
	 */
	public static String encrypt(String pwd) 
	 { 
		 char chr [] = pwd.toCharArray(); 
		 char ret [] = pwd.toCharArray(); 
		 for(int i =0;i<chr.length;i++) 
		 { 
			 int ascii = Integer.valueOf(chr[chr.length-i-1]); 
			 char newchr = (char)(ascii-1); 
			 ret[i] = newchr; 
		 } 
		 String result = new String(ret); 
		 return result; 
	}
	/** 
	 * 将指定字符串解密 
	 * @param pwd String 要解密的字符串 
	 * @return 
	 */
	public static String decrypt(String pwd) {
		char chr[] = pwd.toCharArray();
		char ret [] = pwd.toCharArray(); 
		for (int i = 0; i < chr.length; i++) {
			int ascii = Integer.valueOf(chr[chr.length-i-1]);
			char newchr = (char) (ascii+1);
			ret[i] = newchr;
		}
		String result = new String(ret);
		return result;
	}

	public MySecurity() {
		// TODO Auto-generated constructor stub 
	}
	
	public static void main(String[] args)
	{
		String str=encrypt("pen2g@126.com");
		System.out.println(str);
		System.out.println(decrypt(str));
		System.out.println(decrypt("lnb-510?f1mdo"));
	}
}
