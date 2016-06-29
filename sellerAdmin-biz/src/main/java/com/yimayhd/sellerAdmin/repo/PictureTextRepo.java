package com.yimayhd.sellerAdmin.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComentDO;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.dto.ComentQueryDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commentcenter.client.service.ComPictureTextService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 标签Repo
 * 
 * @author yebin
 *
 */
public class PictureTextRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ComPictureTextService comPictureTextServiceRef;

	public PicTextResult getPictureText(long outId, PictureText outType) {
		if (outId <= 0 || outType == null) {
			log.warn("PictureTextRepo.getPictureText(outId,outType) warn: 参数异常");
			log.warn("Param outId=" + outId);
			log.warn("Param outType=" + outType);
			throw new BaseException("参数异常");
		}
		ComentQueryDTO comentQueryDTO = new ComentQueryDTO();
		comentQueryDTO.setOutId(outId);
		comentQueryDTO.setDomain(Constant.DOMAIN_JIUXIU);
		comentQueryDTO.setOutType(outType.name());
		RepoUtils.requestLog(log, "comPictureTextServiceRef.getPictureText", comentQueryDTO);
		BaseResult<PicTextResult> pictureText = comPictureTextServiceRef.getPictureText(comentQueryDTO);
		RepoUtils.resultLog(log, "comPictureTextServiceRef.getPictureText", pictureText);
		return pictureText.getValue();
	}
	@Deprecated
	public void savePictureText(ComentDTO comentDTO) {
		if (comentDTO == null) {
			log.warn("PictureTextRepo.savePictureText(comentDTO) warn: 参数异常");
			log.warn("Param comentDTO=" + comentDTO);
			throw new BaseException("参数异常");
		}
		comentDTO.setDomain(Constant.DOMAIN_JIUXIU);
		RepoUtils.requestLog(log, "comPictureTextServiceRef.savePictureText", comentDTO);
		BaseResult<ComentDO> savePictureText = comPictureTextServiceRef.savePictureText(comentDTO);
		RepoUtils.resultLog(log, "comPictureTextServiceRef.savePictureText", savePictureText);
	}

	@Deprecated
	public void updatePictureText(ComentEditDTO comentEditDTO) {
		if (comentEditDTO == null) {
			log.warn("PictureTextRepo.updatePictureText(comentEditDTO) warn: 参数异常");
			log.warn("Param comentEditDTO=" + comentEditDTO);
			throw new BaseException("参数异常");
		}
		RepoUtils.requestLog(log, "comPictureTextServiceRef.updatePictureText", comentEditDTO);
		BaseResult<ComentDO> updatePictureText = comPictureTextServiceRef.updatePictureText(comentEditDTO);
		RepoUtils.resultLog(log, "comPictureTextServiceRef.updatePictureText", updatePictureText);
	}
	//TODO ComentEditDTO 中的itemType
	public void editPictureText(ComentEditDTO comentEditDTO) {
		if (comentEditDTO == null) {
			log.warn("PictureTextRepo.editPictureText(comentEditDTO) warn: 参数异常");
			log.warn("Param comentEditDTO=" + comentEditDTO);
			throw new BaseException("参数异常");
		}
		comentEditDTO.setDomain(Constant.DOMAIN_JIUXIU);
		RepoUtils.requestLog(log, "comPictureTextServiceRef.editPictureText", comentEditDTO);
		BaseResult<ComentDO> editPictureText = comPictureTextServiceRef.editPictureText(comentEditDTO);
		RepoUtils.resultLog(log, "comPictureTextServiceRef.editPictureText", editPictureText);
	}
}
