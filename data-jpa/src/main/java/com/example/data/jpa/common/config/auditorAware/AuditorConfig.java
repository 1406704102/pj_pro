
package com.example.data.jpa.common.config.auditorAware;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description  : 设置审计
 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * 返回操作员标志信息
     *
     * @return /
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            // 这里应根据实际业务情况获取具体信息
//            return Optional.of(SecurityUtils.getCurrentUsername());
            return Optional.of("SecurityUtils.getCurrentUsername()");
        }catch (Exception ignored){}
        // 用户定时任务，或者无Token调用的情况

        return Optional.of("System");
    }
}
