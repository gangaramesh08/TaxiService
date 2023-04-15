package com.test.taxiservice.documentservice.model;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalBGStatusRequest {

    private BigInteger verificationId;
    private List<DocumentType> documentsList;
    private String status;
}
