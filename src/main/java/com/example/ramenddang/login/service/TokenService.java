package com.example.ramenddang.login.service;

import com.example.ramenddang.login.entity.UserRefresh;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final CacheManager cacheManager;

    public TokenService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    //insert refresh token 
    @CachePut(cacheNames = "userRefresh", key = "'refresh:' + #refresh ", cacheManager = "refreshCacheManager")
    public UserRefresh cacheUserRefresh(String refresh) {

        UserRefresh userRefresh = new UserRefresh();
        userRefresh.setRefresh(refresh);

        return userRefresh;
    }
    
    //저장된 refresh 가져오기
    public UserRefresh getUserRefresh(String refresh) {
        String key = "refresh:" + refresh;
        Cache cache = cacheManager.getCache("userRefresh");
        if (cache != null) {
            return cache.get(key, UserRefresh.class);
        }
        return null;
    }

    // 캐시에서 특정 refresh 토큰을 삭제하는 메서드
    public void evictUserRefresh(String refresh) {
        String key = "refresh:" + refresh;
        Cache cache = cacheManager.getCache("userRefresh");
        if (cache != null) {
            cache.evict(key); // 캐시에서 삭제
        }
    }
}
