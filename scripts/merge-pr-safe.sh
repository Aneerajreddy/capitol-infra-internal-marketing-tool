#!/usr/bin/env bash
set -euo pipefail

REMOTE_NAME=${REMOTE_NAME:-origin}
REMOTE_URL=${REMOTE_URL:-https://github.com/Aneerajreddy/capitol-infra-internal-marketing-tool.git}
BASE_BRANCH=${1:-main}
HEAD_BRANCH=${2:-codex/develop-internal-sales-operations-android-app-frne8i}
STRATEGY=${3:-ours}

if ! git remote get-url "$REMOTE_NAME" >/dev/null 2>&1; then
  git remote add "$REMOTE_NAME" "$REMOTE_URL"
fi

git fetch "$REMOTE_NAME" "$BASE_BRANCH" "$HEAD_BRANCH" --prune

git checkout -B "$HEAD_BRANCH" "$REMOTE_NAME/$HEAD_BRANCH"

# first pass merge
git merge "$REMOTE_NAME/$BASE_BRANCH" || true

# resolve known conflict set
bash scripts/resolve-merge-conflicts.sh "$REMOTE_NAME" "$BASE_BRANCH" "$HEAD_BRANCH" "$STRATEGY" || true

# final gate
if git ls-files -u | rg . >/dev/null; then
  echo "Unresolved conflicts remain:"
  git ls-files -u
  exit 1
fi

bash scripts/verify-no-conflicts.sh

git commit --allow-empty -m "Finalize merge of $BASE_BRANCH into $HEAD_BRANCH via safe merge helper"

echo "Merge finalized. Push with: git push -u $REMOTE_NAME $HEAD_BRANCH"
