package com.klspta.console.user;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {
	/**
	 * <br>
	 * Description:serialVersionUID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-8
	 */
	private static final long serialVersionUID = 1L;
	String authority;

	GrantedAuthorityImpl(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
