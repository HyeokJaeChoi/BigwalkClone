package com.example.bigwalkclone.util

import java.time.LocalDateTime

object DateTimeUtil {
    fun isCampaignActive(endDate: String): Boolean {
        val endDateTime = LocalDateTime.parse(endDate)
        val now = LocalDateTime.now()

        return now.year <= endDateTime.year
                && now.monthValue <= endDateTime.monthValue
                && now.dayOfMonth <= endDateTime.dayOfMonth
    }
}