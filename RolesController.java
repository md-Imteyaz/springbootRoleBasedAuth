package com.SpringbootRollAuth.Controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringbootRollAuth.Common.RoleConstant;
import com.SpringbootRollAuth.Entity.Roles;
import com.SpringbootRollAuth.Repo.RolesRepo;

@RestController
@RequestMapping("/roles")

public class RolesController {

	@Autowired
	private RolesRepo rolerepo;
	@Autowired(required = false)
	private BCryptPasswordEncoder bcryptpasswordencoder;

	@PostMapping("/join")
	public String joingroup(@RequestBody Roles roles) {
		roles.setRoles(RoleConstant.DEFAULT_ROLE);
		String encryptedpwd;

		encryptedpwd = bcryptpasswordencoder.encode(roles.getPassword());

		roles.setPassword(encryptedpwd);
		rolerepo.save(roles);
		return "Hi" + " "+ roles.getUsername() + " "+ "welcome to group";

	}

	@GetMapping("/access/{id}/{userRoles}")
	//@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATE')")
	public String giveAccesstouser(@PathVariable int id, @PathVariable String userRoles, Principal principal) {
		Roles roles = rolerepo.findById(id).get();

		List<String> activeRoles = getRolesByLoggedInUser(principal);

		String newRole = "";
		if (activeRoles.contains(userRoles)) {
			newRole = roles.getRoles() + "," + userRoles;
			roles.setRoles(newRole);
		}
		rolerepo.save(roles);
		return "Hi" + ' '+ roles.getUsername()+ ' ' +  "new role assign to you by "+ ' ' + principal.getName();
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Roles> loadUsers() {
		return rolerepo.findAll();
	}

	@GetMapping("/test")
	public String testUserAccess() {
		return "user can access only this !";
	}

	private List<String> getRolesByLoggedInUser(Principal principal) {
		String roles = getloggedInUser(principal).getRoles();
		List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
		if (assignRoles.contains("ROLE_ADMIN")) {
			Arrays.stream(RoleConstant.ADMIN_ACCESS).collect(Collectors.toList());
		}
		if (assignRoles.contains("MODERATOR_ACCESS")) {
			Arrays.stream(RoleConstant.MODERATE_ACCESS).collect(Collectors.toList());

		}
		return Collections.emptyList();
	}

	private Roles getloggedInUser(Principal principal) {
		return rolerepo.findByusername(principal.getName()).get();

	}
}
