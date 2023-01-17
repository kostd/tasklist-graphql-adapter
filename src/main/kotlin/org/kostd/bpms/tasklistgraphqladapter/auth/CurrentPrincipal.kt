package org.kostd.bpms.tasklistgraphqladapter.auth

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

/**
 * Возвращает текущего принципала. Получает его из securityContext
 */
@Component
class CurrentPrincipal {

    fun get(): UserDetails {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication;
        val userPrincipal: UserDetails = authentication.principal as UserDetails;
        return userPrincipal;
    }
}