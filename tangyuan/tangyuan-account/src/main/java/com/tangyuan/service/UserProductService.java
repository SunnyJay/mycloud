package com.tangyuan.service;

import com.tangyuan.domain.UserProduct;
import com.tangyuan.repository.UserProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * 作者：sunna
 * 时间: 2018/5/11 10:27
 */
@Service
public class UserProductService
{
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserProductRepository userProductRepository;

    public void addUserProduct(UserProduct userProduct)
    {
        Example<UserProduct> example = Example.of(userProduct);
        /*Optional<UserProduct> opt = userProductRepository.findOne(example);
        if (opt.isPresent())
        {
            logger.info("the user product has already exist!" , userProduct.getId());
        }
        else
        {
            userProductRepository.save(userProduct);
        }*/
    }
}
