package commonmarvel.githubmergebot.core.service

import commonmarvel.githubmergebot.core.data.dto.PullRequest
import commonmarvel.githubmergebot.core.data.dto.SearchPullRequestItem

interface GithubService {
  fun searchPullRequestList(): List<SearchPullRequestItem>
  fun getPullRequest(apiUrl: String): PullRequest?
  fun updateBranch(pr: PullRequest)
  fun mergePullRequest(pr: PullRequest)
}
