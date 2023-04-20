package cherish.backend.curation.repository;

import cherish.backend.item.model.Item;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CurationRepository extends JpaRepository<Item, Long> {

    String GET_CURATION_SQL = """
        select i.id
             , i.name
             , i.brand
             , i.description
             , i.img_url
             , i.price
             , c.name   as category_name
             , itf.name as tag_name
             , (select il.id
                from {h-schema}item_like il
                where il.item_id = i.id
                  and il.member_id = :memberId) as item_like_id
             , rs.score
        from {h-schema}item i
           , {h-schema}category c
           , {h-schema}item_category ic
           , {h-schema}item_filter itf
           , (select t.item_id
                   , t.score
              from (select total.item_id
                         , count(distinct case when total.job_name = :job then 1 end)
                      + count(distinct case when total.category_name in (:categories) then 1 end)
                      + count(distinct case when total.filter_name = :gender then 1 end)
                      + count(distinct case when total.filter_name = :purpose then 1 end)
                      + count(distinct case when total.filter_name = :relation then 1 end)
                      + count(distinct case when total.filter_name = :emotion then 1 end)
                      as score
                    from (select i.id     as item_id
                               , c.name   as category_name
                               , j.name   as job_name
                               , itf.name as filter_name
                          from {h-schema}item i
                                   inner join {h-schema}item_category ic on i.id = ic.item_id
                                   inner join {h-schema}category c on c.id = ic.category_id
                                   inner join {h-schema}item_job ij on i.id = ij.item_id
                                   inner join {h-schema}job j on j.id = ij.job_id
                                   inner join {h-schema}item_filter itf on i.id = itf.item_id
                          where 1 = 1
                            and i.price >= :minPrice and i.price <= :maxPrice
                            and i.min_age <= :age and i.max_age >= :age
                            and c.id < 7
                            and itf.filter_id in (2, 3, 4, 7)
                            and j.id < 11) total
                    group by total.item_id
                    order by score desc
                           , random()
                    limit 5) t) rs
        where i.id = rs.item_id
          and ic.item_id = i.id
          and c.id < 11
          and ic.category_id = c.id
          and itf.id = (select id
                        from (select itf2.id
                              from {h-schema}item_filter itf2
                              where itf2.item_id = i.id
                                and itf2.filter_id = 2) itf3
                        order by random()
                        limit 1)
        """;

    @Query(value = GET_CURATION_SQL, nativeQuery = true)
    List<Tuple> findCurationItemsBy(
        @Param("age") int age,
        @Param("minPrice") int minPrice,
        @Param("maxPrice") int maxPrice,
        @Param("job") String job,
        @Param("categories") Set<String> categories,
        @Param("gender") String gender,
        @Param("purpose") String purpose,
        @Param("relation") String relation,
        @Param("emotion") String emotion,
        @Param("memberId") long memberId
    );
}
