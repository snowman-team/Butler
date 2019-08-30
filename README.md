Snowball Android Butler <br> [ ![Download](https://api.bintray.com/packages/aquarids/maven/butler/images/download.svg?version=0.1.0) ](https://bintray.com/aquarids/maven/butler/0.1.0/link)
============

A butler helps get the application context everywhere and separates the startup process.

## Installation

```groovy
dependencies {
    // add dependency, please replace x.y.z to the latest version
    implementation "com.xueqiu.butler:butler:x.y.z"
}
```

## Usage

Create your loaders.
```kotlin
@AppLoader(ApplicationLoader.LOADER_NAME)
class ApplicationLoader : BaseLoader() {

    companion object {
        const val LOADER_NAME = "application"
    }

    override fun execute() {
        Log.i("application loader", "execute loader")
        // do something
    }

}

@AppLoader(MainLoader.LOADER_NAME)
class MainLoader : BaseLoader() {

    companion object {
        const val LOADER_NAME = "main"
    }

    override fun execute() {
        Log.i("main loader", "execute loader")
        // do something
    }

}
```

Initialize Butler in your Application's onCreate method and register your loader.
```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Butler.init(this)

        Butler.register(ApplicationLoader())
        Butler.register(MainLoader())

        // execute your loader in different places according to its work
        Butler.executeLoader(ApplicationLoader.LOADER_NAME)
    }
}
```

Set the callback for the executor.
```kotlin
Butler.executeLoader(MainLoader.LOADER_NAME, object : LoaderCallback {

    override fun onSuccess() {
        Log.i("execute main loader", "success")
    }

    override fun onFail(e: Exception) {
        Log.e("execute main loader", "fail", e)
    }
})
```

Now you can get current activity and applicationContext everywhere.
```kotlin
Butler.getCurrentActivity()

Butler.getAppContext()
```