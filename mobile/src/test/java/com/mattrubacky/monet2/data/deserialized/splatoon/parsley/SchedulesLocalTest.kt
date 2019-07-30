package com.mattrubacky.monet2.data.deserialized.splatoon.parsley

import com.mattrubacky.monet2.backend.api.splatnet.SplatnetParsley
import com.mattrubacky.monet2.data.rooms.SplatnetDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock

class SchedulesLocalTest {

    @Mock
    private lateinit var mockDb: SplatnetDatabase

    @Mock
    private lateinit var mockSplatnet: SplatnetParsley

    @Before
    fun setUp() {
        when(mockDb.timePeriodDao.)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getFromSource() {
    }

    @Test
    fun checkStore() {
    }

    @Test
    fun getLiveData() {
    }
}