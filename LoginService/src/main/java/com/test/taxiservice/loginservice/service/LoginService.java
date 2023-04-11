package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.taxiservicecommon.model.DriverAccessToken;
import com.test.taxiservice.taxiservicecommon.model.loginservice.model.DriverCredentials;
import com.test.taxiservice.taxiservicecommon.repository.DriverAccessTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import static com.test.taxiservice.loginservice.constants.Constants.BEARER;
import static com.test.taxiservice.loginservice.constants.Constants.EXPIRY_DURATION;
import static com.test.taxiservice.loginservice.constants.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;

@Service
@Slf4j
public class LoginService implements ILoginService{

    @Autowired
    private DriverCredentialsRepository credentialsRepository;

    @Autowired
    DriverAccessTokenRepository driverAccessTokenRepository;

    @Value("${app.jwt.secret}")
    private String secretKey;


    @Override
    public void validateLogin(String mobileNumber, String password) throws InvalidInputException, PersistenceException {
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        DriverCredentials driverCredentials;
        try {
            driverCredentials =  credentialsRepository.getByMobileNumberAndPassword(mobileNumber, encryptedPassword);
        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database");
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
        if(Objects.isNull(driverCredentials)) {
            log.error("Incorrect login credentials");
            ErrorInfo validationError = new ErrorInfo();
            validationError.setMessage(MessageConstants.INVALID_DRIVER_INFO);
            throw new InvalidInputException(validationError);
        }

    }

    @Override
    public String login(DriverCredentials driverCredentials) {
        String token = Jwts.builder()
                .setSubject(String.format("%s",driverCredentials.getMobileNumber()))
                .setIssuer("TaxiService")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        DriverAccessToken driverAccessToken = new DriverAccessToken(
                driverCredentials.getMobileNumber(),
                BEARER + token,
                EXPIRY_DURATION);

        driverAccessTokenRepository.save(driverAccessToken);
        return token;

    }
}
