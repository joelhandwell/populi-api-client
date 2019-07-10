package com.github.joelhandwell.populi.jaxb

import com.github.joelhandwell.populi.ApplicationAnswer
import org.javamoney.moneta.Money
import org.w3c.dom.Element
import java.io.StringWriter
import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.money.MonetaryAmount
import javax.xml.bind.JAXB
import javax.xml.bind.JAXBContext
import javax.xml.bind.annotation.adapters.XmlAdapter
import okhttp3.ResponseBody
import org.slf4j.LoggerFactory
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.StringReader
import java.lang.reflect.Type
import javax.xml.bind.annotation.XmlRootElement

internal class PopuliResponseConverter<T>(val type: Class<T>) : Converter<ResponseBody, T> {
    val logger = LoggerFactory.getLogger(PopuliResponseConverter::class.java)

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val bodyString = value.string()

        val header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><error><code>"
        if (bodyString.startsWith(header)) {
            throw RuntimeException(bodyString)
        } else {
            logger.debug(bodyString)
        }

        val sanitizedBodyString = sanitizeXml(bodyString)

        return JAXB.unmarshal(StringReader(sanitizedBodyString), type)
    }
}

class PopuliResponseConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return if (type is Class<*> && type.isAnnotationPresent(XmlRootElement::class.java)) {
            PopuliResponseConverter(type)
        } else null
    }

    companion object {

        fun create(): PopuliResponseConverterFactory {
            return PopuliResponseConverterFactory()
        }
    }
}

private val spaceDelimitedLocalDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
private val clockLocalDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:ma") //Oct 21, 2017 5:11pm
private val localTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")
private val usaDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy") //Dec 31, 2017
private val transcriptFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM. d yyyy") //Jan. 5 1985

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

class ApplicationAnswerAdapter : XmlAdapter<Any, ApplicationAnswer>() {

    override fun marshal(v: ApplicationAnswer): Any {
        return StringWriter().apply { JAXB.marshal(v, this) }.toString()
    }

    override fun unmarshal(v: Any): ApplicationAnswer {
        val context: JAXBContext = JAXBContext.newInstance(ApplicationAnswer::class.java)
        val unmarshaller = context.createUnmarshaller()

        val e = v as Element

        return when {
            e.childNodes.length == 1 -> ApplicationAnswer(ssn = e.firstChild.textContent)
            else -> unmarshaller.unmarshal(e) as ApplicationAnswer
        }
    }
}

fun sanitizeXml(xml: String): String {
    return xml.replace("&([^;&]+(?!(?:\\w|;)))".toRegex(), "&amp;$1")
        .replace("<\n( )*!\\[CDATA\\[".toRegex(), "<![CDATA[")
        .replace("\\]\\]>\n( )*</".toRegex(), "]]></")
}
