# TremorVideo-Android-SDK
- SDK Version: 3.12.0
- Release Date: April 20th, 2016

# To Download
Please [contact the publisher team](mailto: PublisherManagement@Tremorvideo.com)  at Tremor Video to download the latest SDK. SDK 3.12 contains
- **Lib** which includes TremorVideo library Tremorvideo.aar
- **Doc** which includes Tremor Video Android SDK.pdf (SDK integration documentation)
- **TremorVideoExample** which includes a sample app

# New in SDK 3.12.0
- Tremor Video’s SDK is provided as an aar instead of a jar. Please follow SDK integration note below to update installation of the Tremor SDK
- Added new ad formats and MOAT viewability measurement. Integrating this SDK will help improving your fill rate.
- Bug fixes

# Android OS and Play Service Support
- Android OS 4.0.3 - 4.0.4 (API Level 15) is the minimum OS version supported from Android SDK v3.12.0
- Android SDK 3.12.0 supports Google Play Service revion 27+ (version 8.3+)
- Android SDK requires the use of Google Play Services. Please read this page for more details about setting up Play Services for your project: http://developer.android.com/google/play-services/setup.html

# Android Permission Requirement
Tremor Video SDK requires these permission in order to work properly to maxiumize your revenue.
- android.permission.INTERNET
- android.permission.ACCESS_NETWORK_STATE
- android.permission.READ_PHONE_STATE
- android.permission.WRITE_EXTERNAL_STORAGE
- com.google.android.providers.gsf.permission.READ_GSERVICES


# SDK Integration
- If you have already integrated Tremor iOS SDK in your applications, please review [Update from Earlier Versions](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Updating-from-Earlier-Versions) to update your SDK to the latest SDK.
- If you are new users of Tremor Android SDK, please review [Android Library Integration] (https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Android-Library-Integration) which contains detailed integration instructions.
- [Tremor Video Android API Doc] (http://tremorvideomobile.github.io/android/javadoc/) contains details about each API definitions.

# SDK Integration through mediaiton
Tremor supports Android mediation throught FreeWheel, Google AdMob/DFP, Fyber, Mopub (as a custom network), [Android SDK Medaition](https://github.com/TremorVideoMobile/TremorVideo-Android-SDK/wiki/Android-SDK-Mediation) contains all the details on how to integrate Tremor SDK through the mediaiton. If you integrate Tremor SDK through mediation, you should always go through this documentation to know the requirement to use Tremor SDK.
