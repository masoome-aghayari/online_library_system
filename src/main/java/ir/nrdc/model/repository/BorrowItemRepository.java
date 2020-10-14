package ir.nrdc.model.repository;

import ir.nrdc.model.entity.BorrowItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface BorrowItemRepository extends Repository<BorrowItem, Integer>, JpaSpecificationExecutor<Integer> {
    void save(BorrowItem borrowItem);
}
