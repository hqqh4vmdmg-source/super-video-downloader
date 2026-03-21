package com.myAllVideoBrowser.util.proxy_utils

import com.myAllVideoBrowser.util.AppLogger
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.OkHttpClient
import java.net.InetSocketAddress
import java.net.Proxy
import javax.inject.Inject

class OkHttpProxyClient @Inject constructor(
    private val okHttpClient: OkHttpClient?,
    private val proxyController: CustomProxyController
) {
    private var currentProxy: com.myAllVideoBrowser.data.local.model.Proxy
    private var httpClientCached: OkHttpClient? = null

    init {
        currentProxy = getProxy()
    }

    fun getProxyOkHttpClient(): OkHttpClient {
        val base = okHttpClient ?: run {
            AppLogger.e("OkHttpProxyClient: base OkHttpClient is null, returning fallback")
            return OkHttpClient()
        }

        val proxy = getProxy()

        if (proxy.host != currentProxy.host || proxy.port != currentProxy.port || httpClientCached == null) {
            currentProxy = proxy
            httpClientCached = if (proxy == com.myAllVideoBrowser.data.local.model.Proxy.noProxy()) {
                base.newBuilder().build()
            } else {
                val port = proxy.port.toIntOrNull()
                if (port == null) {
                    AppLogger.e("OkHttpProxyClient: proxy port '${proxy.port}' is not a valid integer — falling back to no-proxy")
                    base.newBuilder().build()
                } else {
                val proxyCredentials = getProxyCredentials()
                val proxyAuthenticator = Authenticator { _, response ->
                    response.request.newBuilder()
                        .header("Proxy-Authorization", proxyCredentials)
                        .build()
                }
                base.newBuilder()
                    .proxy(
                        Proxy(
                            Proxy.Type.HTTP,
                            InetSocketAddress(proxy.host, port)
                        )
                    )
                    .proxyAuthenticator(proxyAuthenticator)
                    .build()
                }
            }
        }

        return httpClientCached ?: base
    }

    private fun getProxy(): com.myAllVideoBrowser.data.local.model.Proxy {
        return proxyController.getCurrentRunningProxy()
    }

    private fun getProxyCredentials(): String {
        val creds = proxyController.getProxyCredentials()
        return Credentials.basic(creds.first, creds.second)
    }
}
