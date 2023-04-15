package com.dorohedoro.wiki;

import com.dorohedoro.wiki.ldap.CA;
import com.dorohedoro.wiki.ldap.CARepo;
import com.dorohedoro.wiki.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.ldap.LdapName;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class LDAPTest {

    @Autowired
    private CARepo caRepo;
    
    @Test
    public void add() {
        long serialNo = IDGenerator.nextId();
        LdapName dn = LdapNameBuilder.newInstance().add("dc", "com")
                .add("dc", "dorohedoro")
                .add("ou", "ca")
                .add("cn", serialNo + "")
                .build();
        log.info("dn: {}", dn);
        CA ca = new CA();
        ca.setId(dn);
        ca.setCn(serialNo);
        ca.setSn(serialNo);
        ca.setNew(true);
        ca.setCaCertificate("MIICdjCCAd+gAwIBAgIJAL7fhq5PobknMA0GCSqGSIb3DQEBCwUAMG8xCzAJBgNVBAYTAkNOMQ8wDQYDVQQIDAZzaGFueGkxDTALBgNVBAcMBHhpYW4xDTALBgNVBAoMBGVjaG8xDTALBgNVBAsMBGVjaG8xDTALBgNVBAMMBGVjaG8xEzARBgkqhkiG9w0BCQEWBGVjaG8wHhcNMjMwMjAzMDY1NDM4WhcNMzMwMTMxMDY1NDM4WjBvMQswCQYDVQQGEwJDTjEPMA0GA1UECAwGc2hhbnhpMQ0wCwYDVQQHDAR4aWFuMQ0wCwYDVQQKDARlY2hvMQ0wCwYDVQQLDARlY2hvMQ0wCwYDVQQDDARlY2hvMRMwEQYJKoZIhvcNAQkBFgRlY2hvMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDX9StMBvj+b+j2oGCpeSdsm/AxxtI+84YJc2QSnG3+8akYChtx3zKW74Jm7kbjQRNbfKtDc8VoBOdphG+95jY+PGHHG+AVwGDfn8Z12F74cB61qLNFloybjVSN4YmiR2Qo8VpXe6TArLbjvwm0d72HI8JOgU5SGHqsMjYjxv8bFQIDAQABoxowGDAJBgNVHRMEAjAAMAsGA1UdDwQEAwIF4DANBgkqhkiG9w0BAQsFAAOBgQATYEuajukgPktwEMVypUytSrQk9RMg/LxG8Q/nF+nCmp8hXZ2wJDzoDNLVg0hMSMCJTb5fxxNWBiS4xPyHcLnA2CNc8ZgGVGP199azgeqRTlpPxyAVnnsp4ezQoxBOa7Bdb0ftvcE5K9MxsWHmGqyM1baBOEDAbpMmYc+Pv/YdNQ==");
        ca.setCrl("MIIBfjBoAgEBMA0GCSqGSIb3DQEBCwUAMEUxCzAJBgNVBAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQXDTIzMDMzMDEwMTUzOVowDQYJKoZIhvcNAQELBQADggEBANTFfG3JdkkYVX6m+ltYIU95qLGyFE9AiFcQApKLxG2cciGpHhQM68xgw4zs1wWO0pa6z0IcfL4lXaWgppgUYjLlUFqoQD74wvuSNOVeAWbRGAD5QnBX4W11JKXKMtlASAHzm360EFtsukwBTt6NDAIlZP4tYtLq7vDaHmhywPnUqRyGH1pETFcQUtCAH3mtlOoVgcniBuxUjbORxpFsDc/0RN1tWTAc1m3xIIR3MjleX0JVpe6N9PYrBesz0luNqMBN0JXZ74FR/eYJt0QzRzVjckzIsIcai/UcKA8+fmXQKi9oE6yRN6Gz9ZCFbsb4Cp8yDnfHSQQbv0juyZbN9mc=");
        ca.setArl("MIIBfjBoAgEBMA0GCSqGSIb3DQEBCwUAMEUxCzAJBgNVBAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEwHwYDVQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQXDTIzMDMzMDEwMTUzOVowDQYJKoZIhvcNAQELBQADggEBANTFfG3JdkkYVX6m+ltYIU95qLGyFE9AiFcQApKLxG2cciGpHhQM68xgw4zs1wWO0pa6z0IcfL4lXaWgppgUYjLlUFqoQD74wvuSNOVeAWbRGAD5QnBX4W11JKXKMtlASAHzm360EFtsukwBTt6NDAIlZP4tYtLq7vDaHmhywPnUqRyGH1pETFcQUtCAH3mtlOoVgcniBuxUjbORxpFsDc/0RN1tWTAc1m3xIIR3MjleX0JVpe6N9PYrBesz0luNqMBN0JXZ74FR/eYJt0QzRzVjckzIsIcai/UcKA8+fmXQKi9oE6yRN6Gz9ZCFbsb4Cp8yDnfHSQQbv0juyZbN9mc=");
        caRepo.save(ca);
    }
}
