package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 资源接口
 * 
 * @author yebin
 *
 */
public class ResourceRepo {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected ItemQueryService itemQueryServiceRef;
	@Autowired
	protected ResourcePublishService resourcePublishServiceRef;

	public boolean addPictures(List<PicturesDO> list) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.addPictures", list);
		ICResult<Boolean> icResultPic = resourcePublishServiceRef.addPictures(list);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.addPictures", icResultPic);
		return icResultPic.getModule();
	}

	public boolean updatePictures(PictureUpdateDTO pictureupdatedto) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.updatePictures", pictureupdatedto);
		ICResult<Boolean> icResultPic = resourcePublishServiceRef.updatePictures(pictureupdatedto);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.updatePictures", icResultPic);
		return icResultPic.getModule();
	}

}
