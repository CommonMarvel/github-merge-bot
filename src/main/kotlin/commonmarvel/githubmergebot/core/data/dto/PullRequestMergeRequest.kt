package commonmarvel.githubmergebot.core.data.dto

data class PullRequestMergeRequest(
  val commit_title: String,
  val commit_message: String,
  val sha: String,
  val merge_method: String
)
