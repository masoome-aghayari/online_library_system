package ir.nrdc.model.repository;

import ir.nrdc.model.entity.LendItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface LendItemRepository extends Repository<LendItem, Integer>, JpaSpecificationExecutor<Integer> {
    void save(LendItem lendItem);

    long countByBook_IsbnAndReturned(String bookISBN, boolean returned);
}
