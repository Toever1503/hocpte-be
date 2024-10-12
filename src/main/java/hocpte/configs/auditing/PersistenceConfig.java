package hocpte.configs.auditing;

import hocpte.entities.UserEntity;
import hocpte.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig {

    private class AuditorAwareImpl implements AuditorAware<UserEntity> {
        @Override
        public Optional<UserEntity> getCurrentAuditor() {
            return Optional.ofNullable(SecurityUtils.getCurrentUser().getUser());
        }
    }

    @Bean
    public AuditorAware<UserEntity> auditorAware() {
        return new AuditorAwareImpl();
    }
}