package pl.redheadsolutions.lg.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
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
            createCache(cm, pl.redheadsolutions.lg.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, pl.redheadsolutions.lg.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, pl.redheadsolutions.lg.domain.User.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.Authority.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.User.class.getName() + ".authorities");
            createCache(cm, pl.redheadsolutions.lg.domain.HousingAssociation.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.HousingAssociation.class.getName() + ".inhabitants");
            createCache(cm, pl.redheadsolutions.lg.domain.Inhabitant.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.Inhabitant.class.getName() + ".conclusions");
            createCache(cm, pl.redheadsolutions.lg.domain.Inhabitant.class.getName() + ".news");
            createCache(cm, pl.redheadsolutions.lg.domain.Conclusion.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.Voting.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.News.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.Region.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.Country.class.getName());
            createCache(cm, pl.redheadsolutions.lg.domain.Location.class.getName());
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
