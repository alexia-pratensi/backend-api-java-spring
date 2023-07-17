package fr.alexia.backendapi.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

		// Use the following line is password are not encrypted in database.
		// user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		// user.setAuthorities(getGrantedAuthorities());
		if (user == null) {
			throw new UsernameNotFoundException("Utilisateur non trouvé avec l'e-mail : " + username);
		}

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				getGrantedAuthorities());
	}

	private List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// Spring Security automatically prefix role with ROLE_
		// so if the role name in database isn't prefix with ROLE_
		// we have to it
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

}
// proposition chat GPT
// @Override
// public UserDetails loadUserByUsername(String username) throws
// UsernameNotFoundException {
// Optional<InternalUser> userOptional = userRepository.findByName(username);
// InternalUser user = userOptional.orElseThrow(() -> new
// UsernameNotFoundException("User not found"));

// return new org.springframework.security.core.userdetails.User(
// user.getName(),
// user.getPassword(), // Le mot de passe est déjà encodé dans la base de
// données, pas besoin de le
// // réencoder ici
// getGrantedAuthorities());
// }