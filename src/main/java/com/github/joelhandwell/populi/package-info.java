@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class, type = LocalDate.class),
        @XmlJavaTypeAdapter(value = YearXmlAdapter.class, type = Year.class),
        @XmlJavaTypeAdapter(value = MonetaryAmountAdapter.class, type = MonetaryAmount.class)
})
package com.github.joelhandwell.populi;

import io.github.threetenjaxb.core.LocalDateXmlAdapter;
import io.github.threetenjaxb.core.YearXmlAdapter;

import javax.money.MonetaryAmount;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.time.Year;
