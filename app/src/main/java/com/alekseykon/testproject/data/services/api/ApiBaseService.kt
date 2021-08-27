package com.alekseykon.testproject.data.services.api

import com.alekseykon.testproject.data.services.api.models.createUser.CreateUserRequest
import com.alekseykon.testproject.data.services.api.models.createUser.CreateUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


internal interface ApiBaseService {

    @POST("user/create")
    suspend fun createUser(@Body createOrganizationRequest: CreateUserRequest): Response<CreateUserResponse>



}