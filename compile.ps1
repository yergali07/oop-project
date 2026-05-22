$ErrorActionPreference = "Stop"

$jdk = "C:\Users\Lenovo\Downloads\java-21-openjdk-21.0.4.0.7-1.win.jdk.x86_64\java-21-openjdk-21.0.4.0.7-1.win.jdk.x86_64"
$javac = Join-Path $jdk "bin\javac.exe"

if (-not (Test-Path $javac)) {
    throw "JDK compiler not found: $javac"
}

New-Item -ItemType Directory -Force "build\classes" | Out-Null
$sources = Get-ChildItem -Recurse -Filter *.java "src\main\java" | ForEach-Object { $_.FullName }

& $javac -encoding UTF-8 -source 17 -target 17 -d "build\classes" $sources

if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Host "Compiled to build\classes"
