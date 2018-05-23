package com.sunnanjun.repository;

import com.sunnanjun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：sunna
 * 时间: 2018/4/8 13:45
 */
public interface UserRepository extends JpaRepository<User, String>
{

}

