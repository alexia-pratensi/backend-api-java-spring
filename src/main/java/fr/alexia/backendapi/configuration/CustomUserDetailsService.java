package fr.alexia.backendapi.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;// accéder aux données des utilisateurs dans la base de données
    @Autowired
    private PasswordEncoder passwordEncoder;// encoder les mots de passe utilisateur
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // charger les informations utilisateur à partir du nom d'utilisateur
        InternalUser user = userService.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // mot de passe de l'utilisateur est encodé à l'aide de l'objet passwordEncoder
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // informations utilisateur sont mappées à un objet User de Spring Security en
        // utilisant org.springframework.security.core.userdetails.User.
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                getGrantedAuthorities(user));
    }

    // Les rôles ou autorisations de l'utilisateur sont ajoutés à la liste des
    // autorisations accordées (GrantedAuthority)
    private List<GrantedAuthority> getGrantedAuthorities(InternalUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // si l'utilisateur a une location (rental), il est considéré comme un
        // propriétaire (ROLE_OWNER), sinon il est considéré comme un utilisateur normal
        // (ROLE_USER)
        if (user.getRental() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}