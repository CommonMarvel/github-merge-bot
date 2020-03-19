package commonmarvel.github.merge.bot.core.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@Configuration
class CommonConfig {

  @Bean
  fun restTemplate(): RestTemplate = RestTemplate(
    OkHttp3ClientHttpRequestFactory(
      OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .retryOnConnectionFailure(true)
        .build()
    )
  )
}
