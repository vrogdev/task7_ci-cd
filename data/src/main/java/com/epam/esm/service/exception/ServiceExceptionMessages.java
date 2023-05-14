package com.epam.esm.service.exception;

public final class ServiceExceptionMessages {

    public static final String BAD_ID="Bad ID";
    public static final String BAD_CERTIFICATE_ID="No GiftCertificate found by provided id ";
    public static final String BAD_TAG_NAME="Bad tag name";
    public static final String BAD_CERTIFICATE_NAME="Bad gift certificate name";
    public static final String BAD_CERTIFICATE_DESC="Bad gift certificate description";
    public static final String BAD_CERTIFICATE_PRICE="Bad gift certificate price";
    public static final String BAD_CERTIFICATE_DURATION="Bad gift certificate duration";
    public static final String NO_PARAMS_TO_UPDATE="No parameters for certificate update";

    public static final String ID_NOT_FOUND="Object with this ID not found";
    public static final String NAME_NOT_FOUND="Objects with this name not found";
    public static final String TAG_NOT_MATCH="Specified tag in GiftCertificate doesn't match the existed by name";
    public static final String TAG_NOT_FOUND="No tag found";
    public static final String USER_ID_NOT_FOUND="No user found by provided id ";
    public static final String ORDER_NOT_FOUND_FOR_USER_ID="No orders found for provided user id";
}
