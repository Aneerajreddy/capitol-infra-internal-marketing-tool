#!/usr/bin/env bash
set -euo pipefail

REMOTE=${1:-origin}
BASE_BRANCH=${2:-main}
HEAD_BRANCH=${3:-codex/develop-internal-sales-operations-android-app-frne8i}
STRATEGY=${4:-ours} # ours|theirs
NO_FETCH=${NO_FETCH:-0}

TARGET_FILES=(
  README.md
  app/build.gradle.kts
  app/src/main/AndroidManifest.xml
  app/src/main/java/com/company/internalapp/MainActivity.kt
  app/src/main/java/com/company/internalapp/ui/screens/AppRoot.kt
  app/src/main/res/values/themes.xml
  app/src/main/res/xml/network_security_config.xml
  build.gradle.kts
  gradle/wrapper/gradle-wrapper.properties
  gradlew
  gradlew.bat
)

resolve_file() {
  local file=$1
  if git ls-files -u -- "$file" | rg . >/dev/null; then
    if [[ "$STRATEGY" == "theirs" ]]; then
      git checkout --theirs -- "$file"
    else
      git checkout --ours -- "$file"
    fi
    git add "$file"
  fi
}

base_ref="$BASE_BRANCH"

if [[ "$NO_FETCH" != "1" ]]; then
  echo "Fetching $REMOTE..."
  if git fetch "$REMOTE" --prune; then
    if git rev-parse --verify "$REMOTE/$BASE_BRANCH" >/dev/null 2>&1; then
      base_ref="$REMOTE/$BASE_BRANCH"
    fi
  else
    echo "Fetch failed; continuing with local refs."
  fi
fi

if git rev-parse --verify "$REMOTE/$BASE_BRANCH" >/dev/null 2>&1; then
  base_ref="$REMOTE/$BASE_BRANCH"
elif git rev-parse --verify "$BASE_BRANCH" >/dev/null 2>&1; then
  base_ref="$BASE_BRANCH"
else
  echo "Base branch not found: $BASE_BRANCH"
  exit 1
fi

if git rev-parse --verify "$HEAD_BRANCH" >/dev/null 2>&1; then
  git checkout "$HEAD_BRANCH"
else
  echo "Head branch '$HEAD_BRANCH' not found locally; using current branch $(git branch --show-current)."
fi

echo "Merging $base_ref into $(git branch --show-current)..."
if ! git merge "$base_ref"; then
  echo "Merge reported conflicts. Applying $STRATEGY strategy for known target files..."
  for f in "${TARGET_FILES[@]}"; do resolve_file "$f"; done

  if git ls-files -u | rg . >/dev/null; then
    echo "Unresolved conflicts remain in files outside target list:"
    git ls-files -u
    echo "Manual resolution required for remaining files."
    exit 1
  fi

  bash scripts/verify-no-conflicts.sh
  git commit -m "Merge $BASE_BRANCH and resolve known conflict files ($STRATEGY)"
else
  echo "Merge completed without conflicts."
fi

echo "Done. Push with: git push -u $REMOTE $(git branch --show-current)"
