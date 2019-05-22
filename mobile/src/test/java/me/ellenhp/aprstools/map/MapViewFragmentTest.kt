/*
 * Copyright (c) 2019 Ellen Poe
 *
 * This file is part of APRSTools.
 *
 * APRSTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * APRSTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with APRSTools.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.ellenhp.aprstools.map

import androidx.fragment.app.testing.launchFragmentInContainer
import com.google.android.gms.location.FusedLocationProviderClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [21, 23, 24, 26, 28])
class MapViewFragmentTest {

    @Mock
    lateinit var fusedLocationProvider: FusedLocationProviderClient

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun startFragment_noCrash() {
        val scenario = launchFragmentInContainer(instantiate = {
            val fragment = MapViewFragment()
            fragment.setupMocksForTesting {
                fragment.fusedLocationClient = fusedLocationProvider
            }
            fragment
        })
        scenario.onFragment {
        }
    }
}