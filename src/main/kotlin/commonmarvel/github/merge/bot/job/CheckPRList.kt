package commonmarvel.github.merge.bot.job

import commonmarvel.github.merge.bot.log.Loggable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CheckPRList : Loggable {

  @Scheduled(cron = "0/30 * * * * ?")
  fun fetchPRList() {
    logger.info("Hello world")
  }
}
