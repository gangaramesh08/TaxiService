package com.test.taxiservice.documentservice.repository;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface DriverDocumentsRepository extends CrudRepository<DriverDocuments, BigInteger> {

    @Query(value = "select dd.document_id from driver_documents dd where dd.driver_id=?1  and dd.doc_type=?2", nativeQuery = true)
    BigInteger findByDocumentType(BigInteger driverId, String documentType);

    @Query(value = "Select count(*) from driver_documents where driver_id=?1", nativeQuery = true)
    long getDocumentCountByDriverId(BigInteger driverId);
}
