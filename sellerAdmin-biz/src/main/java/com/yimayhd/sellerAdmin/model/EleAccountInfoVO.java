package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.result.eleaccount.EleAccountInfoResult;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by hongfei.guo on 2016/7/25.
 */
public class EleAccountInfoVO {
	
	/**用户ID*/
    private long userId;
    /**用户名*/
    private String userName;
    /**用户昵称*/
    private String nickName;
    /**手机号*/
    private String mobilePhone;
    /**邮箱*/
    private String email;

    /**是否有支付密码*/
    private boolean existPayPwd;
    
	/**账户余额 - 分*/
    private long accountBalance;

	/**待结算资金 - 分*/
    private long unSettlementAmount;
    
    /**账户余额 - 元*/
    private String accountBalanceYuan;
    
    /**待结算资金 - 元*/
    private String unSettlementAmountYuan;

    /**机构名称*/
    private String corpName;
    /**对公帐号*/
    private String openAcctNo;
    /**开户账户名*/
    private String openAccName;
	/**开户银行名称*/
    private String openBankName;

    /**状态*/
    private int status;
	
	public static EleAccountInfoVO getEleAccountInfoVO(EleAccountInfoResult result){
		if(result == null){
			return null;
		}
		
		EleAccountInfoVO vo = new EleAccountInfoVO();
		BeanUtils.copyProperties(result, vo);
		 //分转元
        vo.setAccountBalanceYuan(NumUtil.moneyTransform(vo.getAccountBalance()));
        vo.setUnSettlementAmountYuan(NumUtil.moneyTransform(vo.getUnSettlementAmount()));
		return vo;
	}


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isExistPayPwd() {
        return existPayPwd;
    }

    public void setExistPayPwd(boolean existPayPwd) {
        this.existPayPwd = existPayPwd;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getOpenAcctNo() {
        return openAcctNo;
    }

    public void setOpenAcctNo(String openAcctNo) {
        this.openAcctNo = openAcctNo;
    }

    public String getOpenBankName() {
        return openBankName;
    }

    public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String getAccountBalanceYuan() {
		return accountBalanceYuan;
	}
	public void setAccountBalanceYuan(String accountBalanceYuan) {
		this.accountBalanceYuan = accountBalanceYuan;
	}
	public String getUnSettlementAmountYuan() {
		return unSettlementAmountYuan;
	}
	public void setUnSettlementAmountYuan(String unSettlementAmountYuan) {
		this.unSettlementAmountYuan = unSettlementAmountYuan;
	}
	public long getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(long accountBalance) {
		this.accountBalance = accountBalance;
	}
	public long getUnSettlementAmount() {
		return unSettlementAmount;
	}
	public void setUnSettlementAmount(long unSettlementAmount) {
		this.unSettlementAmount = unSettlementAmount;
	}
	public String getOpenAccName() {
		return openAccName;
	}
	public void setOpenAccName(String openAccName) {
		this.openAccName = openAccName;
	}
}
