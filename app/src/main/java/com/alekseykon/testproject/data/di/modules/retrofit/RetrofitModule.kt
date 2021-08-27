package com.alekseykon.testproject.data.di.modules.retrofit

import com.alekseykon.testproject.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.alekseykon.testproject.data.di.DataScope
import com.alekseykon.testproject.data.services.api.ApiBaseService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.net.ssl.*


@Module
internal class RetrofitModule {

    @DataScope
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            requestBuilder.addHeader("Content-Type", "application/json")
            it.proceed(requestBuilder.build())
        }
    }


    @DataScope
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .apply {
                connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                addInterceptor(headerInterceptor)
                setOkHttpClientUnsafe()
            }
            .build()
    }

    @DataScope
    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .serializeNulls()
            .create()


    @DataScope
    @Named("API_BASE_URL")
    @Provides
    fun provideApiBaseRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @DataScope
    @Provides
    fun provideApiBaseService(@Named("API_BASE_URL") retrofit: Retrofit): ApiBaseService {
        return retrofit.create(ApiBaseService::class.java)
    }

      companion object {
        private const val READ_TIMEOUT = 30
        private const val WRITE_TIMEOUT = 30
        private const val CONNECTION_TIMEOUT = 10
        private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

        fun OkHttpClient.Builder.setOkHttpClientUnsafe() {
            sslSocketFactory(getSocketFactory(unsafeX509TrustManager), unsafeX509TrustManager)
                .hostnameVerifier(hostnameVerifier)
        }

        private val hostnameVerifier: HostnameVerifier
            get() = HostnameVerifier { hostname, session ->
                HttpsURLConnection.getDefaultHostnameVerifier().run {
                    verify(hostname, session)
                }
            }

        private val unsafeX509TrustManager: X509TrustManager
            get() = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            }

        private fun getSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
            return SSLContext.getInstance("TLS").apply {
                init(
                    null,
                    arrayOf<TrustManager>(trustManager),
                    java.security.SecureRandom()
                )
            }.socketFactory
        }
    }

}