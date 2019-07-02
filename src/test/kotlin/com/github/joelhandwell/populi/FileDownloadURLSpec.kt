package com.github.joelhandwell.populi

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val fileDownloadURL = FileDownloadURL("https://s3.amazonaws.com/files.populi.co/c000ad3cb61672bf32F10aec4d03Dd7f7.doc?AWSAccessKeyId=BKIAI0OM45DGDLXQFYDA&Expires=1308869410&Signature=IQdxEfIE4tYh6lxpimvKo8W16tM%3D")

object FileDownloadURLSpec : Spek({

    describe("FileDownloadURL") {

        it("marshal to xml") { assertMarshals(getFileDownloadURLXml, fileDownloadURL) }

        it("unmarshal from xml") { assertUnmarshals(fileDownloadURL, getFileDownloadURLXml) }
    }
})

const val getFileDownloadURLXml = """
<response>
    <url>https://s3.amazonaws.com/files.populi.co/c000ad3cb61672bf32F10aec4d03Dd7f7.doc?AWSAccessKeyId=BKIAI0OM45DGDLXQFYDA&Expires=1308869410&Signature=IQdxEfIE4tYh6lxpimvKo8W16tM%3D</url>
</response>"""
