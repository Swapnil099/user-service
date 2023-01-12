package com.ubi.userservice.error;

public enum HttpStatusCode {

    NO_ENTRY_FOUND(101,"Resource Not Found"),

    NO_STUDENT_NAME_FOUND(202,"No student name found"),

    NO_SCHOOL_NAME_FOUND(202, "No School Name Found"),

    NO_STUDENT_FOUND(108,"No Student Found"),

    NO_STUDENT_MATCH_WITH_ID(109,"No such student found with such id"),

    NO_CLASS_MATCH_WITH_ID(109,"No such class found with such id"),

    NO_PAYMENT_FOUND(108,"No Payment Found"),

    NO_PAYMENT_MATCH_WITH_ID(109,"No Payment found with given Id "),

    PAYMENT_RETRIVED_SUCCESSFULLY(200,"Payment Retrived"),


    NO_CLASSCODE_FOUND(202,"No class code found"),

    NO_CLASS_FOUND(108,"No class found"),


    NO_CONTACTINFO_FOUND(108,"No contact info found"),

    NO_EDUCATIONAL_INSTITUTION_FOUND(108,"No Educational Institution Found"),

    NO_EDUCATIONAL_INSTITUTION_MATCH_WITH_ID(109,"No Educational Institution found with given Id "),

    NO_EDUCATIONAL_INSTITUTION_NAME_FOUND(202,"No Educational Institution Name Found"),

    EDUCATIONAL_INSTITUTION_RETRIVED_SUCCESSFULLY(200,"Educational Institution Retrived"),

    NO_TRANSFERCERTIFICATE_FOUND(108, "No Transfer Certificate Found"),

    NO_CONTACTINFO_MATCH_WITH_ID(109,"No such contact info found with such id"),


    NO_SCHOOL_MATCH_WITH_ID(109, "No School Found with Given ID"),

    NO_SCHOOL_FOUND(108, "No School Found"),

    NO_SCHOOL_MATCH_WITH_NAME(110, "No School Found With Given NAME"),



    RESOURCE_NOT_FOUND(108, "Does not exist"),

    RESOURCE_ALREADY_EXISTS(110, "Already exists"),
    ROLE_NOT_EXISTS(110, "Given Role Type Not Exist"),
    USERNAME_NOT_AVAILAIBLE(110, "Username Not Availaible"),
    ROLETYPE_NOT_AVAILAIBLE(110, "Role Type Not Availaible"),
    USER_NOT_EXISTS(110, "User with given roleType not exist"),
    PRINCIPAL_NOT_EXISTS(110, "Principal With Given ID Not Exist"),
    TEACHER_NOT_EXISTS(110, "Teacher With Given ID Not Exist"),


    INVALID_COLUMN_NAME(111, "Invalid column name provided"),

    WRONG_DATA_TYPE(112, "Wrong datatype selected for non multivalued field"),

    IO_EXCEPTION(113, "I/O exception occurred"),

    JSON_PARSE_EXCEPTION(114, "JSON parse error occurred"),

    INVALID_FIELD_VALUE(116, "Value for field : {} is not expected as : {}"),

    CONNECTION_REFUSED(120, "Connection is refused from the server"),

    INVALID_CREDENTIALS(121, "Invalid credentials provided"),

    BAD_REQUEST_EXCEPTION(400, "Bad Request Occuured"),

    NULL_POINTER_EXCEPTION(500, "Received Null response"),

    SERVER_UNAVAILABLE(503, "Unable to Connect To the Server"),

    OPERATION_NOT_ALLOWED(405, "Operation is Not Allowed"),

    UNAUTHORIZED_EXCEPTION(400, "Unauthorized To Perform Request"),
    TOKEN_EXPIRED(401, "Token Is Expired"),
    REFRESH_TOKEN_EXPIRED(402, "Refresh Token Is Expired"),
    TOKEN_FORMAT_INVALID(403, "Token Format Is Invalid"),
    INVALID_TOKEN(404, "Token Is Invalid"),
    TOKEN_NOT_FOUND(405, "Token Not Found"),

    PERMISSION_DENIED(406, "User Dont Have Permission To Perform This Reqeust"),

    FORBIDDEN_EXCEPTION(403, "Forbidden access attempted"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error Occured"),

    PROCESSING_NOT_COMPLETED(202, "Request cannot be Processed"),

    NOT_ACCEPTABLE_ERROR(406, "Request Not accpetable"),

    SUCCESSFUL(200, "Request Successfull"),

    CONTACTINFO_DELETED(200,"contact info deleted successfully"),

    TRANSFER_CERTIFICATE_DELETED(200, "Transfer Certificate Deleted Successfully"),

    RETREIVED_SUCCESSFULLY(200, "Data Retrieved Successfully"),

    STUDENT_DELETED(200, "Student Deleted Successfully"),

    CONTACTINFO_UPDATED(200,"Contact info updated successfully"),


    STUDENT_UPDATED(200, "Student Updated Successfully"),

    CLASS_DELETED(200, "class deleted successfully"),

    CLASS_UPDATED(200, "Class updated successfully"),

    PAYMENT_UPDATED(200, "Payment updated successfully"),
    PAYMENT_DELETED(200,"Payment deleted successfully"),

    EDUCATIONAL_INSTITUTION_DELETED(200,"Educational Institution deleted successfully"),
    EDUCATIONAL_INSTITUTION_UPDATED(200,"Educational Institution updated successfully"),


    SCHOOL_DELETED(200,"School Deleted Successfully"),
    SCHOOL_UPDATED(200,"School Updated Successfully"),




    RESOURCE_CREATED_SUCCESSFULLY(201, "Resource Created Successfully"),


    TRANSFER_CERTIFICATE_UPDATED(200, "Transfer Certificate Updated Successfully"),






    REGION_RETREIVED_SUCCESSFULLY(200,"Region Retrieved Succesfully"),

    REGION_DELETED_SUCCESSFULLY(200,"Region Deleted SuccessFully"),

    REGION_NOT_FOUND(108,"No such region found"),

    REGION_UPDATED(200,"Region Updated Successfully"),

    MAPPING_ALREADY_EXIST(108,"Mapping Already Exist");

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