package com.ewallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ewallet.entity.BillInfo;

public interface BillinfoRepository extends JpaRepository<BillInfo, Long>{
	@Query(value = "select * from bill_infos,categories WHERE categories.id=bill_infos.cagetory_id and customer_code=:customercode and cagetory_id=:id",nativeQuery=true)
	Optional<BillInfo> findBycustomercode(@Param("customercode") String customercode,@Param("id") Long id);
}
