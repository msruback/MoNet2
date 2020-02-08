package com.mattrubacky.monet2.data.deserialized.splatoon.parsley.schedules

import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.backend.getCurrentTime
import com.mattrubacky.monet2.data.deserialized_entities.TimePeriod
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.CurrentSplatfest
import com.mattrubacky.monet2.data.deserialized.splatoon.parsley.Schedules
import com.mattrubacky.monet2.getJSON
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test

class SchedulesStoreTests {

    private val gson = Gson()

    private val mockDb = mockk<SplatnetDatabase>(relaxed = true)

    private val timePeriod = TimePeriod()

    @Before
    fun setUp() {
        every { mockDb.timePeriodDao.selectOld(0) } returns ArrayList<TimePeriod>()
        every { mockDb.timePeriodDao.selectOld(1) } returns ArrayList(listOf(timePeriod))
        mockkStatic("com.mattrubacky.monet2.backend.ExtensionsKt")
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkStorePositive() {
        every { getCurrentTime() } returns 1
        val schedule = Schedules()
        runBlocking {
            schedule.checkStore(mockDb)
            assertThat(schedule.needsUpdate).isTrue()
        }
    }

    @Test
    fun checkStoreNegative(){
        every { getCurrentTime() } returns 0
        val schedule = Schedules()
        runBlocking {
            schedule.checkStore(mockDb)
            assertThat(schedule.needsUpdate).isFalse()
        }
    }

    @Test
    fun getLiveData() {
    }
}