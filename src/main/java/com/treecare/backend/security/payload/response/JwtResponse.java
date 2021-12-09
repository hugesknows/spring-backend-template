package com.treecare.backend.security.payload.response;

import com.treecare.backend.utils.payload.reponse.GenericResponse;

import java.util.List;

public class JwtResponse extends GenericResponse
{
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailAddress;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String firstName, String lastName, String mobileNumber, String email, List<String> roles)
    {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = email;
        this.roles = roles;
    }

    public JwtResponse(String errorMessage, int statusError)
    {
        this.message = errorMessage;
        this.status = statusError;
    }


    public String getTokenType()
    {
        return type;
    }

    public void setTokenType(String tokenType)
    {
        this.type = tokenType;
    }

    public Long getId()
    {
        return id;
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
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

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public void setRoles(List<String> roles)
    {
        this.roles = roles;
    }
}
