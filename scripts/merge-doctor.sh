#!/usr/bin/env bash
set -euo pipefail

echo "Current branch: $(git branch --show-current)"
echo "Remotes:"
git remote -v || true

echo
echo "Unmerged files:"
git ls-files -u || true

echo
echo "Branches:"
git branch -a || true

echo
echo "Conflict marker scan (required files):"
bash scripts/verify-no-conflicts.sh || true

echo
echo "Recommended merge command:"
echo "bash scripts/resolve-merge-conflicts.sh origin main codex/develop-internal-sales-operations-android-app-frne8i ours"
