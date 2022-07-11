package com.thousandmiles.model.profile.user

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun User.parseDOB(): LocalDate? {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    return if (dateOfBirth.isNotBlank())
        LocalDate.parse(dateOfBirth, dateFormatter)
    else null
}

fun toDateString(date: LocalDate): String {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    return date.format(dateFormatter)
}