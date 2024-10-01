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

package com.kotlinpermissions.request.runtime

import android.app.Activity
import com.kotlinpermissions.PermissionStatus
import com.kotlinpermissions.extension.checkRuntimePermissionsStatus
import com.kotlinpermissions.request.BasePermissionRequest

/**
 * Implementation of [BasePermissionRequest] that checks the permissions since Android M.
 * The checks on the permissions are delegated to the [RuntimePermissionHandler] provided
 * to this request.
 *
 * @param activity the [Activity] used to check the permissions status.
 * @param permissions the set of permissions that must be checked.
 * @param handler the [RuntimePermissionHandler] which all checks on permissions are delegated to.
 */
public class RuntimePermissionRequest(
    private val activity: Activity,
    private val permissions: Array<out String>,
    private val handler: RuntimePermissionHandler
) : BasePermissionRequest(), RuntimePermissionHandler.Listener {
    init {
        handler.attachListener(permissions, this)
    }

    override fun checkStatus(): List<PermissionStatus> = activity.checkRuntimePermissionsStatus(permissions.toList())

    override fun send() {
        // The RuntimePermissionHandler will handle the request.
        handler.handleRuntimePermissions(permissions)
    }

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        // Using .iterator() to avoid shadowing with java.lang.Iterable#forEach.
        listeners.iterator().forEach { it.onPermissionsResult(result) }
    }
}
