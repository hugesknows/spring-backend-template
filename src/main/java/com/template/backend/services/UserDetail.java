package com.template.backend.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.template.backend.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetail(Long id, String username, String emailAddress, String firstName, String lastName, String mobileNumber, String password, Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetail build(User user)
    {
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetail(
                user.getId(),
                user.getUsername(),
                user.getEmailAddress(),
                user.getFirstName(),
                user.getLastName(),
                user.getMobileNumber(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    public Long getId()
    {
        return id;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetail user = (UserDetail) o;
        return Objects.equals(id, user.id);
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }
}
