package com.dorohedoro.wiki.ldap;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CARepo extends LdapRepository<CA> {
}
