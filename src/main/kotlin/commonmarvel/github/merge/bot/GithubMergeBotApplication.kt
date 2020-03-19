package commonmarvel.github.merge.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubMergeBotApplication

fun main(args: Array<String>) {
  runApplication<GithubMergeBotApplication>(*args)
}
