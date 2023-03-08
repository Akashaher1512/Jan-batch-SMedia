package com.media;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SMediaApplication.class, args);
		
	
		
	}
	
	
	
	
	@Bean
	public ModelMapper modelmapper() {
		
		return new ModelMapper();
	}
	
	/*
	 * @Autowired private RoleRepository roleRepository;
	 * 
	 * @Override public void run(String... args) throws Exception {
	 * 
	 * Role adminRole = new Role(); adminRole.setName("ROLE_ADMIN");
	 * roleRepository.save(adminRole);
	 * 
	 * Role userRole = new Role(); userRole.setName("ROLE_USER");
	 * roleRepository.save(userRole);
	 * 
	 * 
	 * }
	 */

}



