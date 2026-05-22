$ErrorActionPreference = "Stop"

# Use javac from PATH. Override by setting $env:JAVA_HOME before invoking.
$javac = if ($env:JAVA_HOME) {
    Join-Path $env:JAVA_HOME "bin\javac.exe"
} else {
    "javac"
}

New-Item -ItemType Directory -Force "build\classes" | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java "src\main\java" | ForEach-Object { $_.FullName }

& $javac -encoding UTF-8 -source 17 -target 17 -d "build\classes" $sources

if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Host "Compiled to build\classes"
