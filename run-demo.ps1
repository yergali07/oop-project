$ErrorActionPreference = "Stop"

# Use java from PATH. Override by setting $env:JAVA_HOME before invoking.
$java = if ($env:JAVA_HOME) {
    Join-Path $env:JAVA_HOME "bin\java.exe"
} else {
    "java"
}

if (-not (Test-Path "build\classes\edu\kbtu\university\Main.class")) {
    & ".\compile.ps1"
}

& $java -cp "build\classes" edu.kbtu.university.Main demo
