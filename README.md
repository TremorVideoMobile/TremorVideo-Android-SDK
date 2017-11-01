# TremorVideo-Android-SDK
- SDK Version: 3.15.0
- Release Date: November 1, 2017
- Release Notes: Please visit [Android SDK Releases Notes](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Android-SDK-Release-Notes) to check details of each Android SDK release.

# To Download
Please contact the [publisher team](PublisherManagement@Tremorvideo.com)  at Tremor Video to download the latest SDK. SDK 3.15.0 contains
- **Lib** which includes TremorVideo library Tremorvideo.aar
- **Doc** which includes Tremor Video Android SDK.pdf (SDK integration documentation)
- **TremorVideoExample** which includes a sample app

# New in SDK 3.15.0
- VPAID 2.0 is now supported

# Android OS and Play Service Support
- Android OS 4.4 (API Level 19) is the minimum OS version supported from Android SDK v3.15.0
- Android SDK 3.13.0 supports Google Play Service revision 27+ (version 8.3+)
- Android SDK requires the use of Google Play Services. Please read this page for more details about setting up Play Services for your project: http://developer.android.com/google/play-services/setup.html

# Android Permission Requirement
Tremor Video SDK requires these permission in order to work properly to maxiumize your revenue.
- android.permission.INTERNET
- android.permission.ACCESS_NETWORK_STATE
- android.permission.WRITE_EXTERNAL_STORAGE

Adding the optional permissions listed in [Android Library Integration](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Android-Library-Integration) would help you improve fill rate and user experience.

# SDK Integration
- If you have already integrated Tremor iOS SDK in your applications, please review [Update from Earlier Versions](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Updating-from-Earlier-Versions) to update your SDK to the latest SDK.
- If you are new users of Tremor Android SDK, please review [Android Library Integration](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Android-Library-Integration) which contains detailed integration instructions.
- [Tremor Video Android API Doc](http://tremorvideomobile.github.io/android/javadoc/) contains details about each API definitions.
- This project also includes a sample which demostrates how to integration Android SDK into your application.

# SDK Integration through mediation
Tremor supports Android mediation including FreeWheel, Google AdMob/DFP, Fyber, Mopub (as a custom network), [Android SDK Mediation](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Android-SDK-Mediation) contains all the details on how to integrate Tremor SDK through the mediaiton. If you integrate Tremor SDK through mediation, you should always go through this documentation to know the requirements to configure your applicaton in order to use Tremor SDK.
