package commonmarvel.githubmergebot.job

import commonmarvel.githubmergebot.core.data.dto.MergeableState
import commonmarvel.githubmergebot.core.service.GithubService
import commonmarvel.githubmergebot.log.Loggable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ReviewPRList(
  @Autowired val githubService: GithubService
) : Loggable {

  @Scheduled(fixedDelay = 300000)
  fun fetchPRList() {
    logger.info("========= Start review PRs ===========")
    githubService.searchPullRequestList()
      .forEach { item ->
        githubService.getPullRequest(item.pull_request.url)
          .let { pullRequest ->
            if (pullRequest?.mergeable_state == MergeableState.behind) {
              githubService.updateBranch(pullRequest)
              logger.info("#{} {} branch updated", pullRequest.number, pullRequest.title)
            } else if (pullRequest?.mergeable_state == MergeableState.clean) {
              githubService.mergePullRequest(pullRequest)
              logger.info("#{} {} has been merged", pullRequest.number, pullRequest.title)
            }
          }
      }
      .let { logger.info("========= Finish review PRs ===========") }
  }
}
