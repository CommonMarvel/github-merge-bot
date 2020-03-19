package commonmarvel.github.merge.bot.core.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
@ConfigurationProperties("github")
class GithubProperties {
  var searchURL = "https://api.github.com/search/issues"
  var owner: String = ""
  var token: String = ""
}
