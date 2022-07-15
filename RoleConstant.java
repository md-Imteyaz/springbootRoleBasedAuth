package com.SpringbootRollAuth.Common;

import org.springframework.stereotype.Component;

@Component

public class RoleConstant {
	public static final String DEFAULT_ROLE= "ROLE_USER";
	public static final String[] ADMIN_ACCESS = { "ROLE_ADMIN", "ROLE_MODERATOR" };
	public static final String[] MODERATE_ACCESS = {"ROLE_MODERATOR"};

}
