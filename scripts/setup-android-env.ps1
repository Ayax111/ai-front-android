param(
    [string]$SdkPath
)

$ErrorActionPreference = "Stop"

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..")

if (-not $SdkPath -or [string]::IsNullOrWhiteSpace($SdkPath)) {
    if ($env:ANDROID_SDK_ROOT) {
        $SdkPath = $env:ANDROID_SDK_ROOT
    } elseif ($env:ANDROID_HOME) {
        $SdkPath = $env:ANDROID_HOME
    } else {
        $SdkPath = Join-Path $env:LOCALAPPDATA "Android\Sdk"
    }
}

if (-not (Test-Path -LiteralPath $SdkPath)) {
    throw "No existe Android SDK en '$SdkPath'. Instala Android command-line tools o define ANDROID_SDK_ROOT."
}

$localPropertiesPath = Join-Path $repoRoot "local.properties"
$escapedSdkPath = $SdkPath -replace "\\", "\\\\"
"sdk.dir=$escapedSdkPath" | Set-Content -Path $localPropertiesPath -Encoding UTF8

Write-Host "local.properties generado en: $localPropertiesPath"
Write-Host "sdk.dir=$SdkPath"

$adbCandidate = Join-Path $SdkPath "platform-tools\adb.exe"
if (Test-Path -LiteralPath $adbCandidate) {
    Write-Host "adb detectado: $adbCandidate"
} else {
    Write-Host "No se encontro adb.exe en platform-tools. Instala 'platform-tools' con sdkmanager."
}
