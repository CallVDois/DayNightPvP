@echo off
chcp 65001 >nul
title Script feito por Claudinei Junior

:: Verificar se está sendo executado como administrador
net session >nul 2>&1
if %errorlevel% neq 0 (
    echo Solicitando permissões de administrador...
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
	echo UAC.ShellExecute "%~fn0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
	"%temp%\getadmin.vbs"
	exit /b
)

:menu
color 0A
cls
echo.
echo Computador: %computername%    Usuario: %username%
echo.
echo ^+---------------------------------------------^+
echo ^|                    MENU                     ^|
echo ^+---------------------------------------------^+
echo ^| 1. Instalar                                 ^|
echo ^| 2. Desinstalar                              ^|
echo ^| 3. Sair                                     ^|
echo ^+---------------------------------------------^+
echo.

set /p opcao=Escolha uma opcao: 

:: Verificar se a opção é válida
if "%opcao%"=="" (
    echo Opcao invalida, tente novamente.
    call :pausar 2
    goto menu
)

if %opcao% equ 1 goto install
if %opcao% equ 2 goto uninstall
if %opcao% equ 3 goto end

goto operror

:install
cls
echo ^+---------------------------------------------^+
echo ^|                 Instalador                  ^|
echo ^+---------------------------------------------^+
echo:

call :verificar_internet
call :create_folder "%PROGRAMDATA%\Claudinei" "Claudinei"

call :download_file "ScriptLauncher.zip" "%PROGRAMDATA%\Claudinei\ScriptLauncher.zip"

call :extract_file "ScriptLauncher.zip" "%PROGRAMDATA%\Claudinei"

call :create_task "ScriptClaudinei" "%PROGRAMDATA%\Claudinei\ScriptLauncher.cmd"

call :run_task "ScriptClaudinei"

echo Script instalado com sucesso.
call :pausar 5
goto menu

:uninstall
cls
echo ^+---------------------------------------------^+
echo ^|                Desinstalador                ^|
echo ^+---------------------------------------------^+
echo:
call :delete_task "ScriptClaudinei"
call :delete_folder "%PROGRAMDATA%\Claudinei" "Claudinei"
echo Script desinstalado com sucesso.
call :pausar 5
goto menu

:operror
cls
echo ^+---------------------------------------------^+
echo ^| Opcao Invalida! Escolha outra opcao do menu ^|
echo ^+---------------------------------------------^+
pause
goto menu

:create_task
set "TASK_NAME=%~1"
set "SCRIPT_TO_RUN=%~2"
echo Criando a task '%TASK_NAME%'...
schtasks /create /tn "%TASK_NAME%" /tr "%SCRIPT_TO_RUN%" /sc onstart /ru SYSTEM /RL HIGHEST >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Erro ao criar a task '%TASK_NAME%'. Verifique permissões.
    call :pausar 5
    exit
)
echo Task '%TASK_NAME%' criada com sucesso.
call :pausar 1
exit /b 0

:run_task
set "TASK_NAME=%~1"
echo Executando a task '%TASK_NAME%'...
schtasks /run /tn "%TASK_NAME%" >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Erro ao executar a task '%TASK_NAME%'. Verifique se a task existe.
    call :pausar 5
    exit /b 1
)
echo Task '%TASK_NAME%' executada com sucesso.
call :pausar 1
exit /b 0

:delete_task
set "TASK_NAME=%~1"
echo Deletando a task '%TASK_NAME%'...
schtasks /delete /tn "%TASK_NAME%" /f >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Erro ao deletar a task '%TASK_NAME%'. Verifique se a task existe.
    call :pausar 5
    exit /b 1
)
echo Task '%TASK_NAME%' deletada com sucesso.
call :pausar 1
exit /b 0

:create_folder
set "FOLDER_PATH=%~1"
set "FOLDER_NAME=%~2"
echo Criando diretório '%FOLDER_NAME%'...
if exist "%FOLDER_PATH%" (
    echo O diretório '%FOLDER_NAME%' já existe.
    call :pausar 1
    exit /b 0
)
mkdir "%FOLDER_PATH%" >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Erro ao criar o diretório '%FOLDER_NAME%'. Verifique as permissões.
    call :pausar 5
    exit
)
echo Diretório '%FOLDER_NAME%' criado com sucesso.
call :pausar 1
exit /b 0

:delete_folder
set "DIR_PATH=%~1"
set "DIR_NAME=%~2"
echo Apagando a pasta '%DIR_NAME%'...
rmdir /S /Q "%DIR_PATH%" >nul 2>&1
if errorlevel 1 (
	color 0C
    echo Erro ao apagar '%DIR_NAME%'. Verifique se o diretório existe ou se você tem permissões suficientes.
	call :pausar 5
	exit
)
echo Pasta '%DIR_NAME%' apagada com sucesso.
call :pausar 1
exit /b 0

:download_file
set "FILE_TO_DOWNLOAD=%~1"
set "PATH_TO_SAVE=%~2"
echo Baixando o arquivo '%FILE_TO_DOWNLOAD%'...
curl -L "https://raw.githubusercontent.com/needkg/uninga-lab-script/refs/heads/main/%FILE_TO_DOWNLOAD%" -o "%PATH_TO_SAVE%" >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Erro ao baixar o arquivo '%FILE_TO_DOWNLOAD%'. Verifique sua conexão à internet.
    call :pausar 5
    exit
)
echo Arquivo '%FILE_TO_DOWNLOAD%' baixado com sucesso.
call :pausar 1
exit /b 0

:extract_file
set "ZIP_NAME=%~1"
set "PATH_TO_SAVE=%~2"
echo Extraindo o arquivo '%ZIP_NAME%'...
tar -xf "%PROGRAMDATA%\Claudinei\%ZIP_NAME%" -C "%PATH_TO_SAVE%" >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Erro ao extrair o arquivo ZIP. Verifique se o arquivo ZIP não está corrompido.
    call :pausar 5
    exit
)
echo Arquivo '%ZIP_NAME%' extraído com sucesso.
call :pausar 1
exit /b 0

:verificar_internet
echo Verificando a conexão com a Internet...
ping -n 1 www.google.com >nul 2>&1
if errorlevel 1 (
    color 0C
    echo Sem conexão com a Internet.
    call :pausar 5
    exit
)
echo Conexão com a Internet verificada com sucesso.
call :pausar 1
exit /b 0

:pausar
timeout /t %~1 /nobreak >nul 2>&1
echo:
exit /b 0

:end
cls
exit