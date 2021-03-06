package commonmarvel.githubmergebot.core.config

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

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
