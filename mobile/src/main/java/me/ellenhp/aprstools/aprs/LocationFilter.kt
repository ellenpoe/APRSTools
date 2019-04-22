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

package me.ellenhp.aprstools.aprs

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.util.*

class LocationFilter(location: LatLng, private val radiusKilometers: Double) : Serializable {

    private val latitude = location.latitude
    private val longitude = location.longitude

    internal val filterCommand: String
        get() {
            val filterTemplate = "#filter r/%f/%f/%f\r\n"
            return String.format(Locale.US, filterTemplate, latitude, longitude, radiusKilometers)
        }

}
