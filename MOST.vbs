set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("AllUsersDesktop")
set oShellLink = WshShell.CreateShortcut(strDesktop & "\MOST.lnk")
oShellLink.TargetPath = "C:\Program Files\Rutgers_MOST\MOST.exe"
oShellLink.WindowStyle = 1
oShellLink.IconLocation = "C:\Program Files\Rutgers_MOST\etc\most32.ico"
oShellLink.Description = "Shortcut Script"
oShellLink.WorkingDirectory = "C:\Program Files\Rutgers_MOST"
oShellLink.Save