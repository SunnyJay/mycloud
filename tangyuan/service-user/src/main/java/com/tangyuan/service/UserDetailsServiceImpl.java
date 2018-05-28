package com.tangyuan.service;

import com.tangyuan.domain.User;
import com.tangyuan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 如果需要改变认证的用户信息来源，我们可以实现 UserDetailsService

 * 作者：sunna
 * 时间: 2018/4/9 13:17
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = new User();
        user.setUsername(username);

        Example<User> example = Example.of(user);
        Optional<User> opt = userRepository.findOne(example);
        if (opt.isPresent())
        {
            throw new UsernameNotFoundException(username);
        }
        return opt.get();
    }
}
