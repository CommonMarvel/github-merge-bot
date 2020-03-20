package commonmarvel.githubmergebot.core.service.impl

import commonmarvel.githubmergebot.core.data.dto.PullRequest
import commonmarvel.githubmergebot.core.data.dto.PullRequestMergeRequest
import commonmarvel.githubmergebot.core.data.dto.PullRequestUpdateBranchRequest
import commonmarvel.githubmergebot.core.data.dto.SearchPullRequest
import commonmarvel.githubmergebot.core.data.dto.SearchPullRequestItem
import commonmarvel.githubmergebot.core.properties.GithubProperties
import commonmarvel.githubmergebot.core.service.GithubService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import puni.extension.jackson.jsonStringToObject
import java.net.URI

@Service
class GithubServiceImpl(
  @Autowired val githubProperties: GithubProperties,
  @Autowired val restTemplate: RestTemplate
) : GithubService {

  override fun searchPullRequestList(): List<SearchPullRequestItem> {
    return restTemplate
      .run {
        exchange<String>(
          RequestEntity.get(URI.create("${githubProperties.searchURL}?q=state:open+is:pr+user:${githubProperties.owner}"))
            .header(HttpHeaders.AUTHORIZATION, "token ${githubProperties.token}")
            .build()
        )
      }
      .let { it.body?.jsonStringToObject(SearchPullRequest::class)?.items }
      ?.filter { !it.locked }
      .let {
        it ?: listOf()
      }
  }

  override fun getPullRequest(apiUrl: String): PullRequest? {
    return restTemplate
      .run {
        exchange<String>(
          RequestEntity.get(URI.create(apiUrl))
            .header(HttpHeaders.AUTHORIZATION, "token ${githubProperties.token}")
            .build()
        )
      }
      .let { it.body?.jsonStringToObject(PullRequest::class) }
  }

  override fun updateBranch(pr: PullRequest) {
    restTemplate
      .run {
        exchange<String>(
          RequestEntity.put(URI("${githubProperties.baseURL}/repos/${pr.head.repo.full_name}/pulls/${pr.number}/update-branch"))
            .header(HttpHeaders.AUTHORIZATION, "token ${githubProperties.token}")
            .header(HttpHeaders.ACCEPT, "application/vnd.github.lydian-preview+json")
            .body(PullRequestUpdateBranchRequest(pr.head.sha))
        )
      }
  }

  override fun mergePullRequest(pr: PullRequest) {
    val isDefaultBranch = pr.base.ref == pr.head.repo.default_branch
    restTemplate
      .run {
        exchange<String>(
          RequestEntity.put(URI.create("${githubProperties.baseURL}/repos/${pr.head.repo.full_name}/pulls/${pr.number}/merge"))
            .header(HttpHeaders.AUTHORIZATION, "token ${githubProperties.token}")
            .body(PullRequestMergeRequest(
              pr.title,
              "Auto merge #${pr.number}",
              pr.head.sha,
              if (isDefaultBranch) "squash" else "merge"
            ))
        )
      }
  }
}
