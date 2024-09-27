Kotlin Runtime Handle Permissions
===============

An Android library totally written in Kotlin that helps to request runtime permissions.
This library is compatible also below Android M (API 23) where runtime permissions doesn't exist, so you haven't to handle them separately. 

Usage
------


For further samples, check the [app](https://github.com/rjmngr929/Kotlin-Permission-Handle/tree/master/app) provided by this library. It shows how to integrate this library and request the permissions from an Activity or a Fragment.

### Basic usage
You can create a ```PermissionRequest``` either from an ```Activity``` or a ```Fragment``` using the extension method ```permissionsBuilder()```:

```kotlin
// Build the request with the permissions you would like to request and send it.
permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.SEND_SMS).build().send { result ->
    // Handle the result, for example check if all the requested permissions are granted.
    if (result.allGranted()) {
       // All the permissions are granted.
    }
}
```

#### Coroutines
The artifact `kotlinpermissions-coroutines` adds the integration with the Kotlin coroutines:
```kotlin
launch {
    val result = permissionsBuilder(Manifest.permission.CAMERA).build().sendSuspend()
    // Handle the result.
}
```

It also supports the Kotlin coroutines `Flow` API:
```kotlin
val request = permissionsBuilder(Manifest.permission.CAMERA).build()
launch {
    request.flow().collect { result ->
        // Handle the result. 
    }
}
request.send()
```


#### LiveData
In the core artifact, there's a useful extension on `PermissionRequest` to get a `LiveData`:
```kotlin
val request = permissionsBuilder(Manifest.permission.CAMERA).build()
request.liveData().observe(this) { result ->
    // Handle the result.
}
request.send()
```

Compatibility
------

**Android SDK**: Kotlin-Permission-Handle requires a minimum API level of **21** (the same of the latest support libraries).

**AndroidX**: this library requires AndroidX. To use it in a project without AndroidX, refer to the version **1.x**

Integration
------

```gradle
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

```gradle
dependencies {
	        implementation 'com.github.rjmngr929:Kotlin-Permission-Handle:Tag'
	}
```
