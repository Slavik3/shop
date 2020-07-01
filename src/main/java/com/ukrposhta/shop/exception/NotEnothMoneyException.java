package com.ukrposhta.shop.exception;

public class NotEnothMoneyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1092366061336915479L;

	public NotEnothMoneyException(String errorMessage) {
		super(errorMessage);
	}

}
