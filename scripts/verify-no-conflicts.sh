#!/usr/bin/env bash
set -euo pipefail

files=(
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

for f in "${files[@]}"; do
  if rg -n "<<<<<<<|=======|>>>>>>>" "$f" >/dev/null; then
    echo "Conflict markers found in $f"
    exit 1
  fi
done

echo "No conflict markers found in required files."
