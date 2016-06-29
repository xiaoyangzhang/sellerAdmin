package com.yimayhd.sellerAdmin.base;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;

/**
 * @author
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 715232087424762931L;

	public BaseException(Object msg) {
		super(JSON.toJSONString(msg));
	}

	public BaseException(Throwable cause, String pattern, Object... args) {
		super(format(pattern, args), cause);
	}

	public BaseException(String pattern, Object... args) {
		super(MessageFormat.format(pattern, args));
	}

	public BaseException(Throwable cause, Object msg) {
		super(JSON.toJSONString(msg), cause);
	}

	/**
	 * 格式化
	 * 
	 * @param pattern
	 * @param args
	 * @return
	 */
	private static String format(String pattern, Object... args) {
		if (args != null) {
			Object[] _args = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				_args[i] = JSON.toJSONString(args[i]);
			}
			return MessageFormat.format(pattern, _args);
		} else {
			return pattern;
		}
	}
}
