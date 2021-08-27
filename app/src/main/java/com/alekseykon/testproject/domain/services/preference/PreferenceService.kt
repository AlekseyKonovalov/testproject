package com.alekseykon.testproject.domain.services.preference

internal interface PreferenceService {

    var isUserCreated: Boolean?

    var accessToken: String?

    fun clear()

}