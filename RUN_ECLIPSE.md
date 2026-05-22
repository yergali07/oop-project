# Запуск проекта в Eclipse

1. Откройте Eclipse.
2. Выберите `File -> Import -> General -> Existing Projects into Workspace`.
3. В `Select root directory` укажите папку `oop-project`.
4. Нажмите `Finish`.
5. Откройте `src/main/java/edu/kbtu/university/Main.java`.
6. Запустите файл через `Run As -> Java Application`.

Также можно открыть `Run -> Run Configurations...` и выбрать готовую конфигурацию `UniversitySystemDemo`.

Проект настроен как обычный Java-проект под Java 17. Maven-файл `pom.xml` также оставлен, поэтому проект можно импортировать и как Maven-проект, если в Eclipse установлен m2e.

Если системная команда `java` не работает, используйте найденный portable JDK:

```powershell
.\compile.bat
.\run-demo.bat
```

Для интерактивной консоли с логином:

```powershell
.\run-console.bat
```

Для запуска Eclipse с этим JDK:

```powershell
.\open-eclipse-with-jdk.bat
```
