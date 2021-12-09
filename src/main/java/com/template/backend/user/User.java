package com.template.backend.user;

import com.template.backend.user.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "emailAddress")
        })
public class User implements UserDetails
{

    public static final int STATUS_ENABLED = 1;
    public static final int STATUS_SUSPENDED = 2;
    public static final int STATUS_TERMINATED = 3;

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;
    private String mobileNumber;
    private Date dateCreated;
    private Date lastLogin;
    private Date lastUpdated;
    @Lob
    @Column(nullable = false)
    private String encryptedPassword;
    @Column(nullable = false)
    private int statusId = 1;
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User()
    {
    }

    public User(String username, String encryptedPassword)
    {
    }

    public User(String username, String emailAddress, String encryptedPassword, String mobileNumber)
    {
        this.username = username;
        this.emailAddress = emailAddress;
        this.encryptedPassword = encryptedPassword;
        this.mobileNumber = mobileNumber;
        this.lastLogin = new Date();
        this.lastUpdated = new Date();
        this.dateCreated = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    public String getPassword()
    {
        return encryptedPassword;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return statusId == User.STATUS_ENABLED;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }


    @Override
    public boolean isEnabled()
    {
        return statusId == User.STATUS_ENABLED;
    }

    public Long getId()
    {
        return id;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEncryptedPassword(String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Date getLastLogin()
    {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public int getStatusId()
    {
        return statusId;
    }

    public void setStatusId(int statusId)
    {
        this.statusId = statusId;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
