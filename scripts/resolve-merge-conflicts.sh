#!/usr/bin/env bash
set -euo pipefail

REMOTE=${1:-origin}
BASE_BRANCH=${2:-main}
HEAD_BRANCH=${3:-codex/develop-internal-sales-operations-android-app-frne8i}
STRATEGY=${4:-ours} # ours|theirs

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

echo "Fetching $REMOTE..."
git fetch "$REMOTE" --prune

echo "Checking out $HEAD_BRANCH..."
git checkout "$HEAD_BRANCH"

echo "Merging $REMOTE/$BASE_BRANCH into $HEAD_BRANCH..."
if ! git merge "$REMOTE/$BASE_BRANCH"; then
  echo "Merge reported conflicts. Applying $STRATEGY strategy for target files..."

  for f in "${TARGET_FILES[@]}"; do
    if git ls-files -u -- "$f" | rg . >/dev/null; then
      if [[ "$STRATEGY" == "theirs" ]]; then
        git checkout --theirs -- "$f"
      else
        git checkout --ours -- "$f"
      fi
      git add "$f"
    fi
  done

  if git ls-files -u | rg . >/dev/null; then
    echo "Unresolved conflicts remain in files outside target list:"
    git ls-files -u
    exit 1
  fi

  bash scripts/verify-no-conflicts.sh
  git commit -m "Merge $BASE_BRANCH into $HEAD_BRANCH and resolve known file conflicts ($STRATEGY)"
else
  echo "Merge completed without conflicts."
fi

echo "Done. Push with: git push -u $REMOTE $HEAD_BRANCH"
