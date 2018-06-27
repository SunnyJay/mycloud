package com.tangyuan.repository;

import com.tangyuan.domain.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：sunna
 * 时间: 2018/6/22 15:38
 */
public interface UserCredentialRepository extends JpaRepository<UserCredential, String>
{
    UserCredential findByIdentifierAndIdentityType(String identifier, int identityType);
}
