package org.ttm.foodorderingappcmp.auth.network.api_service

import org.ttm.foodorderingappcmp.auth.network.responses.LoginRegisterResponse

interface LoginRegisterService {

    suspend fun login(email: String, password: String) : LoginRegisterResponse?

    suspend fun register(email: String, fullName: String, password: String) : LoginRegisterResponse?
}