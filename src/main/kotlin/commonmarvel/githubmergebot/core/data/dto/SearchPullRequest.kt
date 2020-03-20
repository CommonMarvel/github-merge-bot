package commonmarvel.githubmergebot.core.data.dto

data class PullRequestInfo(
  val url: String
)

data class SearchPullRequestItem(
  val locked: Boolean,
  val pull_request: PullRequestInfo
)

data class SearchPullRequest(
  val total_count: Int,
  val incomplete_results: Boolean,
  val items: List<SearchPullRequestItem>
)
