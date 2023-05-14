package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private GiftCertificateServiceImpl service;


    @Test
    void getAllCertificates() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<GiftCertificate> certificates = new PageImpl<>(Arrays.asList(new GiftCertificate()));

        when(certificateRepository.findAll(pageable)).thenReturn(certificates);

        Page<GiftCertificate> actualCertificates = service.getAllCertificates(pageable);
        verify(certificateRepository).findAll(pageable);
        assertEquals(actualCertificates, certificates);
    }

    @Test
    void getCertificateById() {
        long certId = 1L;
        GiftCertificate certificate = new GiftCertificate();
        when(certificateRepository.findById(certId)).thenReturn(Optional.of(certificate));
        GiftCertificate actualCertificate = service.getCertificateById(certId);
        assertEquals(certificate, actualCertificate);
    }

    @Test
    void getCertificateByIdInvalid() {
        assertThrows(ServiceException.class, () -> service.getCertificateById(-1L));
        when(certificateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> service.getCertificateById(1L));

    }

    @Test
    void insertCertificate() {
        Tag newTag = new Tag(0, "tagNameNew");
        GiftCertificate newCertificate = new GiftCertificate(0, "giftCertificateNew",
                "description", 9999.99, 20,
                null, null,
                Collections.singletonList(newTag));

        when(tagRepository.existsTagByName(newTag.getName())).thenReturn(false);
        when(tagRepository.save(newTag)).thenReturn(newTag);
        when(certificateRepository.save(newCertificate)).thenReturn(newCertificate);

        service.insertCertificate(newCertificate);
    }

    @Test
    void removeCertificateById() {
        long certificateId = 1L;
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(certificateId);

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        doNothing().when(certificateRepository).deleteById(certificateId);

        service.removeCertificateById(certificateId);
        verify(certificateRepository).deleteById(certificateId);
    }

    @Test
    void updateCertificate() {
        long certificateId = 1L;
        long tagId = 2L;

        Tag tag = new Tag(tagId, "tagName1");

        GiftCertificate certificate = new GiftCertificate(certificateId, "giftCertificate1",
                "description1", 100.50, 10,
                LocalDateTime.parse("2023-04-01T08:00:00.000"), LocalDateTime.parse("2023-04-01T08:00:00.000"),
                Collections.singletonList(tag));

        certificate.setId(certificateId);

        when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        service.updateCertificate(certificateId, certificate);

        verify(certificateRepository).findById(certificateId);
        verify(tagRepository).findById(tagId);
    }

    @Test
    void updateCertificateInvalid() {
        assertThrows(ServiceException.class, () -> service.updateCertificate(-1L, null));
        assertThrows(ServiceException.class, () -> service.updateCertificate(1L, new GiftCertificate()));
    }

    @Test
    void getCertificatesByPartOfName() {
        PageRequest page = PageRequest.of(0, 5);
        String partialName = "someName";

        Page<GiftCertificate> certificates = new PageImpl<>(Arrays.asList(new GiftCertificate()));

        when(certificateRepository.getCertificatesWithTagsByPartOfName(partialName, page)).thenReturn(certificates);
        Page<GiftCertificate> actualCertificates = service.getCertificatesByPartOfName(partialName, page);
        assertEquals(certificates, actualCertificates);
    }

    @Test
    void getCertificatesByPartOfNameWhenEmpty() {
        PageRequest page = PageRequest.of(1, 5);
        String partialName = "someName";

        Page<GiftCertificate> certificates = new PageImpl<>(new ArrayList<>());

        when(certificateRepository.getCertificatesWithTagsByPartOfName(partialName, page)).thenReturn(certificates);
        assertThrows(ServiceException.class, () -> service.getCertificatesByPartOfName(partialName, page));
    }

    @Test
    void getCertificatesByPartOfDescription() {
        PageRequest page = PageRequest.of(0, 5);
        String partialDesc = "someDesc";

        Page<GiftCertificate> certificates = new PageImpl<>(Arrays.asList(new GiftCertificate()));

        when(certificateRepository.getCertificatesWithTagsByPartOfDescription(partialDesc, page)).thenReturn(certificates);
        Page<GiftCertificate> actualCertificates = service.getCertificatesByPartOfDescription(partialDesc, page);
        assertEquals(certificates, actualCertificates);
    }

    @Test
    void getCertificatesByPartOfTagName() {
        PageRequest page = PageRequest.of(0, 5);
        String partialTagName = "someTagName";

        Page<GiftCertificate> certificates = new PageImpl<>(Arrays.asList(new GiftCertificate()));

        when(certificateRepository.getCertificatesWithTagsByPartOfTagName(partialTagName, page)).thenReturn(certificates);
        Page<GiftCertificate> actualCertificates = service.getCertificatesByPartOfTagName(partialTagName, page);
        assertEquals(certificates, actualCertificates);
    }
}