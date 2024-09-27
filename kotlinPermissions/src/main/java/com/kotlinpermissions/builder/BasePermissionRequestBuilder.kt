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

import androidx.annotation.VisibleForTesting
import com.kotlinpermissions.request.PermissionRequest
import com.kotlinpermissions.request.runtime.RuntimePermissionHandlerProvider

/**
 * Base [PermissionRequestBuilder] that specifies the common configurations.
 *
 * This builder specifies also the default configurations and throws the correct
 * exceptions when the method [build] is invoked with an invalid configuration.
 * When the method [createRequest] is called, the configuration can't change anymore,
 * all the defaults are assigned and it can be considered valid.
 */
public abstract class BasePermissionRequestBuilder : PermissionRequestBuilder {
    private var permissions: Array<out String>? = null
    @VisibleForTesting internal var runtimeHandlerProvider: RuntimePermissionHandlerProvider? = null
        private set

    override fun permissions(
        firstPermission: String,
        vararg otherPermissions: String
    ): PermissionRequestBuilder = apply {
        this.permissions = Array(otherPermissions.size + 1) { index ->
            if (index == 0) {
                firstPermission
            } else {
                otherPermissions[index - 1]
            }
        }
    }

    override fun permissions(permissions: List<String>): PermissionRequestBuilder = apply {
        this.permissions = permissions.toTypedArray()
    }

    override fun runtimeHandlerProvider(runtimeHandlerProvider: RuntimePermissionHandlerProvider): PermissionRequestBuilder =
        apply {
            this.runtimeHandlerProvider = runtimeHandlerProvider
        }

    override fun build(): PermissionRequest {
        val permissions = permissions
            ?: throw IllegalArgumentException("The permissions names are necessary.")

        // Get the runtime handler.
        val runtimeHandlerProvider = runtimeHandlerProvider
            ?: throw IllegalArgumentException("A runtime handler is necessary to request the permissions.")

        return createRequest(permissions, runtimeHandlerProvider)
    }

    /**
     * Create the [PermissionRequest] after the common checks are executed, the defaults are
     * assigned and this configuration can be considered valid.
     *
     * @param permissions set of permissions that must be requested.
     * @param runtimeHandlerProvider instance of [RuntimePermissionHandlerProvider] specified in this configuration.
     * @return instance of [PermissionRequest] that uses this configuration.
     */
    protected abstract fun createRequest(
        permissions: Array<out String>,
        runtimeHandlerProvider: RuntimePermissionHandlerProvider
    ): PermissionRequest
}
