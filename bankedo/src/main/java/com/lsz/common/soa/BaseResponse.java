package com.lsz.common.soa;


/**
 * @author JiangZuoWei
 * @createTime 2015年11月10日 下午3:52:30
 * @description 
 */
public class BaseResponse implements java.io.Serializable{

	private boolean success = false;
	
	private String code;
	
	private String message;

	protected BaseResponse(){}

	public BaseResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public BaseResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public boolean isSuccess() {
		//soa 返回code2000表示成功，否则表示失败
		if("2000".equals(code)){
			this.success = true;
		}else{
			this.success = false;
		}
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
