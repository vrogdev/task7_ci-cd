package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceExceptionMessages;
import com.epam.esm.service.interfaces.GiftCertificateService;
import com.epam.esm.service.util.GiftCertificateValidator;
import com.epam.esm.service.util.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<GiftCertificate> getAllCertificates(Pageable page) {
        Page<GiftCertificate> certificatePage = certificateRepository.findAll(page);
        checkPageBounds(page, certificatePage);

        return certificatePage;
    }

    private static void checkPageBounds(Pageable page, Page<GiftCertificate> certificatePage) {
        int pageNumber = page.getPageNumber();
        int totalPages = certificatePage.getTotalPages();

        if(pageNumber >= totalPages)
            throw new ServiceException(
                    "Page out of bounds",
                    ServiceExceptionCodes.BAD_ID,
                    HttpStatus.BAD_REQUEST);
    }

    @Override
    public GiftCertificate getCertificateById(long id) {
        return getCertificateIfExistsById(id);
    }

    @Override
    @Transactional
    public void insertCertificate(GiftCertificate item) {
        insertNewTags(item);

        item.setCreateDate(LocalDateTime.now());
        item.setLastUpdateDate(LocalDateTime.now());

        GiftCertificateValidator.validate(item);
        certificateRepository.save(item);
    }

    private void insertNewTags(GiftCertificate item) {
        List<Tag> tags = item.getTags();
        if (tags != null) {
            tags.forEach(tag -> {
                if (tag.getId() != 0) {
                    Tag tagFromDB = tagRepository.findById(tag.getId())
                            .orElseThrow(() -> new ServiceException(ServiceExceptionMessages.TAG_NOT_FOUND, ServiceExceptionCodes.BAD_ID, HttpStatus.NOT_FOUND));

                    if (!tagFromDB.getName().equals(tag.getName())) {
                        throw new ServiceException(ServiceExceptionMessages.TAG_NOT_MATCH, ServiceExceptionCodes.BAD_TAG_NAME, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    if (tagRepository.existsTagByName(tag.getName())) {
                        Tag tagFromDB = tagRepository.findByName(tag.getName())
                                .orElseThrow(() -> new ServiceException("Tag with given name not found in db"));
                        tag.setId(tagFromDB.getId());

                    } else {
                        tagRepository.save(tag);
                    }
                }
            });
        }
    }

    @Override
    public void removeCertificateById(long id) {
        getCertificateIfExistsById(id);
        certificateRepository.deleteById(id);
    }

    @Override
    public void updateCertificate(long id, GiftCertificate item) {
        GiftCertificate certificateFromDB = getCertificateIfExistsById(id);

        GiftCertificateValidator.validateForUpdate(item);
        updateGiftCertificateFields(item, certificateFromDB);
    }

    private GiftCertificate getCertificateIfExistsById(long id) {
        IdentifiableValidator.validateId(id);
        return certificateRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionMessages.ID_NOT_FOUND, ServiceExceptionCodes.NO_ENTITY_WITH_ID, HttpStatus.NOT_FOUND));
    }

    private void updateGiftCertificateFields(GiftCertificate item, GiftCertificate certificate) {
        if (item.getName() != null) {
            certificate.setName(item.getName());
        }
        if (item.getDescription() != null) {
            certificate.setDescription(item.getDescription());
        }
        if (item.getPrice() != null) {
            certificate.setPrice(item.getPrice());
        }
        if (item.getDuration() != 0) {
            certificate.setDuration(item.getDuration());
        }

        if (item.getTags() != null) {
            insertNewTags(item);
            certificate.setTags(item.getTags());
        }
        item.setLastUpdateDate(LocalDateTime.now());
    }

    @Override
    public Page<GiftCertificate> getCertificatesByPartOfName(String name, Pageable page) {
        Page<GiftCertificate> certificates = certificateRepository.getCertificatesWithTagsByPartOfName(name, page);
        checkPageBounds(page, certificates);
        checkIfCertificatesExists(certificates);
        return certificates;
    }

    @Override
    public Page<GiftCertificate> getCertificatesByPartOfDescription(String description, Pageable page) {
        Page<GiftCertificate> certificates = certificateRepository.getCertificatesWithTagsByPartOfDescription(description, page);
        checkPageBounds(page, certificates);
        checkIfCertificatesExists(certificates);
        return certificates;
    }

    @Override
    public Page<GiftCertificate> getCertificatesByPartOfTagName(String tagNames, Pageable page) {
        Page<GiftCertificate> certificates = certificateRepository.getCertificatesWithTagsByPartOfTagName(tagNames, page);
        checkPageBounds(page, certificates);
        checkIfCertificatesExists(certificates);
        return certificates;
    }


    private void checkIfCertificatesExists(Page<GiftCertificate> certificates) {
        if (certificates.isEmpty()) {
            throw new ServiceException(ServiceExceptionMessages.NAME_NOT_FOUND, ServiceExceptionCodes.NO_ENTITY_WITH_NAME, HttpStatus.NOT_FOUND);
        }
    }

}
