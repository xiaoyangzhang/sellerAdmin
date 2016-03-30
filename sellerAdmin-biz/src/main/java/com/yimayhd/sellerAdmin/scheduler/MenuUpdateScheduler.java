package com.yimayhd.sellerAdmin.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuzhengfei358
 */
public class MenuUpdateScheduler {
	private static final Logger logger = LoggerFactory.getLogger("MenuUpdateScheduler");

	public void execute() {
		doWork();
	}

	private void doWork() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("doWork", e);
		}
	}
}
