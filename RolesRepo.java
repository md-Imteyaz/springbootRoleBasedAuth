package com.SpringbootRollAuth.Repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.SpringbootRollAuth.Entity.Roles;

@Repository
@Component
public interface RolesRepo extends JpaRepository<Roles, Integer> {

	Optional<Roles> findByusername(String username);

}
