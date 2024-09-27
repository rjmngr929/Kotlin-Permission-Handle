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

import androidx.fragment.app.Fragment
import com.kotlinpermissions.request.runtime.RuntimePermissionHandler

/**
 * Provides a [RuntimePermissionHandler] to manage runtime permissions since Android M.
 *
 * The [RuntimePermissionHandler] must be available instantly so, for example, if
 * it's a [Fragment], it must be committed synchronously.
 */
public fun interface RuntimePermissionHandlerProvider {

    /**
     * Provides an instance of [RuntimePermissionHandler] used to check permissions since Android M.
     *
     * @return instance of [RuntimePermissionHandler].
     */
    public fun provideHandler(): RuntimePermissionHandler
}
