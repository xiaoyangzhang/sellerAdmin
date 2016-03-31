package com.yimayhd.sellerAdmin.vo.menu;

import java.io.Serializable;

public class MenuTreeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private MenuVO menu;
	private MenuVO parent;
	
	public MenuTreeVO(MenuVO menu, MenuVO parent) {
		super();
		this.menu = menu;
		this.parent = parent;
	}

	public MenuVO getMenu() {
		return menu;
	}

	public void setMenu(MenuVO menu) {
		this.menu = menu;
	}

	public MenuVO getParent() {
		return parent;
	}

	public void setParent(MenuVO parent) {
		this.parent = parent;
	}

}