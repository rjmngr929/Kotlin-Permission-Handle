/*
 * Copyright (c) 2020 Giorgio Antonioli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotlinpermissions.builder

import android.app.Activity
import android.os.Build
import com.kotlinpermissions.request.PermissionRequest
import com.kotlinpermissions.request.manifest.ManifestPermissionRequest
import com.kotlinpermissions.request.runtime.RuntimePermissionHandler
import com.kotlinpermissions.request.runtime.RuntimePermissionHandlerProvider
import com.kotlinpermissions.request.runtime.RuntimePermissionRequest

/**
 * Implementation of [BasePermissionRequestBuilder] that creates a different request depending
 * on the device's API version.
 *
 * Since Android M, this builder creates a [RuntimePermissionRequest] that uses the
 * [RuntimePermissionHandler] provided by this builder.
 * Below Android M, this builder creates a [ManifestPermissionRequest].
 *
 * @property activity the [Activity] used to create the [PermissionRequest].
 */
public class CompatPermissionRequestBuilder internal constructor(private val activity: Activity) : BasePermissionRequestBuilder() {
    override fun createRequest(
        permissions: Array<out String>,
        runtimeHandlerProvider: RuntimePermissionHandlerProvider
    ): PermissionRequest = if (Build.VERSION.SDK_INT >= 23) {
        // Provide the handler.
        val handler = runtimeHandlerProvider.provideHandler()
        // Create the runtime request.
        RuntimePermissionRequest(activity, permissions, handler)
    } else {
        // Create the manifest request.
        ManifestPermissionRequest(activity, permissions)
    }
}
