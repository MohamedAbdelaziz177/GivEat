package com.abdelaziz26.GivEat.Utils;

import com.abdelaziz26.GivEat.Core.Entities.User;
import com.abdelaziz26.GivEat.Core.Repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepo userRepository;
    private final static Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    public Long getUserId() {

        String userEmail =  SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("User email: {}", userEmail);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new AuthorizationServiceException("User not found"));
        return user.getId();
    }
}
