$ErrorActionPreference = "Stop"

$env:JAVA_HOME = "C:\Users\Lenovo\Downloads\java-21-openjdk-21.0.4.0.7-1.win.jdk.x86_64\java-21-openjdk-21.0.4.0.7-1.win.jdk.x86_64"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

$shortcutPath = "C:\Users\Lenovo\Desktop\Eclipse IDE for Java Developers - 2025-12.lnk"
if (-not (Test-Path $shortcutPath)) {
    throw "Eclipse shortcut not found: $shortcutPath"
}

$shell = New-Object -ComObject WScript.Shell
$shortcut = $shell.CreateShortcut($shortcutPath)
Start-Process -FilePath $shortcut.TargetPath -ArgumentList $shortcut.Arguments -WorkingDirectory $shortcut.WorkingDirectory
