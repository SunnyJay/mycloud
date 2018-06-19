package com.tangyuan;

import com.tangyuan.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 作者：sunna
 * 时间: 2018/4/11 14:50
 */
public class OAuth2ServerConfig
{

    //资源配置服务器
    /*@Configuration
    @EnableResourceServer
    public static class ResourceServerConfig extends ResourceServerConfigurerAdapter
    {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception
        {
            super.configure(resources);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception
        {
            super.configure(http);
        }
    }*/

    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
    {
        //由WebSecurityConfigurerAdapter提供bean
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        /*@Bean
        public RedisConnectionFactory jedisConnectionFactory()
        {
            JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName("39.107.102.175");
            jedisConnectionFactory.setPort(6379);
            jedisConnectionFactory.afterPropertiesSet();

            return connectionFactory;
        }*/

        /**
         * 用什么存储token
         * <p>
         * 可以使用redis，也可以直接在内存中、jwt、jdbc
         */
        private TokenStore tokenStore = new InMemoryTokenStore();
        //private TokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);

        /**
         * 用来配置令牌端点(Token Endpoint)的安全约束.
         *
         * 有很多端点是需要保护的
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
        {
            //什么意思
            security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        }

        /**
         * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
         * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
         *
         * ClientDetailsServiceConfigurer能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService）
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception
        {
            clients.inMemory()  // 使用in-memory存储
                    .withClient("browser")  // client_id 必须的 用来标识客户的Id。
                    .secret("sssss") // client_secret 需要值得信任的客户端 客户端安全码，如果有的话。
                    .authorizedGrantTypes("refresh_token", "password") // 此客户端可以使用的授权类型，默认为空。
                    .scopes("ui")// 用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
                .and()
                    .withClient("webapp")
                    .scopes("xx")
                    .authorizedGrantTypes("implicit");
        }

        /**
         * AuthorizationServerTokenServices 接口定义了一些操作使得你可以对令牌进行一些必要的管理，在使用这些操作的时候请注意以下几点：
         当一个令牌被创建了，你必须对其进行保存，这样当一个客户端使用这个令牌对资源服务进行请求的时候才能够引用这个令牌。
         当一个令牌是有效的时候，它可以被用来加载身份信息，里面包含了这个令牌的相关权限。
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
        {
            endpoints.tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService); //可以配置userService，也可以不配置，配的话每次认证的时候会去检验用户是否锁定，有效等
        }
    }

}
