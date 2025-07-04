# Clipboard Access on Android 10+ via ADB and Shell

## Introduction

Starting from Android 10, Google introduced a **privacy restriction on clipboard access**: only the currently focused app or input methods (IMEs) can read the clipboard, and applications can not read the clipboard in the background even with root access. This change significantly limits developers' ability to monitor or control the clipboard via ADB or automation tools. 

To address this limitation, this project provides a new solution: **a custom software input method**. By acting as an IME, this app can access the clipboard without restrictions, while still communicating with users or scripts via ADB or Shell.

---

## Usage

1. **Download and Install**
   - Get the release APK from the [GitHub Releases](#) page and install it on your device.

2. **Enable the Input Method**
   ```sh
   adb shell ime enable com.example.input/.MyInputMethodService

---

## Examples

Here are some example commands to use via ADB. In some devices, you must set the target application explicitly. Otherwise, the broadcast will not be received.

- **Read Clipboard Content**
  ```sh
  adb shell am broadcast -a com.example.input.ACTION_READ_CLIPBOARD
  or
  adb shell am broadcast -a com.example.input.ACTION_READ_CLIPBOARD -n com.example.input/.ClipboardReceiver
  ```

- **Set Clipboard Content (No Spaces)**
  ```sh
  adb shell am broadcast -a com.example.input.ACTION_SET_CLIPBOARD --es com.example.input.EXTRA_CLIPBOARD_TEXT "YourNewClipboardTestWithoutAnySpace"
  or
  adb shell am broadcast -a com.example.input.ACTION_SET_CLIPBOARD --es com.example.input.EXTRA_CLIPBOARD_TEXT "YourNewClipboardTestWithoutAnySpace"  -n com.example.input/.ClipboardReceiver
  ```

- **Set Clipboard Content (With Spaces)**
  ```sh
  adb shell am broadcast -a com.example.input.ACTION_SET_CLIPBOARD --es com.example.input.EXTRA_CLIPBOARD_TEXT "\"Your new clipboard text, and you can have space here\""
  or
  adb shell am broadcast -a com.example.input.ACTION_SET_CLIPBOARD --es com.example.input.EXTRA_CLIPBOARD_TEXT "\"Your new clipboard text, and you can have space here\""  -n com.example.input/.ClipboardReceiver
  ```

- **Clear Clipboard**
  ```sh
  adb shell am broadcast -a com.example.input.ACTION_CLEAR_CLIPBOARD
  or
  adb shell am broadcast -a com.example.input.ACTION_CLEAR_CLIPBOARD  -n com.example.input/.ClipboardReceiver
  ```
---

## Reproducing

To reproduce and test the functionality of this project, it is recommended to use **use [Android Studio Narwhal | 2025.1.1 June 24, 2025]([https://developer.android.com/studio](https://developer.android.com/studio/archive))**.  
You may download the code from this repository and open it with Android Studio Narwhal directly. Older versions of Android Studio may also work, but not guaranteed.

---

## Notes

- This input method must be **enabled and selected** for clipboard commands to work.

## Personal Notes

Google developers tend to make bewildering implementations. I understand that security is important, but at least they should leave a button (can be with warnings) for users to proactively grant the clipboard access to the apps they want. It is true that some users may ignore the warnings, but ultimately, it is the users' choice. Just like Xiaomi, OPPO, and other companies allow users to install unknown apps after clicking "I have read the warnings and understand the potential consequences". The clipboard should be the same. Google even blocks reading the clipboard with root access. Besides, there is still a security vulnerability since malicious applications can pretend to be an input method (Just like this project, though this project is not malicious). So the most secure way is to remove the clipboard so that nobody can read it LOL.
