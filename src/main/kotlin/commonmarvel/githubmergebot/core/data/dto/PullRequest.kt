package commonmarvel.githubmergebot.core.data.dto

data class PullRequestHeadRepo(
  val full_name: String,
  val default_branch: String
)

data class PullRequestHead(
  val repo: PullRequestHeadRepo,
  val sha: String
)

data class PullRequestBase(
  val ref: String
)

data class Label(
  val name: String
)

enum class MergeableState {
  behind,
  blocked,
  clean,
  dirty,
  draft,
  has_hooks,
  unknown,
  unstable,
}

data class PullRequest(
  val number: Int,
  val title: String,
  val head: PullRequestHead,
  val base: PullRequestBase,
  val mergeable_state: MergeableState?,
  val labels: List<Label>
)
