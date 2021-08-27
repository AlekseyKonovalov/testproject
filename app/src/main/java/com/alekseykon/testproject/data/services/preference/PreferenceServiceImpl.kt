package com.alekseykon.testproject.data.services.preference

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.alekseykon.testproject.domain.services.preference.PreferenceService
import javax.inject.Inject

internal class PreferenceServiceImpl @Inject constructor(
        private val context: Context
) : PreferenceService {

    companion object {
        private const val PREFERENCE_NAME_COMMON = "common_pref"
        private const val PREFERENCE_NAME_CRYPTO = "crypto_pref"
    }

    private val preference by lazy {
        context.getSharedPreferences(PREFERENCE_NAME_COMMON, Context.MODE_PRIVATE)
    }

    private val masterKey by lazy {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    private val crypto by lazy {
        EncryptedSharedPreferences.create(
            PREFERENCE_NAME_CRYPTO,
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override var isUserCreated: Boolean?
        get() = preference.getBoolean(::isUserCreated.name, false)
        set(value) {
            preference.edit().putBoolean(::isUserCreated.name, value ?: false).apply()
        }

    override var accessToken: String?
        get() = crypto.getString(::accessToken.name, null)
        set(value) {
            crypto.edit().putString(::accessToken.name, value).apply()
        }


    override fun clear() {
        preference.edit().clear().apply()
        crypto.edit().clear().apply()
    }


}