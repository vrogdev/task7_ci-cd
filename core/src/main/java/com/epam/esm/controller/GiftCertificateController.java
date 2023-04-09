package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.model.entity.GiftCertificate;
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

/**
 * Class {@code GiftCertificateController} represents endpoint of API which allows you to perform
 * operations on gift certificates.
 */
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

    /**
     * This method retrieves all gift certificates with Page request.
     *
     * @param page page requested information
     * @return Page with listed certificates and hateoas information
     */
    @GetMapping
    public PagedModel<GiftCertificateDto> getAllCertificates(
            @PageableDefault(
                    sort = {"id"},
                    size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getAllCertificates(page);

        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    /**
     * This method is used to get GiftCertificate by id.
     *
     * @param id GiftCertificate's id
     * @return GiftCertificate
     */
    @GetMapping("/{id}")
    public GiftCertificate getCertificateById(@PathVariable long id) {
        return certificateService.getCertificateById(id);
    }

    /**
     * This method is used to get GiftCertificates by name.
     *
     * @param name search parameter
     * @param page page requested information
     * @return Page with listed certificates and hateoas information
     */
    @GetMapping(value = "/name/{name}")
    public PagedModel<GiftCertificateDto> getByName(@PathVariable String name,
                                                    @PageableDefault(
                                                            sort = {"id"},
                                                            size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getCertificatesByPartOfName(name, page);

        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    /**
     * This method is used to get GiftCertificates by description.
     *
     * @param description search parameter
     * @param page        page requested information
     * @return Page with listed certificates and hateoas information
     */
    @GetMapping(value = "/description/{description}")
    public PagedModel<GiftCertificateDto> getByDescription(@PathVariable String description,
                                                           @PageableDefault(
                                                                   sort = {"id"},
                                                                   size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getCertificatesByPartOfDescription(description, page);
        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    /**
     * This method is used to get GiftCertificates by description.
     *
     * @param tagName search parameter
     * @param page    page requested information
     * @return Page with listed certificates and hateoas information
     */
    @GetMapping(value = "/tagname/{tagName}")
    public PagedModel<GiftCertificateDto> getByTagName(@PathVariable String tagName,
                                                       @PageableDefault(
                                                               sort = {"id"},
                                                               size = 5) Pageable page) {
        Page<GiftCertificate> certificates = certificateService.getCertificatesByPartOfTagName(tagName, page);
        return pagedResourcesAssembler.toModel(certificates, certificateConverter);
    }

    /**
     * Creates a new {@link GiftCertificate} entity in the data source.
     *
     * @param certificate a new {@link GiftCertificate} entity for saving
     * @return string with Success result information
     */
    @PostMapping
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificate certificate) {
        certificateService.insertCertificate(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Delete from database a {@link GiftCertificate} by specified ID.
     *
     * @param id a {@link GiftCertificate} ID.
     * @return string with result information
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificateById(@PathVariable long id) {
        certificateService.removeCertificateById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Entity with id = " + id + " removed successfully");
    }

    /**
     * Updates a {@link GiftCertificate} by specified ID.
     *
     * @param id          a {@link GiftCertificate} ID.
     * @param certificate a {@link GiftCertificate} with updated information.
     * @return string with success result
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificate certificate) {
        certificateService.updateCertificate(id, certificate);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
}
