package fr.alexia.backendapi.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		InternalUser user = userRepository.findByName(username).get();

		//Use the following line is password are not encrypted in database.
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		
		return new org.springframework.security.core.userdetails.User(
				user.getName(), 
				user.getPassword(), 
				getGrantedAuthorities());
	}
	
	private List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//Spring Security automatically prefix role with ROLE_
		//so if the role name in database isn't prefix with ROLE_
		//we have to it
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
	
}
