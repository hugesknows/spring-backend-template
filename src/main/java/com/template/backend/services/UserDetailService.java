package com.template.backend.services;

import com.template.backend.user.User;
import com.template.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailAddress) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(usernameOrEmailAddress);
        if (user == null)
        {
            user = userRepository.findByEmailAddress(usernameOrEmailAddress);
        }
        if (user == null)
        {
            throw (new UsernameNotFoundException("User Not Found with email or username: " + usernameOrEmailAddress));
        }

        return UserDetail.build(user);
    }
}
