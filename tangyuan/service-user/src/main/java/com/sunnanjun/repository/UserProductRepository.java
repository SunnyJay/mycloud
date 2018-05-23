package com.sunnanjun.repository;

import com.sunnanjun.domain.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作者：sunna
 * 时间: 2018/5/11 10:28
 */
public interface UserProductRepository extends JpaRepository<UserProduct, String>
{

}
