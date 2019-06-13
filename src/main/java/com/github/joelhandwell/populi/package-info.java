@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = LocalDateXmlAdapter.class, type = LocalDate.class),
        @XmlJavaTypeAdapter(value = YearXmlAdapter.class, type = Year.class)
})
package com.github.joelhandwell.populi;

import io.github.threetenjaxb.core.LocalDateXmlAdapter;
import io.github.threetenjaxb.core.YearXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
import java.time.Year;
