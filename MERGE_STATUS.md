# Merge Status

Attempted command-line merge operations in this environment:

```bash
git pull origin main
```

and

```bash
git remote add origin https://github.com/Aneerajreddy/capitol-infra-internal-marketing-tool.git
git fetch origin --prune
```

Both failed due network/proxy restriction (`CONNECT tunnel failed, response 403`) from this execution environment.

Use the included script locally (with GitHub network access):

```bash
bash scripts/manual-merge-main.sh main codex/develop-internal-sales-operations-android-app-frne8i origin
```

Then verify:

```bash
bash scripts/verify-no-conflicts.sh
```

For repeat merge failures, use:

```bash
bash scripts/resolve-merge-conflicts.sh origin main codex/develop-internal-sales-operations-android-app-frne8i ours
```

If remote fetch is blocked but local `main` already exists, run:

```bash
NO_FETCH=1 bash scripts/resolve-merge-conflicts.sh origin main codex/develop-internal-sales-operations-android-app-frne8i ours
```

Primary fix command for repeated failures:

```bash
REMOTE_URL=https://github.com/Aneerajreddy/capitol-infra-internal-marketing-tool.git \
  bash scripts/merge-pr-safe.sh main codex/develop-internal-sales-operations-android-app-frne8i ours
```

If merge issues still persist, bypass history by creating a clean new repository:

```bash
bash scripts/create-clean-repo.sh ../capitol-infra-internal-marketing-tool-clean
```
