package com.epam.esm.service.interfaces;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GiftCertificateService {
    Page<GiftCertificate> getAllCertificates(Pageable page);

    GiftCertificate getCertificateById(long id) throws ServiceException;

    void insertCertificate(GiftCertificate item);

    void removeCertificateById(long id);

    void updateCertificate(long id, GiftCertificate item);

    Page<GiftCertificate> getCertificatesByPartOfName(String name, Pageable page);

    Page<GiftCertificate> getCertificatesByPartOfDescription(String description, Pageable page);

    Page<GiftCertificate> getCertificatesByPartOfTagName(String tagNames, Pageable page);

}
