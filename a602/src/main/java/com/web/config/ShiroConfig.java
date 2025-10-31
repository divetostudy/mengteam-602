package com.web.config;

import com.web.shiro.CORSAuthenticationFilter;
import com.web.shiro.JWTFilter;
import com.web.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.InvalidRequestFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    /**
     * 配置自定义Realm
     * @return
     */
    @Bean("userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }



    /**
     * 配置Shiro的SecurityManager
     * @param userRealm
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

        // 关闭Shiro的Session管理功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 配置Shiro的Web过滤器
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean
    (@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        /**
         *添加Shiro内置过滤器
         * 常用的过滤器：
         *    anon:无需认证（登录）可以访问
         *    authc:必须认证才可以访问
         *    user:如果使用rememberMe的功能可以直接访问
         *    perms:该资源必须得到资源权限才可以访问
         *    role:该资源必须得到角色权限才可以访问
         *    还可以自己自定义过滤器
         */

        // J过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt",new JWTFilter());
        filterMap.put("corsAuthenticationFilter",corsAuthenticationFilter());
        filterMap.put("invalidRequest",invalidRequestFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        Map<String, String> filterRuleMap = new HashMap<>();
        filterRuleMap.put("/#/**","anon");
        filterRuleMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public CORSAuthenticationFilter corsAuthenticationFilter(){
        return new CORSAuthenticationFilter();
    }

    /**
     * 解决路径中文报400的问题
     * @return
     */
    @Bean
    public InvalidRequestFilter invalidRequestFilter(){
        InvalidRequestFilter invalidRequestFilter = new InvalidRequestFilter();
        invalidRequestFilter.setBlockNonAscii(false);
        return invalidRequestFilter;
    }

}
