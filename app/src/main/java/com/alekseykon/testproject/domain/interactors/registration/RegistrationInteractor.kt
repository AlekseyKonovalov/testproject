package com.alekseykon.testproject.domain.interactors.registration

import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.domain.models.UserInfoEntity
import kotlinx.coroutines.flow.Flow

internal interface RegistrationInteractor {
    suspend fun createUser(
        firstName: String,
        secondName: String,
        phoneNumber: String
    ): Flow<DataResult<UserInfoEntity>>

    suspend fun getUser(): Flow<DataResult<UserInfoEntity>>

    suspend fun updateUserInfo(userInfoEntity: UserInfoEntity): Flow<DataResult<UserInfoEntity>>

    fun getCreateUserStatus(): Boolean

    fun setCreateUserStatus()

    suspend fun searchApplicationsByPhone(phone: String): Flow<DataResult<List<SearchItemEntity>>>
}