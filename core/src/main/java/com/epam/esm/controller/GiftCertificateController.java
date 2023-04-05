package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.interfaces.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final DtoConverter<GiftCertificate, GiftCertificateDto> certificateConverter;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService, DtoConverter<GiftCertificate, GiftCertificateDto> certificateConverter, PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler) {
        this.certificateService = certificateService;
        this.certificateConverter = certificateConverter;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public GiftCertificate getCertificateById(@PathVariable long id) {
        return certificateService.getCertificateById(id);
    }

    @GetMapping
    public PagedModel<GiftCertificateDto> getAllCertificates(
            @PageableDefault(
                    sort = {"id"},
                    size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getAllCertificates(page);

        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    @GetMapping(value = "/name/{name}")
    public PagedModel<GiftCertificateDto> getByName(@PathVariable String name,
                                                    @PageableDefault(
                                                            sort = {"id"},
                                                            size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getCertificatesByPartOfName(name, page);

        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    @GetMapping(value = "/description/{description}")
    public PagedModel<GiftCertificateDto> getByDescription(@PathVariable String description,
                                                           @PageableDefault(
                                                                   sort = {"id"},
                                                                   size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getCertificatesByPartOfDescription(description, page);
        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    @GetMapping(value = "/tagname/{tagName}")
    public PagedModel<GiftCertificateDto> getByTagName(@PathVariable String tagName,
                                                       @PageableDefault(
                                                               sort = {"id"},
                                                               size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getCertificatesByPartOfTagName(tagName, page);
        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    @PostMapping
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificate certificate) {
        certificateService.insertCertificate(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificateById(@PathVariable long id) throws ServiceException {
        certificateService.removeCertificateById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Entity with id = " + id + " removed successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificate certificate) {
        certificateService.updateCertificate(id, certificate);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
}
