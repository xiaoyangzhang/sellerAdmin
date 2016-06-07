package com.yimayhd.sellerAdmin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;

/**
 * Created by czf on 2015/12/26. 资源图片集用
 */
public class PictureVO {
	private static final int NAME_MAX_LENGTH = 15;
	private long id;// 图片id
	private int index;// 图片索引
	private String name;// 图片原名称
	private String value;// 图片url
	private boolean isTop;// 是否置顶
	private boolean modify = false;// 是否修改
	private boolean isdel = false;// 是否删除

	/**
	 * 修改提交时设置pictureList
	 * 
	 * @param outId
	 *            资源ID
	 * @param pictureOutType
	 *            资源类型
	 * @param pictureUpdateDTO
	 *            更新用的包装DTO对象
	 * @param picturesDOList
	 *            数据库图片列表
	 * @param pictureVOList
	 *            页面传过来的图片列表
	 * @return
	 * @throws Exception
	 */
	public static PictureUpdateDTO setPictureListPictureUpdateDTO(long outId, PictureOutType pictureOutType,
			PictureUpdateDTO pictureUpdateDTO, List<PicturesDO> picturesDOList, List<PictureVO> pictureVOList) {
		if (pictureUpdateDTO == null || CollectionUtils.isEmpty(pictureVOList)) {
			return null;
		}

		// 新增sku数组
		List<PicturesDO> addPicturesDOList = new ArrayList<PicturesDO>();
		// 删除sku数组
		List<Long> delPicturesDOList = new ArrayList<Long>();
		// 修改sku数组
		List<PicturesDO> modifyPicturesDOList = new ArrayList<PicturesDO>();

		if (CollectionUtils.isEmpty(picturesDOList)) {
			for (PictureVO pictureVO : pictureVOList) {
				if (pictureVO.getId() == 0) {
					PicturesDO picturesDO = pictureVO.toPicturesDO();
					picturesDO.setOutId(outId);
					picturesDO.setOutType(pictureOutType.getValue());
					addPicturesDOList.add(picturesDO);
				}
			}
		} else {
			// picturesDOList转map
			Map<Long, PicturesDO> pictureDOMap = new HashMap<Long, PicturesDO>();
			for (PicturesDO picturesDO : picturesDOList) {
				pictureDOMap.put(picturesDO.getId(), picturesDO);
			}

			for (PictureVO pictureVO : pictureVOList) {
				if (pictureVO.getId() == 0) {
					PicturesDO picturesDO = pictureVO.toPicturesDO();
					picturesDO.setOutId(outId);
					picturesDO.setOutType(pictureOutType.getValue());
					addPicturesDOList.add(picturesDO);
				} else {
					if (pictureVO.isdel()) {
						delPicturesDOList.add(pictureVO.getId());
					} else {
						if (pictureVO.isModify()) {
							PicturesDO picturesDO = pictureDOMap.get(pictureVO.getId());
							picturesDO.setIsTop(pictureVO.isTop());
							modifyPicturesDOList.add(picturesDO);
						}
					}
				}
			}
		}
		pictureUpdateDTO.setAddPictureDOList(addPicturesDOList);
		pictureUpdateDTO.setDelPictureIdList(delPicturesDOList);
		pictureUpdateDTO.setUpdatePictureDOList(modifyPicturesDOList);
		return pictureUpdateDTO;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public boolean isdel() {
		return isdel;
	}

	public void setIsdel(boolean isdel) {
		this.isdel = isdel;
	}

	public PicturesDO toPicturesDO() {
		PicturesDO picturesDO = new PicturesDO();
		picturesDO.setPath(this.value);
		if (StringUtils.isNotBlank(this.name)) {
			int length = this.name.length() > NAME_MAX_LENGTH ? NAME_MAX_LENGTH : this.name.length();
			picturesDO.setName(name.substring(0, length));
		}
		picturesDO.setIsTop(this.isTop);
		return picturesDO;
	}
}
