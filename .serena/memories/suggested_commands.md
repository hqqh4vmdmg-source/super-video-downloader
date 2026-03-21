From repository root on Linux:
- ./gradlew --no-daemon :app:compileDebugJavaWithJavac  # verify Android compile/data binding
- ./gradlew --no-daemon :app:testDebugUnitTest :app:lintDebug  # targeted verification command
- ./gradlew --no-daemon :app:assembleDebug  # build debug APK (triggers Go/native setup)
- ./gradlew --no-daemon tasks --all  # inspect available tasks
Useful shell commands: git --no-pager status, git --no-pager diff, rg <pattern>, find . -maxdepth N