package com.marekvogt.randomcity.data.location.repository

import android.content.Context
import android.location.Geocoder
import com.marekvogt.randomcity.domain.common.coroutine.Dispatchers
import com.marekvogt.randomcity.domain.location.model.Location
import com.marekvogt.randomcity.domain.location.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationGeocoderRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: Dispatchers,
    private val locale: Locale
) : LocationRepository {

    private val cache: MutableMap<String, Location> = mutableMapOf()

    override suspend fun findLocationBy(name: String): Location? = withContext(dispatchers.ioDispatcher) {
        suspendCancellableCoroutine { continuation ->
            val address = Geocoder(context, locale)
                .getFromLocationName(name, 1)
                .firstOrNull()

            continuation.resume(address?.run {
                Location(latitude, longitude).also { location ->
                    cache[name] = location
                }
            })
        }
    }
}