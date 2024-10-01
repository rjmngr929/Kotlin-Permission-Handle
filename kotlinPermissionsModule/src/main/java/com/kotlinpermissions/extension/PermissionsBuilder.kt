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

package com.kotlinpermissions.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kotlinpermissions.builder.CompatPermissionRequestBuilder
import com.kotlinpermissions.builder.PermissionRequestBuilder
import com.kotlinpermissions.request.runtime.ResultLauncherRuntimePermissionHandlerProvider

/**
 * Creates the default [PermissionRequestBuilder] using the context of the [Activity].
 * The builder will use the default configurations and will be provided with
 * the set of [firstPermission] + [otherPermissions] attached to it.
 *
 * @param firstPermission the first permission which should be requested.
 * @param otherPermissions the other permissions that must be requested, if the request
 * should handle more than one permission.
 * @return new instance of the default [PermissionRequestBuilder].
 */
public fun FragmentActivity.permissionsBuilder(
    firstPermission: String,
    vararg otherPermissions: String
): PermissionRequestBuilder {
    val handler = ResultLauncherRuntimePermissionHandlerProvider(supportFragmentManager)
    // Creates the builder.
    return CompatPermissionRequestBuilder(this)
        .permissions(firstPermission, *otherPermissions)
        .runtimeHandlerProvider(handler)
}

/**
 * Creates the default [PermissionRequestBuilder] using the context of the [Activity].
 * The builder will use the default configurations and will be provided with
 * the set of [permissions] attached to it.
 *
 * @param permissions the permissions which should be requested.
 * @return new instance of the default [PermissionRequestBuilder].
 */
public fun FragmentActivity.permissionsBuilder(permissions: List<String>): PermissionRequestBuilder {
    val handler = ResultLauncherRuntimePermissionHandlerProvider(supportFragmentManager)
    // Creates the builder.
    return CompatPermissionRequestBuilder(this)
        .permissions(permissions)
        .runtimeHandlerProvider(handler)
}

/**
 * Creates the default [PermissionRequestBuilder] using the context of the [Activity] at which
 * this [Fragment] is attached.
 * The builder will use the default configurations and will be provided with
 * the set of [firstPermission] + [otherPermissions] attached to it.
 *
 * @param firstPermission the first permission which should be requested.
 * @param otherPermissions the other permissions that must be requested, if the request
 * should handle more than one permission.
 * @return new instance of the default [PermissionRequestBuilder].
 * @throws NullPointerException if the [Fragment] is not attached to an [Activity].
 */
public fun Fragment.permissionsBuilder(
    firstPermission: String,
    vararg otherPermissions: String
): PermissionRequestBuilder = requireActivity().permissionsBuilder(firstPermission, *otherPermissions)

/**
 * Creates the default [PermissionRequestBuilder] using the context of the [Activity] at which
 * this [Fragment] is attached.
 * The builder will use the default configurations and will be provided with
 * the set of [permissions] attached to it.
 *
 * @param permissions the permissions which should be requested.
 * @return new instance of the default [PermissionRequestBuilder].
 * @throws NullPointerException if the [Fragment] is not attached to an [Activity].
 */
public fun Fragment.permissionsBuilder(
    permissions: List<String>,
): PermissionRequestBuilder = requireActivity().permissionsBuilder(permissions)
