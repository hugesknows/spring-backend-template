package com.treecare.backend.utils.payload.reponse;

public class GenericResponse
{
    public final static int STATUS_SUCCESS = 1;
    public final static int STATUS_ERROR = 2;
    protected String message;
    protected int status = STATUS_SUCCESS;

    public int getStatus()
    {
        return status;
    }

    public GenericResponse()
    {
    }

    public GenericResponse(String message, int status)
    {
        this.message = message;
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}
