package com.marekvogt.randomcity.domain.location.repository

import com.marekvogt.randomcity.domain.location.model.Location

interface LocationRepository {

    suspend fun findLocationBy(name: String): Location?
}