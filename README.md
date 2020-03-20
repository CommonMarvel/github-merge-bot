# github-merge-bot

Auto update the branch of pull request and merge if all status check pass / resolve the review request changed

## Usage

> docker-compose.ym;
```yaml
version: '3'

services:
  github-merge-bot:
    image: commonmarvel/github-merge-bot:latest
    environment:
      - GITHUB_OWNER=<OWNER>
      - GITHUB_TOKEN=<TOKEN>

```   

> Notice: the bot will review all available repositories by the token you provide

### lint
```
./gradlew ktlintcheck
```

### lint autofix
```
./gradlew ktlintformat
```
