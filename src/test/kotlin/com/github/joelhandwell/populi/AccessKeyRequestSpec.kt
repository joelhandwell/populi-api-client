package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.StringWriter
import javax.xml.bind.JAXB
import kotlin.test.assertEquals

object AccessKeyRequestSpec : Spek({
    describe("AccessKeyResponse"){

        it("marshal AccessKeyResponse"){
            val accessKeyResponse = AccessKeyResponse("AAABBB", AccountId(1111), "PERSON")
            val sw = StringWriter()
            JAXB.marshal(accessKeyResponse, sw)
            val xml = """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <response>
                <access_key>AAABBB</access_key>
                <account_id>
                    <id>1111</id>
                </account_id>
                <account_type>PERSON</account_type>
            </response>
            """.trimIndent()
            assertEquals(xml.trim(), sw.toString().trim())
        }

        it("unmarshal AccessKeyResponse"){
            val xml = """
            <response>
               <access_key>
                   32AD2d3432...REALLYLONGSTRINGOFCHARACTERS...232as72asdf3
               </access_key>
               <account_id>
                   <id>2222</id>
               </account_id>
               <account_type>PERSON</account_type>
            </response>
            """.trimIndent()
            val r = JAXB.unmarshal(xml.reader(), AccessKeyResponse::class.java)
            assertEquals("32AD2d3432...REALLYLONGSTRINGOFCHARACTERS...232as72asdf3", r.access_key.trim())
            assertEquals(2222, r.account_id.id)
            assertEquals("PERSON", r.account_type)
        }
    }
})
