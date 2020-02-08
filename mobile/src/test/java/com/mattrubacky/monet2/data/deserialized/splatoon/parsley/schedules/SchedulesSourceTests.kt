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
import retrofit2.Response

class SchedulesSourceTests {

    private val gson = Gson()

    private val mockSplatnet = mockk<SplatnetParsley>(relaxed = true)

    private val mockSchedulesSuccess = mockk<Response<Schedules>>()
    private val mockSchedulesFail = mockk<Response<Schedules>>(relaxed = true)

    private val timePeriod = TimePeriod()
    private val schedules = gson.fromJson(getJSON("api_schedules.json",this::class), Schedules::class.java)
    private val splatfest = CurrentSplatfest()

    @Before
    fun setUp() {
        every { mockSchedulesSuccess.isSuccessful} returns true
        every { mockSchedulesSuccess.body() } returns schedules

        every { mockSchedulesFail.isSuccessful} returns false
        mockkStatic("com.mattrubacky.monet2.backend.ExtensionsKt")
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getFromSourceSuccessNormal() {
        every { mockSplatnet.getSchedules().execute()} returns mockSchedulesSuccess
        val schedule = Schedules()
        runBlocking {
            schedule.getFromSource(mockSplatnet)
            assertThat(schedule.regular).containsExactlyElementsIn(schedules.regular)
            assertThat(schedule.ranked).containsExactlyElementsIn(schedules.ranked)
            assertThat(schedule.league).containsExactlyElementsIn(schedules.league)
            assertThat(schedule.splatfest).isEmpty()
        }
    }

    //Todo implement failure handling in sprigs
    @Test
    fun getFromSourceFail() {
        every { mockSplatnet.getSchedules().execute() } returns mockSchedulesFail
        every { mockSplatnet.getActiveSplatfests().execute() } returns mockk{
            every { isSuccessful } returns false
        }
        val schedule = Schedules()
        runBlocking {
            schedule.getFromSource(mockSplatnet)
        }
    }
}