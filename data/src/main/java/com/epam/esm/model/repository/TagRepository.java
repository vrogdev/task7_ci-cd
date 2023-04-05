package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    /*    @Query("select t from Order o " +
            "join o.certificate c " +
            "join c.tags t " +
            "group by t.id order by count(t.id) desc, sum(o.cost) desc")*/

    @Query(value = "select t.id,t.name from gift_certificate gc\n" +
            "    join gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id\n" +
            "    join tag t on gcht.tag_id = t.id\n" +
            "    join orders o on gc.id = o.gift_certificate_id\n" +
            "    group by t.id order by count(t.id) desc, sum(o.cost) desc limit 1", nativeQuery = true)
    Optional<Tag> findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();

    boolean existsTagByName(String name);
    Tag findByName(String name);
}
