# PositionDialogFragment

This is an Android project developed in Kotlin and Java, using Gradle as the build system. The project demonstrates the use of a custom dialog fragment, `PositionDialogFragment`, which can be positioned on the screen according to the specified `PopupAttribute`.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Android Studio Flamingo | 2022.2.1 Patch 2 or later
- JDK 8 or later
- Android SDK

### Installing

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Add the JitPack repository to your build file. Add it in your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

4. Add the dependency in your module `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.hieud1245k:PositionDialogFragment:1.0.2'
}
```

5. Build and run the project on an emulator or a real device.

## Usage

The main activity, `MainActivity`, contains a TextView. When this TextView is clicked, it triggers the display of `PositionDialogFragment`. The position and size of this dialog fragment are determined by the `PopupAttribute` passed to it.

## Built With

- [Kotlin](https://kotlinlang.org/)
- [Java](https://www.java.com/)
- [Gradle](https://gradle.org/)

## Contributing

Please read `CONTRIBUTING.md` for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details.

## Acknowledgments

- Thanks to all contributors who participate in this project.
