@ECHO OFF
REM Updated 11/12/2011 BC
REM  This batch file is used to update the Genesys Demo Image to use a new IP Address
setlocal

:: Check command line parameter
ECHO.%* | FIND "?" >NUL
IF NOT ERRORLEVEL 1 GOTO Syntax

:: Check that there are at least 1 but not more than 2 command line parameters, except for AUTO mode
IF "%1" == "" GOTO Syntax

:: Set default IP to be changed - we will override later if they included a second parametr
set oldipaddress=192.168.10.98
ECHO %oldipaddress%
:: If there is more than 1 command line parameter, then we will allow assume they are changing a non default value
:: so first check to see if there is a second parameter.  If not continue on to confirmation.  If so use parameter as new value
IF "%2" == "" GOTO CONFIRMWARNING
set oldipaddress=%2
IF "%3" == "AUTO" GOTO MAIN

:CONFIRMWARNING
::Give one last warning
CLS
ECHO.
ECHO  Current IP Address:    %oldipaddress%
ECHO  NEW IP Address:        %1
ECHO.
ECHO Please verify this data.  If the value(s) listed above are incorrect, DO NOT PROCEED.
ECHO.
CHOICE /M "Shall I proceed?"
IF ERRORLEVEL 2 GOTO End
GOTO CHANGEFILES


:MAIN
ECHO .
ECHO .
ECHO Stopping Genesys Services
ECHO .
net stop ComposerTomcat
net stop GDA
net stop "Genesys Licensing"
net stop MsgServer64
net stop LCA64
net stop ConfigServerMT64
net stop DBServer64
net stop DBServer64_1
net stop DBServer64_2
net stop DBServer64_3
net stop DBServer64_4
net stop SNMPMasterAgnt64
net stop SCServer64
:CHANGEFILES
ECHO .
ECHO .
ECHO . Changing files
fart C:\Windows\System32\drivers\etc\hosts %oldipaddress% %1
fart c:\gcti\01*.vbs %oldipaddress% %1
fart c:\gcti\02*.vbs %oldipaddress% %1
fart "C:\Program Files (x86)\GCTI\Genesys SIP Phone\Config\*.xml" %oldipaddress% %1
fart "C:\Program Files\PostgreSQL\9.2\data\pg_hba.conf" %oldipaddress% %1
fart "C:\Users\Administrator\GWMProxy\config.xml" %oldipaddress% %1
fart "C:\inetpub\wwwroot\gms\sipcli\sipcli.ini"  %oldipaddress% %1
ECHO .

:loop
sleep 5
ECHO Checking that SQL Server port is opened
netstat -o -n -a | findstr 0.0.0.0:1433
if %ERRORLEVEL% equ 0 GOTO goahead
GOTO loop

:goahead


ECHO .
ECHO . Changing GCTI Database
rem sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_host SET ip_address='%1' where dbid=102"
sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_host SET ip_address='%1', name='%1' where dbid=102"
sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_flex_prop SET prop_value=REPLACE(prop_value, '%oldIPaddress%', '%1') where object_dbid=452 and prop_name='contact'"
sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_flex_prop SET prop_value=REPLACE(prop_value, '%oldIPaddress%', '%1') where object_dbid=450 and prop_name='contact'"

REM **
REM ** Commented out by BC 11/12/2011 **
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_server SET app_params=REPLACE(app_params, '%oldIPaddress%', '%1') where app_dbid=260 and app_server_dbid=266"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=140 and section='gli_server_group_1' and opt='gli-server-address'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=259 and section='gvp.rm' and opt='aor'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=259 and section='mediacontroller' and opt='bridge_server'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=259 and section='mediacontroller' and opt='sipproxy'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=259 and section='sip' and opt='routeset'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=263 and section='gvp.rm' and opt='aor'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=263 and section='sip' and opt='localrtpaddr'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=263 and section='gvp.rm' and opt='aor'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=263 and section='sip' and opt='routeset'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=265 and section='CTIC' and opt='RMIPAddr'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=265 and section='gvp.rm' and opt='aor'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=265 and section='IServer_Sample' and opt='iserveraddr'"
REM sqlcmd -S localhost -d gctimt -U sa -P sqlgenesys -Q "UPDATE cfg_app_option SET val=REPLACE(val, '%oldIPaddress%', '%1') where object_dbid=265 and section='sip' and opt='localhostname'"
REM **
ECHO.
ECHO.
ECHO The IP Change process is almost done. Just follow the instructions 
ECHO in the document that has been opened (complete.txt) to finish 
ECHO the name change.
ECHO.
ECHO.
IF "%3" == "AUTO" (
    net start "Genesys Licensing"
    net start DBServer64
    net start DBServer64_1
    net start DBServer64_2
    net start DBServer64_3
    net start DBServer64_4
    net start ConfigServerMT64
    net start MsgServer64
    net start LCA64
    net start SNMPMasterAgnt64
    net start SCServer64
    net start ComposerTomcat
    net start GDA
    GOTO END
)
start notepad complete.txt
GOTO end

:syntax
ECHO.
ECHO IP  Change Kit for localhost DEMO image
ECHO .
ECHO This utility will change the IP Address of the Genesys Demo image
ECHO .
ECHO Usage: CHANGEIP NewIPAddres [OldIPAddress]
ECHO .
ECHO .
ECHO ------
ECHO Example #1: CHANGEIP 172.20.34.99
ECHO .
ECHO             This example will change the IP address from the shipped default
ECHO             of 192.168.10.98 to the new IP address of 172.20.34.99.  Changes
ECHO             will be made in select files and database tables.
ECHO .
ECHO             This is the most common usage of the changeip program.
ECHO ------
ECHO .
ECHO .
ECHO ------
ECHO Example #2: CHANGEIP 10.10.38.65 172.20.34.99 
ECHO .
ECHO             This example will change the IP address from 172.20.34.99 to
ECHO             10.10.38.65.  This usage assumes that the IP address had already
ECHO             been changed from its default value using this tool.
ECHO ------
ECHO .
ECHO Note that Genesys Services will be stopped before this application executes.
ECHO .
PAUSE
GOTO end

:END
