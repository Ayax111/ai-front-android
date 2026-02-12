param(
    [string]$SdkPath = "$env:LOCALAPPDATA\Android\Sdk",
    [string[]]$Packages = @(
        "platform-tools",
        "platforms;android-35",
        "build-tools;35.0.0"
    ),
    [switch]$AcceptLicenses = $true,
    [switch]$PersistEnv = $true
)

$ErrorActionPreference = "Stop"

function Get-JavaHomeFromPath {
    $javaCmd = Get-Command java -ErrorAction SilentlyContinue
    if (-not $javaCmd) {
        return $null
    }
    $javaExe = $javaCmd.Source
    return Split-Path -Parent (Split-Path -Parent $javaExe)
}

function Ensure-CmdlineTools {
    param(
        [Parameter(Mandatory = $true)][string]$SdkRoot
    )

    $cmdlineToolsLatest = Join-Path $SdkRoot "cmdline-tools\latest"
    $sdkManagerBat = Join-Path $cmdlineToolsLatest "bin\sdkmanager.bat"
    if (Test-Path -LiteralPath $sdkManagerBat) {
        return $sdkManagerBat
    }

    New-Item -ItemType Directory -Force -Path $SdkRoot | Out-Null
    $tempRoot = Join-Path $env:TEMP "android-sdk-bootstrap"
    New-Item -ItemType Directory -Force -Path $tempRoot | Out-Null
    $zipPath = Join-Path $tempRoot "commandlinetools-win.zip"

    $toolUrls = @(
        "https://dl.google.com/android/repository/commandlinetools-win-11076708_latest.zip",
        "https://dl.google.com/android/repository/commandlinetools-win-10406996_latest.zip",
        "https://dl.google.com/android/repository/commandlinetools-win-9477386_latest.zip"
    )

    $downloaded = $false
    foreach ($url in $toolUrls) {
        try {
            Write-Host "Descargando: $url"
            Invoke-WebRequest -Uri $url -OutFile $zipPath
            $downloaded = $true
            break
        } catch {
            Write-Host "No disponible: $url"
        }
    }

    if (-not $downloaded) {
        throw "No se pudo descargar Android command-line tools."
    }

    $extractPath = Join-Path $tempRoot "extracted"
    if (Test-Path -LiteralPath $extractPath) {
        Remove-Item -LiteralPath $extractPath -Recurse -Force
    }
    Expand-Archive -Path $zipPath -DestinationPath $extractPath -Force

    $inner = Join-Path $extractPath "cmdline-tools"
    if (-not (Test-Path -LiteralPath $inner)) {
        throw "No se encontro carpeta cmdline-tools en el zip descargado."
    }

    New-Item -ItemType Directory -Force -Path $cmdlineToolsLatest | Out-Null
    Copy-Item -Path (Join-Path $inner "*") -Destination $cmdlineToolsLatest -Recurse -Force

    if (-not (Test-Path -LiteralPath $sdkManagerBat)) {
        throw "sdkmanager.bat no quedo disponible tras la extraccion."
    }

    return $sdkManagerBat
}

if (-not $env:JAVA_HOME) {
    $detectedJavaHome = Get-JavaHomeFromPath
    if ($detectedJavaHome) {
        $env:JAVA_HOME = $detectedJavaHome
        Write-Host "JAVA_HOME detectado: $env:JAVA_HOME"
    }
}

if (-not $env:JAVA_HOME) {
    throw "No se encontro JAVA_HOME. Instala JDK 17 y define JAVA_HOME."
}

New-Item -ItemType Directory -Force -Path $SdkPath | Out-Null
$sdkManager = Ensure-CmdlineTools -SdkRoot $SdkPath

if ($AcceptLicenses) {
    Write-Host "Aceptando licencias Android SDK"
    ("y`n" * 40) | & $sdkManager --sdk_root=$SdkPath --licenses | Out-Null
}

Write-Host "Instalando paquetes Android en $SdkPath"
& $sdkManager --sdk_root=$SdkPath @Packages

$platformTools = Join-Path $SdkPath "platform-tools"
$buildTools = Join-Path $SdkPath "build-tools\35.0.0"
$cmdToolsBin = Join-Path $SdkPath "cmdline-tools\latest\bin"

if ($PersistEnv) {
    [Environment]::SetEnvironmentVariable("ANDROID_SDK_ROOT", $SdkPath, "User")
    [Environment]::SetEnvironmentVariable("ANDROID_HOME", $SdkPath, "User")

    $currentUserPath = [Environment]::GetEnvironmentVariable("Path", "User")
    $pathEntries = @($platformTools, $cmdToolsBin, $buildTools)
    $newPath = $currentUserPath
    foreach ($entry in $pathEntries) {
        if (-not $newPath) {
            $newPath = $entry
            continue
        }
        $escaped = [Regex]::Escape($entry)
        if ($newPath -notmatch "(?i)(^|;)$escaped(;|$)") {
            $newPath = "$newPath;$entry"
        }
    }
    [Environment]::SetEnvironmentVariable("Path", $newPath, "User")
}

Write-Host ""
Write-Host "Instalacion completada."
Write-Host "ANDROID_SDK_ROOT sugerido: $SdkPath"
Write-Host "Agrega al PATH:"
Write-Host " - $platformTools"
Write-Host " - $cmdToolsBin"
Write-Host " - $buildTools"
