#!/usr/bin/env bash
set -euo pipefail

TARGET_DIR=${1:-../capitol-infra-internal-marketing-tool-clean}
REMOTE_URL=${2:-}

SOURCE_DIR=$(pwd)

if [[ -e "$TARGET_DIR" ]]; then
  echo "Target already exists: $TARGET_DIR"
  exit 1
fi

mkdir -p "$TARGET_DIR"

rsync -a --exclude='.git' --exclude='node_modules' --exclude='build' --exclude='.gradle' --exclude='.idea' "$SOURCE_DIR"/ "$TARGET_DIR"/

cd "$TARGET_DIR"
git init
git checkout -b main
git add .
git commit -m "Initial clean import of Real Estate CRM stack"

if [[ -n "$REMOTE_URL" ]]; then
  git remote add origin "$REMOTE_URL"
  echo "Remote set: $REMOTE_URL"
  echo "Push with: git push -u origin main"
fi

echo "Clean repository created at: $TARGET_DIR"
