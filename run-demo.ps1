$ErrorActionPreference = "Stop"

$jdk = "C:\Users\Lenovo\Downloads\java-21-openjdk-21.0.4.0.7-1.win.jdk.x86_64\java-21-openjdk-21.0.4.0.7-1.win.jdk.x86_64"
$java = Join-Path $jdk "bin\java.exe"

if (-not (Test-Path $java)) {
    throw "Java runtime not found: $java"
}

if (-not (Test-Path "build\classes\edu\kbtu\university\Main.class")) {
    & ".\compile.ps1"
}

& $java -cp "build\classes" edu.kbtu.university.Main demo
