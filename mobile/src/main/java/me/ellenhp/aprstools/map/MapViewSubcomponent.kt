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

import dagger.Subcomponent
import dagger.android.AndroidInjector
import me.ellenhp.aprstools.MapFragmentScope
import me.ellenhp.aprstools.map.google.GoogleMapWrapperModule

@Subcomponent(modules = [MapViewFragmentModule::class, GoogleMapWrapperModule::class])
@MapFragmentScope
interface MapViewSubcomponent : AndroidInjector<MapViewFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MapViewFragment>
}