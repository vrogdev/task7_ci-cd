package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@code TagRepository} describes operations with Tag entity extending JPA repository to work with database tables.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Method for retrieving most widely used {@code Tag} of {@code User} with the highest cost
     * of all orders from table.
     * @return {@code Tag} tag
     */
    @Query(value = "select t.id,t.name from gift_certificate gc\n" +
            "    join gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id\n" +
            "    join tag t on gcht.tag_id = t.id\n" +
            "    join orders o on gc.id = o.gift_certificate_id\n" +
            "    join user u on o.user_id = u.id\n" +
            "    where u.id = :userId \n" +
            "    group by t.id order by count(t.id) desc, sum(o.cost) desc limit 1", nativeQuery = true)
    Optional<Tag> findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders(@Param("userId") Long userId);

    /**
     * Method checking if Tag with given name already exists in database
     * @param name given name to check
     * @return {@code Boolean} flag
     */
    boolean existsTagByName(String name);

    /**
     * Method search for {@code Tag} with provided name
     * @param name of Tag to search
     * @return {@cade Optional} with search result
     */
    Optional<Tag> findByName(String name);
}
