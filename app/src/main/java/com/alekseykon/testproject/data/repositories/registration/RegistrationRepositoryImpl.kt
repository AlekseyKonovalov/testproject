package com.alekseykon.testproject.data.repositories.registration

import com.alekseykon.testproject.data.repositories.BaseRemoteRepository
import com.alekseykon.testproject.data.retrofit.DataResult
import com.alekseykon.testproject.data.services.api.ApiBaseService
import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.domain.models.ApplicationStatus
import com.alekseykon.testproject.domain.models.UserInfoEntity
import com.alekseykon.testproject.domain.repositories.registration.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
import javax.inject.Inject

internal class RegistrationRepositoryImpl @Inject constructor(
    private val apiBaseService: ApiBaseService
) : BaseRemoteRepository(), RegistrationRepository {

    override suspend fun createUser(
        firstName: String,
        secondName: String,
        phoneNumber: String,
    ): Flow<DataResult<UserInfoEntity>> {
        return flow {
            delay(1000L)
            emit(
                DataResult.success(
                    UserInfoEntity(
                        firstName, secondName, phoneNumber
                    )
                )
            )
            return@flow
/*            val result = getResult(
                request = { apiBaseService.createUser(CreateUserRequest(inn, bik, account)) },
                mapTo = UserMapper::mapToUserEntity
            )
            emit(result)*/
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUser(): Flow<DataResult<UserInfoEntity>> {
        return flow {
            delay(1000L)
            emit(
                DataResult.success(
                    UserInfoEntity(
                        "Aleksey",
                        "Konovalov",
                        ""
                    )
                )
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateUserInfo(organizationInfoEntity: UserInfoEntity): Flow<DataResult<UserInfoEntity>> {
        return flow {
            delay(1000L)
            emit(DataResult.success(organizationInfoEntity))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun searchApplicationsByPhone(phone: String): Flow<DataResult<List<SearchItemEntity>>> {
        return flow {
            delay(3000L)
            emit(
                DataResult.success(
                    listOf(
                        SearchItemEntity(
                            applicationId = "1",
                            clientPhone = "+796502020202",
                            applicationState = ApplicationStatus.SIGNED,
                            purchaseAmount = BigDecimal(1000)
                        ),
                        SearchItemEntity(
                            applicationId = "2",
                            clientPhone = "+79658545333",
                            applicationState = ApplicationStatus.REJECTED,
                            purchaseAmount = BigDecimal(1000000)
                        ),
                        SearchItemEntity(
                            applicationId = "3",
                            clientPhone = "+796523123231",
                            applicationState = ApplicationStatus.WAIT_APPROVE,
                            purchaseAmount = BigDecimal(650)
                        ),
                        SearchItemEntity(
                            applicationId = "4",
                            clientPhone = "+796502020202",
                            applicationState = ApplicationStatus.SIGNED,
                            purchaseAmount = BigDecimal(1000)
                        ),
                        SearchItemEntity(
                            applicationId = "5",
                            clientPhone = "+79658545333",
                            applicationState = ApplicationStatus.REJECTED,
                            purchaseAmount = BigDecimal(1000000)
                        ),
                        SearchItemEntity(
                            applicationId = "6",
                            clientPhone = "+796523123231",
                            applicationState = ApplicationStatus.WAIT_APPROVE,
                            purchaseAmount = BigDecimal(650)
                        )
                    )
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}
