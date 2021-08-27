package com.alekseykon.testproject.domain.repositories.registration

import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.domain.models.UserInfoEntity
import kotlinx.coroutines.flow.Flow

internal interface RegistrationRepository {
    suspend fun createUser(
        firstName: String,
        secondName: String,
        phoneNumber: String
    ): Flow<DataResult<UserInfoEntity>>

    suspend fun getUser(): Flow<DataResult<UserInfoEntity>>

    suspend fun updateUserInfo(userInfoEntity: UserInfoEntity): Flow<DataResult<UserInfoEntity>>

    suspend fun searchApplicationsByPhone(phone: String): Flow<DataResult<List<SearchItemEntity>>>

}