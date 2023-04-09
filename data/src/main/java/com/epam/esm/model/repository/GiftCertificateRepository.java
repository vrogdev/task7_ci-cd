package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * {@code GiftCertificateRepository} describes operations with GiftCertificate entity extending JPA repository to work with database tables.
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
     /**
     * Method for getting list of certificates by part of certificate's description from table.
     * @param certificateName part of name
     * @param page {@code Pageable} object contains parameters for page request
     * @return page of certificates
     */
    @Query("select g from GiftCertificate g where g.name like %:certificateName%")
    Page<GiftCertificate> getCertificatesWithTagsByPartOfName(String certificateName, Pageable page);

    /**
     * Method for getting list of certificates by part of certificate's name from table.
     * @param description part of description
     * @param page {@code Pageable} object contains parameters for page request
     * @return page of certificates
     */
    @Query("select g from GiftCertificate g where g.description like %:description%")
    Page<GiftCertificate> getCertificatesWithTagsByPartOfDescription(String description, Pageable page);

    /**
     * Method for getting list of certificates by part of certificate's name from table.
     * @param tagName part of {@code Tag} name
     * @param page {@code Pageable} page object contains parameters for page request
     * @return page of certificates
     */
    @Query("select g from GiftCertificate g join g.tags t where t.name like %:tagName%")
    Page<GiftCertificate> getCertificatesWithTagsByPartOfTagName(String tagName, Pageable page);
}
