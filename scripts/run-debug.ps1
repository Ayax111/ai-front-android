param(
    [string]$ApplicationId = "com.ayax.iafront",
    [string]$ActivityName = "com.ayax.iafront.MainActivity"
)

$ErrorActionPreference = "Stop"
$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..")

$adb = "adb"
try {
    & $adb version | Out-Null
} catch {
    throw "adb no esta en PATH. Agrega platform-tools al PATH o usa la ruta completa de adb."
}

Push-Location $repoRoot
try {
    & ".\gradlew.bat" installDebug
    & $adb shell am start -n "$ApplicationId/$ActivityName"
} finally {
    Pop-Location
}
