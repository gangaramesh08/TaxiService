package com.test.taxiservice.documentservice.common;

public class Constants {

    public static final String BASE_PACKAGE = "com.test.taxiservice";

    public static final String DOCUMENT_SERVICE_BASE_URL = "/driver/documentservice";
    public static final String DOCUMENT_UPDATE_URL = "/update";
    public static final String HEALTH_CHECK_URL = "/healthcheck";
    public static final String INITIATE_DRIVER_BACKGROUND_CHECK_URL = "/backgroundcheck/initiate";
    public static final String UPDATE_DRIVER_BACKGROUND_CHECK_STATUS_URL = "/backgroundcheck/update";

    public static final String DOCUMENTS = "Documents";
    public static final String SUCCESS = "Success";
    public static final String ACCEPTED_RESPONSE = "Accepted";
    public static final String DOCUMENT_NAME_REGEX = "^([a-zA-Z0-9\\s\\._-]+)$";
    public static final String DOCUMENT_NAME = "documentName";
    public static final String DOCUMENT_TYPE = "documentType";
}
