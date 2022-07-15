package com.SpringbootRollAuth.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.SpringbootRollAuth.Entity.Roles;
import com.SpringbootRollAuth.Repo.RolesRepo;

@Service
@Component

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private RolesRepo rolesrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<Roles >roles = rolesrepo.findByusername(username);
		return roles.map(GroupUserDeatails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + "does not exist in the system"));
	}

}
