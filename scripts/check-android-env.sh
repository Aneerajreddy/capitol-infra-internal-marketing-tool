#!/usr/bin/env bash
set -euo pipefail

echo "[1/4] Checking Java..."
if ! command -v java >/dev/null 2>&1; then
  echo "❌ Java not found in PATH"
  exit 1
fi
JAVA_VER=$(java -version 2>&1 | head -n 1)
echo "Found: $JAVA_VER"
if [[ "$JAVA_VER" != *'"17.'* && "$JAVA_VER" != *'"17"'* ]]; then
  echo "⚠️  Recommended Java version is 17 for this Android project."
  if [[ -x "/root/.local/share/mise/installs/java/17.0.2/bin/java" ]]; then
    echo "   Suggested fix:"
    echo "   export JAVA_HOME=/root/.local/share/mise/installs/java/17.0.2"
    echo "   export PATH=\$JAVA_HOME/bin:\$PATH"
  fi
else
  echo "✅ Java version looks compatible (17)."
fi

echo "[2/4] Checking Android SDK env vars..."
SDK_PATH="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
if [[ -z "${SDK_PATH}" ]]; then
  echo "⚠️  ANDROID_HOME / ANDROID_SDK_ROOT is not set."
else
  echo "✅ SDK path: ${SDK_PATH}"
fi

echo "[3/4] Checking Gradle wrapper files..."
if [[ -f "gradlew" && -f "gradlew.bat" && -f "gradle/wrapper/gradle-wrapper.properties" ]]; then
  echo "✅ Gradle wrapper scripts and properties are present."
  if [[ -f "gradle/wrapper/gradle-wrapper.jar" ]]; then
    echo "✅ gradle-wrapper.jar found."
  else
    echo "⚠️  gradle-wrapper.jar is missing (binary file omitted intentionally)."
    echo "   Run: gradle wrapper --gradle-version 8.14.3"
  fi
else
  echo "❌ Missing Gradle wrapper scripts/properties."
  exit 1
fi

echo "[4/4] Done."
