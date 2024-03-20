package cb.task.backend.service;

import cb.task.backend.entity.AppUser;
import cb.task.backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = userRepository.findByLogin(login);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username %s does not exist".formatted(login));
        }
        AppUser appUser = userOptional.get();

        return new User(appUser.getLogin(), appUser.getPassword(), getAuthorities(appUser));
    }

    Collection<? extends GrantedAuthority> getAuthorities(AppUser appUser) {
        return appUser
                .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole().getName().name()))
                .toList();
    }
}