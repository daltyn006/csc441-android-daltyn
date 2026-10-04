1. First build took about 10 seconds, second under 100ms
2. I default my phone to dark mode, so the background was automatically dark and i did not even think about that
3. Not fully understanding the intro png, it loads too quickly for it to realistically have an effect past an .ico on the app directory

// lab 7

2026-10-02 16:25:26.704 28086-28086 InputEventReceiver edu.lemoyne.campusapp E Exception in NativeInputEventReceiver callbacks
2026-10-02 16:25:26.704 28086-28086 InputEventReceiver edu.lemoyne.campusapp E Failed to dispatch motion event to Java
2026-10-02 16:25:26.709 28086-28086 AndroidRuntime edu.lemoyne.campusapp D Shutting down VM
2026-10-02 16:25:26.724 28086-28086 AndroidRuntime edu.lemoyne.campusapp E FATAL EXCEPTION: main
Process: edu.lemoyne.campusapp, PID: 28086

...
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:906)
2026-10-02 16:25:26.836 28086-28086 Process edu.lemoyne.campusapp I Sending signal. PID: 28086 SIG: 9
2026-10-02 16:25:26.877 756-1132 InputDispatcher system_server E channel 'f789978 edu.lemoyne.campusapp/edu.lemoyne.campusapp.MainActivity' ~ Channel is unrecoverably broken and will be disposed!
---------------------------- PROCESS ENDED (28086) for package edu.lemoyne.campusapp ----------------------------
2026-10-02 16:25:28.509 756-2234 WindowOrga...Controller system_server E Attempting to externally change a non-organized container: Task{cf9bd92 #33 type=standard A=10230:edu.lemoyne.campusapp}={handlePackageUpdate:false,} playercount=2 taskorg=android.window.ITaskOrganizer$Stub$Proxy@df1799

2. The screen did not update since count was held in a standard local variable instead of a compose object.
3. remember makes sure that the screen keeps a variable after the UI is destroyed and recomposed. without remember compose would lose the

|     | I typed         | What the app did    | Correct |
| --- | --------------- | ------------------- | ------- |
| 1   | nothing         | the button was gray | true    |
| 2   | spaces          | the button was grey | true    |
| 3   | Abaracadabara   | Add Chacacter       | true    |
| 4   | Abaracada56bara | Add Character       | true    |
| 5   | 6Abacacadabara's | Add Character       | false?    |
| 6   | Abac-acadabara' | Add Character       | true    |
| 7   | A$ba(acadabara  | Use Non-Special Characters       | true    |
| 8   | bracadabra      | Use Correct Title       | true    |
