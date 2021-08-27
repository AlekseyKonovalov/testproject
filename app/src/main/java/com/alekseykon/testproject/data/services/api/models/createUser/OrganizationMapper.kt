package com.alekseykon.testproject.data.services.api.models.createUser

import com.alekseykon.testproject.domain.models.UserInfoEntity


internal object UserMapper {
    fun mapToUserEntity(value: CreateUserResponse?): UserInfoEntity {
        return UserInfoEntity(
            firstName = value?.firstName ?: "",
            secondName = value?.secondName ?: "",
            phoneNumber = value?.phoneNumber ?: ""
        )
    }
}
