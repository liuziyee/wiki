package com.dorohedoro.wiki.ldap;

import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

import javax.naming.Name;

@Data
@Entry(objectClasses = {"certificationAuthority", "inetOrgPerson"})
public class CA implements Persistable<Name> {

    @Id
    private Name id;
    @Attribute(name = "cn")
    private long cn;
    @Attribute(name = "sn")
    private long sn;
    @Attribute(name = "caCertificate")
    private String caCertificate;
    @Attribute(name = "certificateRevocationList")
    private String crl;
    @Attribute(name = "authorityRevocationList")
    private String arl;
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
