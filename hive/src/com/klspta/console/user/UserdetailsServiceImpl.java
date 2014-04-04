package com.klspta.console.user;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.klspta.console.ManagerFactory;

/**
 * 
 * <br>
 * Title:用户详细信息获取类 <br>
 * Description: <br>
 * Author:陈强峰 <br>
 * Date:2012-2-8
 */

public class UserdetailsServiceImpl implements UserDetailsService {
	/**
	 * 
	 * <br>
	 * Description:获取用户信息 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-8
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		try {
            return ManagerFactory.getUserManager().getUserWithName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
}
