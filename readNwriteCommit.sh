#!/usr/local/bin/bash

#OWNER="torvalds"
#REPO="linux"
OWNER="Shalahur"
REPO="devops-task2"
BRANCH="master"

API_URL="https://api.github.com/repos/$OWNER/$REPO/commits?sha=$BRANCH"

curl -s "$API_URL" | jq -r '.[] | "\n\nAuthor Name: \(.commit.author.name)\nCommit Message: \(.commit.message)"'
