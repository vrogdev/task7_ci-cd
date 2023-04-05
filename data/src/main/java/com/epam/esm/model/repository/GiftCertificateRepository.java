package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    @Query("select g from GiftCertificate g where g.name like %:certificateName%")
    Page<GiftCertificate> getCertificatesWithTagsByPartOfName(String certificateName, Pageable page);

    @Query("select g from GiftCertificate g where g.description like %:description%")
    Page<GiftCertificate> getCertificatesWithTagsByPartOfDescription(String description, Pageable page);

    @Query("select g from GiftCertificate g join g.tags t where t.name like %:tagName%")
    Page<GiftCertificate> getCertificatesWithTagsByTagPartOfName(String tagName, Pageable page);
}
