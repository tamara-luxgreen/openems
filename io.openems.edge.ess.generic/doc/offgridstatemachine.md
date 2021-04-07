# State-Machine

```mermaid
graph LR
Undefined -->|target START | GridSwitch

GridSwitch -->|On Grid|StartBatteryInOnGrid
GridSwitch -->|Off Grid && soc >5 |StartBatteryInOffGrid
GridSwitch -->|Off Grid and --soc <5% or min Cell Volt < 2.8V--  |StopBatteryInverter

StartBatteryInOnGrid -->|not timeout| StartBatteryInOnGrid
StartBatteryInOnGrid -->|isStarted| StartBatteryInverterInOnGrid
StartBatteryInOnGrid -->|timeout| Undefined

StartBatteryInverterInOnGrid -->|not timeout| StartBatteryInverterInOnGrid
StartBatteryInverterInOnGrid -->|isStarted| StartedInOnGrid
StartBatteryInverterInOnGrid -->|timeout| Undefined

StartedInOnGrid -->|Battery+BatteryInverter.isStarted && everythingOk| StartedInOnGrid
StartedInOnGrid -->|isGridOff| StopBatteryInverterBeforeSwitch
StartedInOnGrid -->|timeout| Undefined

StopBatteryInverterBeforeSwitch -->|isInverterStopped| Undefined

StartBatteryInOffGrid -->|not timeout| StartBatteryInOffGrid
StartBatteryInOffGrid -->|isStarted| StartBatteryInverterInOffGrid
StartBatteryInOffGrid -->|timeout| Undefined

StartBatteryInverterInOffGrid -->|not timeout| StartBatteryInverterInOffGrid
StartBatteryInverterInOffGrid -->|isStarted| StartedInOffGrid
StartBatteryInverterInOffGrid -->|timeout| Undefined

StartedInOffGrid -->|Battery+BatteryInverter.isStarted && everythingOk| StartedInOffGrid
StartedInOffGrid -->|isGridOn| StopBatteryInverterBeforeSwitch
StartedInOffGrid -->|timeout| Undefined
StartedInOffGrid -->| soc <5% or min Cell Volt < 2.8V |StopBatteryInverter

Undefined -->|target STOP| StopBatteryInverter

StopBatteryInverter -->|not timeout| StopBatteryInverter
StopBatteryInverter -->|isStarted| StopBattery
StopBatteryInverter -->|timeout| Undefined

StopBattery -->|not timeout| StopBattery
StopBattery -->|isStopped| Stopped
StopBattery -->|timeout| Undefined

Stopped -->|Battery+BatteryInverter.isStopped && everythingOk| Stopped
Stopped -->|otherwise| Undefined

Undefined -->|hasFault| ErrorHandling
ErrorHandling -->|not timeout| ErrorHandling
ErrorHandling -->|eventually| Undefined
```

View using Mermaid, e.g. https://mermaid-js.github.io/mermaid-live-editor