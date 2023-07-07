package com.depromeet.whatnow.domain.repo

import com.depromeet.whatnow.domain.model.*

interface AuthRepository {
    suspend fun postAuthOauthKakaoLogin(
        id_token: String,
        usersFcmToken: UsersFcmToken,
    ): Result<TokenAndUser>

    suspend fun postAuthKakaoRegister(
        id_token: String,
        request: Register,
    ): Result<TokenAndUser>

    suspend fun postAuthKakaoInfo(access_token: String): Result<OauthUserInfo>

    suspend fun getAuthOauthKakaoRegisterValid(id_token: String): Result<AbleRegister>

    suspend fun deleteAutoMe()
}