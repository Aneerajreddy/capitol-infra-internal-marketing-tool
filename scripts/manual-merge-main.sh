#!/usr/bin/env bash
set -euo pipefail

BASE_BRANCH=${1:-main}
HEAD_BRANCH=${2:-codex/develop-internal-sales-operations-android-app-frne8i}
REMOTE=${3:-origin}

git fetch "$REMOTE" --prune

if [[ "$(git branch --show-current)" != "$HEAD_BRANCH" ]]; then
  git checkout "$HEAD_BRANCH"
fi

git merge "$REMOTE/$BASE_BRANCH"

bash scripts/verify-no-conflicts.sh

echo "Merge complete. Resolve remaining semantic conflicts if any, then commit and push:"
echo "git push -u $REMOTE $HEAD_BRANCH"
