package com.github.joelhandwell.populi

import org.javamoney.moneta.Money
import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.money.MonetaryAmount
import javax.xml.bind.annotation.adapters.XmlAdapter

val spaceDelimitedLocalDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
val clockLocalDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:ma") //Oct 21, 2017 5:11pm
val localTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")
val usaDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy") //Dec 31, 2017
val transcriptFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM. d yyyy") //Jan. 5 1985

class LocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {

    @Throws(Exception::class)
    override fun marshal(value: LocalDateTime): String =
        value.format(DateTimeFormatter.ISO_DATE_TIME)

    @Throws(Exception::class)
    override fun unmarshal(s: String): LocalDateTime = when {
        s.contains('T') -> LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME)
        s.contains('m') -> LocalDateTime.parse(s.replace("am", "AM").replace("pm", "PM"), clockLocalDateTimeFormatter)
        else -> LocalDateTime.parse(s, spaceDelimitedLocalDateTimeFormatter)
    }
}

class UsaLocalDateAdapter : XmlAdapter<String, LocalDate>() {
    override fun marshal(value: LocalDate): String = value.format(DateTimeFormatter.ISO_DATE)

    override fun unmarshal(s: String): LocalDate = when {
        s.contains(',') -> LocalDate.parse(s, usaDateFormatter)
        s.contains('.') -> LocalDate.parse(s, transcriptFormatter)
        else -> LocalDate.parse(s)
    }
}

class ClockLocalTimeAdapter : XmlAdapter<String, LocalTime>() {

    @Throws(Exception::class)
    override fun marshal(value: LocalTime): String = value.format(localTimeFormatter).toLowerCase()

    @Throws(Exception::class)
    override fun unmarshal(s: String): LocalTime = LocalTime.parse(s.toUpperCase(), localTimeFormatter)
}

class DayOfWeekAdapter : XmlAdapter<String, DayOfWeek>() {

    @Throws(Exception::class)
    override fun marshal(value: DayOfWeek): String = value.toString().substring(0..1)

    @Throws(Exception::class)
    override fun unmarshal(s: String): DayOfWeek = DayOfWeek.values().first { it.toString().startsWith(s) }
}

class MonetaryAmountAdapter : XmlAdapter<String, MonetaryAmount>() {

    @Throws(Exception::class)
    override fun marshal(value: MonetaryAmount): String =
        NumberFormat.getCurrencyInstance().format(value.number).replace("$", "")

    @Throws(Exception::class)
    override fun unmarshal(s: String): MonetaryAmount = Money.parse("USD $s")
}