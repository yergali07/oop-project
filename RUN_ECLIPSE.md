# Запуск проекта

Проект собирается под Java 17. Основной способ сборки — Maven (`pom.xml`):

```bash
mvn compile
mvn exec:java -Dexec.mainClass=edu.kbtu.university.Main
```

## Eclipse

1. `File -> Import -> Existing Maven Projects` и укажите корень `oop-project`.
   (Если m2e не установлен, можно импортировать как `Existing Projects into Workspace`,
   но IDE-файлы `.classpath`/`.project` теперь не коммитятся и сгенерируются локально.)
2. Откройте `src/main/java/edu/kbtu/university/Main.java`.
3. `Run As -> Java Application`.

## Windows без Maven

Для Windows-разработчиков добавлены вспомогательные скрипты. По умолчанию они
берут `java`/`javac` из `PATH`; если нужно указать конкретный JDK — задайте
`JAVA_HOME` перед запуском:

```powershell
$env:JAVA_HOME = "C:\Path\To\JDK"
.\compile.bat
.\run-demo.bat        # запускает Main с аргументом 'demo' (smoke-сценарий)
.\run-console.bat     # запускает Main без аргументов
```

`.bat` — тонкие обёртки над одноимёнными `.ps1`. Скрипты компилируют исходники
в `build\classes\` (этот путь игнорируется git'ом).
