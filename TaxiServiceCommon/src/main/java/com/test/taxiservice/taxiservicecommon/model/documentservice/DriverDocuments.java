package com.test.taxiservice.taxiservicecommon.model.documentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DriverDocuments")
public class DriverDocuments {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "documentId")

    private BigInteger documentId;

    @Column(name = "driverId")

    private BigInteger driverId;

    @Column(name = "docName")

    private String docName;

    @Column(name = "docType")

    private String docType;

    @Column(name = "storageLink")

    private String storageLink;

    @Column(name = "createdAt")

    private Date createdAt;

    @Column(name = "modifiedAt")

    private Date modifiedAt;
}
