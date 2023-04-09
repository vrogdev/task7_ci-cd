package com.epam.esm.service.util;

import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IdentifiableValidatorTest {
    @Test
    void validateIdIncorrect() {
        assertThrows(ServiceException.class, () -> IdentifiableValidator.validateId(0));
        assertThrows(ServiceException.class, () -> IdentifiableValidator.validateId(-1L));
    }
}