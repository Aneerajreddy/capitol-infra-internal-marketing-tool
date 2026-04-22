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
