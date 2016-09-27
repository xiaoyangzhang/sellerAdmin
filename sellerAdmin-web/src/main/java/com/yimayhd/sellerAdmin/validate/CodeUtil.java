package com.yimayhd.sellerAdmin.validate;

import java.util.Random;

public class CodeUtil {

	private static final String[] CHARS = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" ,
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
			"u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z"
	};
	public static final String[] NUMBER = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
			/*"3", "4", "5", "6", "7", "8", "9" ,
			"A", "B", "C", "D", "E", "F", "G", "H",
			"K", "M", "N", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y"*/
	};
	private static final Random random = new Random() ;
	private static final int TOKEN_LENGTH = 32;

	public static final String getToken(){
		int length = CHARS.length ;
		StringBuffer sb = new StringBuffer() ;
		for( int i=0; i<TOKEN_LENGTH ; i++ ){
			int index = random.nextInt(length);
			sb.append(CHARS[index]) ;
		}
		return sb.toString();
	}

	public static final String getNumberCode(int length){
		StringBuffer sb = new StringBuffer() ;
		for( int i=0; i<length ; i++ ){
			int index = random.nextInt(NUMBER.length);
			sb.append(NUMBER[index]) ;
		}
		return sb.toString();
	}















	
	
}
