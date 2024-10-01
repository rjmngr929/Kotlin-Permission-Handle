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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kotlinpermissions.request.runtime.ResultLauncherRuntimePermissionHandlerProvider.Companion.FRAGMENT_TAG

/**
 * Implementation of [RuntimePermissionHandler] that uses a [ResultLauncherRuntimePermissionHandler]
 * to manage the permissions.
 *
 * The [Fragment] will be added synchronously to the [Activity] with the tag [FRAGMENT_TAG].
 * Only one [Fragment] in the same [Activity] will be instantiated to avoid
 * multiple instances.
 *
 * @property manager the [FragmentManager] of the [Activity].
 */
internal class ResultLauncherRuntimePermissionHandlerProvider(private val manager: FragmentManager) :
    RuntimePermissionHandlerProvider {
    @RequiresApi(23)
    override fun provideHandler(): RuntimePermissionHandler {
        // Obtain the current Fragment if possible, otherwise create it.
        var fragment = manager.findFragmentByTag(FRAGMENT_TAG) as? RuntimePermissionHandler
        if (fragment == null) {
            // Create the Fragment delegated to handle permissions.
            fragment = ResultLauncherRuntimePermissionHandler()
            manager.beginTransaction()
                .add(fragment, FRAGMENT_TAG)
                .commitAllowingStateLoss()
        }
        return fragment
    }

    companion object {
        private const val FRAGMENT_TAG = "KPermissionsFragment"
    }
}
