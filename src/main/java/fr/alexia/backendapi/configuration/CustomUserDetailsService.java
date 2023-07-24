package fr.alexia.backendapi.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

		if (user == null) {
			throw new UsernameNotFoundException("User not found with name: " + username);
		}

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				getGrantedAuthorities());
	}

	private List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

}
