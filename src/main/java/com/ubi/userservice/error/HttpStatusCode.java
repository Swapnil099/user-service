package com.ubi.userservice.error;

public enum HttpStatusCode {

    RESOURCE_NOT_FOUND(404, "Does not exist"),
    RESOURCE_ALREADY_DELETED(410, "Does not exist"),
    RESOURCE_ALREADY_EXISTS(409, "Already exists"),
    ROLE_NOT_EXISTS(110, "Given Role Type Not Exist"),
    USERNAME_NOT_AVAILAIBLE(110, "Username Not Availaible"),
    ROLETYPE_NOT_AVAILAIBLE(110, "Role Type Not Availaible"),
    USER_NOT_EXISTS(110, "User with given roleType not exist"),
    PRINCIPAL_NOT_EXISTS(110, "Principal With Given ID Not Exist"),
    TEACHER_NOT_EXISTS(110, "Teacher With Given ID Not Exist"),
    REGION_ADMIN_NOT_EXISTS(110, "Regional Admin With Given ID Not Exist"),

    INSTITUTE_ADMIN_NOT_EXISTS(110, "Institute Admin With Given ID Not Exist"),


    INVALID_CREDENTIALS(121, "Invalid credentials provided"),

    BAD_REQUEST_EXCEPTION(400, "Bad Request Occuured"),

    UNAUTHORIZED_EXCEPTION(400, "Unauthorized To Perform Request"),
    TOKEN_EXPIRED(401, "Token Is Expired"),
    REFRESH_TOKEN_EXPIRED(402, "Refresh Token Is Expired"),
    TOKEN_FORMAT_INVALID(403, "Token Format Is Invalid"),
    INVALID_TOKEN(404, "Token Is Invalid"),
    TOKEN_NOT_FOUND(405, "Token Not Found"),

    PERMISSION_DENIED(406, "User Dont Have Permission To Perform This Reqeust"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error Occured"),


    SUCCESSFUL(200, "Request Successfull"),

    RESOURCE_CREATED_SUCCESSFULLY(201, "Resource Created Successfully"),

    NO_CONTENT(204, "No Content Found");

    private int code;
    private String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}