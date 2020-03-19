package commonmarvel.github.merge.bot.job

import commonmarvel.github.merge.bot.core.properties.GithubProperties
import commonmarvel.github.merge.bot.log.Loggable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.RequestEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.net.URI

@Component
class CheckPRList(
  @Autowired val restTemplate: RestTemplate,
  @Autowired val githubProperties: GithubProperties
) : Loggable {

  @Scheduled(cron = "0/30 * * * * ?")
  fun fetchPRList() {
    restTemplate
      .run {
        exchange<String>(
          RequestEntity.get(URI.create("${githubProperties.token}?q=state:open+is:pr+user:${githubProperties.owner}"))
            .header(HttpHeaders.AUTHORIZATION, "token ${githubProperties.token}")
            .build()
        )
      }
      .let { responseEntity ->
        logger.info("body: {}", responseEntity.body)
      }
  }
}
