package com.yimayhd.sellerAdmin.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.yimayhd.sellerAdmin.biz.MenuBiz;

/**
 *
 * @author wuzhengfei358
 *
 */
public class ContextStartUpLinstener implements ApplicationListener<ContextRefreshedEvent>{
	private static final Logger logger = LoggerFactory.getLogger(ContextStartUpLinstener.class);
	private boolean isStarted = false;
	@Autowired
	private MenuBiz menuBiz ;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Task task = new Task();
		task.start();
	}

	private class Task extends Thread  {

		public void run() {
			if( isStarted ){
				return ;
			}
			isStarted = true ;

			//容器启动初始化活动
			logger.info("=================================init======================================");
			menuBiz.cacheAllMenusToTair();

		}

	}


}
