package com.sunnanjun.controller;

import com.sunnanjun.domain.UserProduct;
import com.sunnanjun.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：sunna
 * 时间: 2018/5/11 10:50
 */
@RestController
public class UserProductController
{
    @Autowired
    UserProductService userProductService;

    @RequestMapping("/add_user_product")
    public void addUserProduct(@RequestBody UserProduct userProduct)
    {
        userProductService.addUserProduct(userProduct);
    }

}
