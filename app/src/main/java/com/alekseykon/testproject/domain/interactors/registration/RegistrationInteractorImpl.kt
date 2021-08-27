package com.alekseykon.testproject.domain.interactors.registration

import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.domain.models.UserInfoEntity
import com.alekseykon.testproject.domain.repositories.registration.RegistrationRepository
import com.alekseykon.testproject.domain.services.preference.PreferenceService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RegistrationInteractorImpl @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val preferenceService: PreferenceService
) : RegistrationInteractor {

    override suspend fun createUser(
        firstName: String,
        secondName: String,
        phoneNumber: String
    ): Flow<DataResult<UserInfoEntity>> = registrationRepository.createUser(firstName, secondName, phoneNumber)


    override suspend fun getUser(): Flow<DataResult<UserInfoEntity>> {
        return registrationRepository.getUser()
    }

    override suspend fun updateUserInfo(userInfoEntity: UserInfoEntity): Flow<DataResult<UserInfoEntity>> {
        return registrationRepository.updateUserInfo(userInfoEntity)
    }

    override fun getCreateUserStatus(): Boolean {
        return preferenceService.isUserCreated ?: false
    }

    override fun setCreateUserStatus() {
        preferenceService.isUserCreated = true
    }

    override suspend fun searchApplicationsByPhone(phone: String): Flow<DataResult<List<SearchItemEntity>>> {
        return registrationRepository.searchApplicationsByPhone(phone)
    }

}