package com.blog.application.project;

import com.blog.application.project.Model.Role;
import com.blog.application.project.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setRoleId(501);
			role.setName("ROLE_ADMIN");
			roleRepository.save(role);

			Role role1 = new Role();
			role1.setRoleId(502);
			role1.setName("ROLE_NORMAL");
			roleRepository.save(role1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
