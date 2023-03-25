package com.github.yildizmy.common;

/**
 * Constant variables used in the project
 */
public final class Constants {

    private Constants() {
    }

    public static final String TRACE = "trace";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static final String VALIDATION_ERROR = "Validation error. Check 'errors' field for details";
    public static final String METHOD_ARGUMENT_NOT_VALID = "MethodArgumentNotValid exception";
    public static final String DELETED_USER = "User is deleted";
    public static final String CANNOT_SET_AUTH = "Cannot set user authentication: {}";
    public static final String INVALID_JWT_SIGN = "Invalid JWT signature: {}";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token: {}";
    public static final String JWT_EXPIRED = "JWT token is expired: {}";
    public static final String JWT_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String JWT_EMPTY = "JWT claims string is empty: {}";
    public static final String SUCCESS = "Success";
    public static final String NOT_FOUND = "Requested element is not found";
    public static final String NOT_FOUND_RECORD = "Not found any record";
    public static final String NOT_FOUND_TYPE = "Requested type is not found";
    public static final String NOT_FOUND_PET = "Requested pet is not found";
    public static final String NOT_FOUND_USER = "Requested user is not found";
    public static final String NOT_FOUND_USERNAME = "User with username of {0} is not found";
    public static final String ALREADY_EXISTS = "Requested element is already exists";
    public static final String ALREADY_EXISTS_TYPE = "Type with the same name is already exists";
    public static final String ALREADY_EXISTS_USER = "User with the same username is already exists";
    public static final String CREATED_TYPE = "Type is created";
    public static final String CREATED_PET = "Pet is created";
    public static final String CREATED_USER = "User is created";
    public static final String UPDATED_TYPE = "Type is updated";
    public static final String UPDATED_PET = "Pet is updated";
    public static final String UPDATED_USER = "User is updated";
    public static final String DELETED_TYPE = "Type is deleted";
    public static final String DELETED_PET = "Pet is deleted";

}
