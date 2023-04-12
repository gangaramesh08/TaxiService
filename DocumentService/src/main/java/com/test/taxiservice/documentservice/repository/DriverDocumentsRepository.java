package com.test.taxiservice.documentservice.repository;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface DriverDocumentsRepository extends CrudRepository<DriverDocuments, BigInteger> {
}
