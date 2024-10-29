<img align="right" src='https://github.com/user-attachments/assets/0de98482-d4be-4669-92d0-4effb3964ecc' width='30%'/>

### MysticLogger
Simple, pretty and powerful logger for android


<img src="https://img.shields.io/github/license/MAshhal/PrettyLogger?style=for-the-badge&logo=opensourceinitiative&logoColor=white&color=006f0c" alt="license">	<img src="https://img.shields.io/github/last-commit/MAshhal/PrettyLogger?style=for-the-badge&logo=git&logoColor=white&color=006f0c" alt="last-commit"> <img src="https://img.shields.io/github/languages/top/MAshhal/PrettyLogger?style=for-the-badge&color=006f0c" alt="repo-top-language">

### Setup
Download
```kotlin
implementation(io.github.mashhal:prettylogger:x.y.z)
```

Initialize and Usage
```kotlin
class App: Application(), PrettyLogger {
    override fun onCreate() {
        // ...
        addAdapter(AndroidLogAdapter())
        debug { "Hello World" }
        debug("Hello World!")
    }
}
```
OR 
```kotlin
class App: Application() {
    override fun onCreate() {
        // ...
        with(MysticLogger) {
            addAdapter(AndroidLogAdapter())
            debug { "Hello World" }
            debug("Hello World")
        }
    }
}
```


##  License
```
Copyright 2024 Muhammad Ashhal
Copyright 2018 Orhan Obut

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
---

##  Acknowledgments
- Original Work done by: Orhan obut in his [java implementation](https://github.com/orhanobut/logger)
  - Purpose of this library was to create a Kotlin implementation and keep it up-to-date
