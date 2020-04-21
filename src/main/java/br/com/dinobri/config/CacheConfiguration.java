package br.com.dinobri.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.dinobri.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.dinobri.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.dinobri.domain.User.class.getName());
            createCache(cm, br.com.dinobri.domain.Authority.class.getName());
            createCache(cm, br.com.dinobri.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.dinobri.domain.Revista.class.getName());
            createCache(cm, br.com.dinobri.domain.Revista.class.getName() + ".secaos");
            createCache(cm, br.com.dinobri.domain.Revista.class.getName() + ".edicaos");
            createCache(cm, br.com.dinobri.domain.Secao.class.getName());
            createCache(cm, br.com.dinobri.domain.Secao.class.getName() + ".materias");
            createCache(cm, br.com.dinobri.domain.Autor.class.getName());
            createCache(cm, br.com.dinobri.domain.Autor.class.getName() + ".materias");
            createCache(cm, br.com.dinobri.domain.Edicao.class.getName());
            createCache(cm, br.com.dinobri.domain.Edicao.class.getName() + ".materias");
            createCache(cm, br.com.dinobri.domain.Materia.class.getName());
            createCache(cm, br.com.dinobri.domain.Materia.class.getName() + ".tags");
            createCache(cm, br.com.dinobri.domain.Materia.class.getName() + ".autors");
            createCache(cm, br.com.dinobri.domain.Tag.class.getName());
            createCache(cm, br.com.dinobri.domain.Tag.class.getName() + ".materias");
            createCache(cm, br.com.dinobri.domain.GrupoTag.class.getName());
            createCache(cm, br.com.dinobri.domain.GrupoTag.class.getName() + ".tags");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
