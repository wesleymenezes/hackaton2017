package com.ericsson.edb.hackaton.heroku.services.support;

import javax.ws.rs.core.Response;

public class ErrorMessage {

    private int statusCode;
    private String statusDescription;
    private String message;

    /**
     * Creates an ErrorMessage instance that can be returned in the body of an Http error response.
     * 
     * @param responseStatus The Http status
     * @param errorMessage A custom error message
     */
    public ErrorMessage(Response.Status responseStatus, String errorMessage) {
        this.statusCode = responseStatus.getStatusCode();
        this.statusDescription = responseStatus.getReasonPhrase();
        this.message = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getErrorMessage() {
        return message;
    }

    public void setErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
