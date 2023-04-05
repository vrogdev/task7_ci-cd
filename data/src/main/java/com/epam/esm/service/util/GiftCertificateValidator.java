package com.epam.esm.service.util;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceExceptionMessages;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * This class provides a validator for {@link GiftCertificate} entity.
 */
public class GiftCertificateValidator {
    private static final int MAX_LENGTH_NAME = 30;
    private static final int MIN_LENGTH_NAME = 3;
    private static final int MIN_LENGTH_DESCRIPTION = 5;
    private static final int MAX_LENGTH_DESCRIPTION = 100;
    private static final int MAX_SCALE = 2;
    private static final Double MIN_PRICE = 0.01;
    private static final Double MAX_PRICE = 999999.99;
    private static final int MAX_DURATION = 366;
    private static final int MIN_DURATION = 1;

    /**
     * Validates all fields of an {@link GiftCertificate} entity.
     *
     * @param item an {@link GiftCertificate} entity for validating.
     * @throws ServiceException if the entity contains incorrect field values.
     */
    public static void validate(GiftCertificate item) throws ServiceException {
        checkIfEmpty(item);
        validateName(item.getName());
        validateDescription(item.getDescription());
        validatePrice(item.getPrice());
        validateDuration(item.getDuration());
        validateListOfTags(item.getTags());
    }

    /**
     * Validates exists fields of an {@link GiftCertificate} entity. If some fields do not contain values, validation is not interrupted.
     *
     * @param item an {@link GiftCertificate} entity for validating.
     * @throws ServiceException if the entity contains incorrect field values.
     */
    public static void validateForUpdate(GiftCertificate item) throws ServiceException {
        checkIfEmpty(item);

        if (item.getName() != null) {
            validateName(item.getName());
        }
        if (item.getDescription() != null) {
            validateDescription(item.getDescription());
        }
        if (item.getPrice() != null) {
            validatePrice(item.getPrice());
        }
        if (item.getDuration() != 0) {
            validateDuration(item.getDuration());
        }
        validateListOfTags(item.getTags());
    }

    /**
     * Validates exist tags of {@link GiftCertificate} entity.
     *
     * @param tags a {@link List} of {@link Tag}.
     * @throws ServiceException if some tag has incorrect name.
     */
    public static void validateListOfTags(List<Tag> tags) throws ServiceException {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag);
        }
    }

    private static void validateName(String name) throws ServiceException {
        if (name == null
                || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new ServiceException(ServiceExceptionMessages.BAD_CERTIFICATE_NAME,
                    ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_NAME,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateDescription(String description) throws ServiceException {
        if (description == null
                || description.length() < MIN_LENGTH_DESCRIPTION || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw new ServiceException(ServiceExceptionMessages.BAD_CERTIFICATE_DESC,
                    ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_DESCRIPTION,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private static void validatePrice(Double price) throws ServiceException {
        if (price == null || price < 0) {
            throw new ServiceException(ServiceExceptionMessages.BAD_CERTIFICATE_PRICE,
                    ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_PRICE,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateDuration(int duration) throws ServiceException {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            throw new ServiceException(ServiceExceptionMessages.BAD_CERTIFICATE_DURATION,
                    ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_DURATION,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private static void checkIfEmpty(GiftCertificate item) throws ServiceException {
        if (item.getName() == null
                && item.getDescription() == null
                && item.getPrice() == null
                && item.getDuration() < MIN_DURATION
                && item.getTags() == null) {
            throw new ServiceException(ServiceExceptionMessages.NO_PARAMS_TO_UPDATE,
                    ServiceExceptionCodes.EMPTY_CERTIFICATE,
                    HttpStatus.BAD_REQUEST);
        }
    }
}
