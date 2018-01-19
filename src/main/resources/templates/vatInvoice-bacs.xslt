<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:date="http://exslt.org/dates-and-times"
                extension-element-prefixes="date">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/invoiceInfo">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait" page-height="29.7cm" page-width="21.0cm" margin="2cm">
                    <fo:region-body margin="120pt 0 0 0"/>
                    <fo:region-before/>
                    <fo:region-after/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4-portrait">
                <fo:static-content flow-name="xsl-region-before"
                                   font-size="10pt">
                    <fo:block>Sedex Information Exchange Ltd</fo:block>
                    <fo:block>24 Southwark Bridge Road</fo:block>
                    <fo:block>London</fo:block>
                    <fo:block>SE1 9HF</fo:block>
                    <fo:block>Tel: +44 (0)20 7902 2320</fo:block>
                    <fo:block space-before="12pt">VAT Registration No: GB 266455185</fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after"
                                   font-size="10pt">
                    <fo:block text-align="center"
                              font-style="italic">VAT is only charged on UK Companies</fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body"
                         font-size="10pt">
                    <fo:block  font-weight="bold"
                               font-size="14pt"
                               text-align="center"
                               space-after="30pt">VAT INVOICE</fo:block>
                    <fo:table width="100%">
                        <fo:table-column column-width="50%"></fo:table-column>
                        <fo:table-column column-width="50%"></fo:table-column>
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell border="1pt solid black"
                                               padding="6pt">
                                    <fo:block>
                                        <xsl:value-of select="organisationName"/>
                                    </fo:block>
                                    <fo:block>
                                        <xsl:value-of select="address/addressLine1"/>
                                    </fo:block>
                                    <fo:block>
                                        <xsl:value-of select="address/addressLine2"/>
                                    </fo:block>
                                    <fo:block>
                                        <xsl:value-of select="address/postCode"/>
                                    </fo:block>
                                    <fo:block>
                                        <xsl:value-of select="address/city"/>
                                    </fo:block>
                                    <fo:block>
                                        <xsl:value-of select="address/countryCode"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="36pt 6pt"
                                               border="1pt solid black">
                                    <fo:table>
                                        <fo:table-column />
                                        <fo:table-column />

                                        <fo:table-body>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block text-align="left">Invoice Number</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell>
                                                    <fo:block text-align="right">
                                                        <xsl:value-of select="details/invoiceNumber"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block text-align="left">Invoice / TAX Date</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell>
                                                    <fo:block text-align="right">
                                                        <xsl:variable name="invoiceDate" select="details/invoiceDate"/>
                                                        <xsl:value-of select="date:format-date($invoiceDate, 'dd/MM/yyyy')"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </fo:table-body>
                                    </fo:table>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <fo:table border="2pt solid black"
                              width="100%"
                              margin="24pt 0 24pt 0">
                        <fo:table-column column-width="50%"
                                         border="1pt solid black"></fo:table-column>
                        <fo:table-column column-width="25%"
                                         border="1pt solid black"></fo:table-column>
                        <fo:table-column column-width="25%"
                                         border="1pt solid black"></fo:table-column>
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="center">Details</fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="center">Quantity</fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="center">Net Amount</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        Subscription for <xsl:value-of select="subscription/sites"/> sites for <xsl:value-of select="subscription/numberOfYears"/> years
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="subscription/numberOfYears"/> X <xsl:value-of select="subscription/sites"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="amount/amount"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        Administration Fee
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="administrationFee/amount"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="right">
                                        Total Net Amount
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="netTotal/currencyCode"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="netTotal/amount"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="right">
                                        VAT
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="vat/currencyCode"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="vat/amount"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="right">
                                        Invoice Total
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="total/currencyCode"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="total/amount"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block text-align="right">
                                        Total Amount Received
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="totalAmountReceived/currencyCode"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2pt"
                                               border="1pt solid black">
                                    <fo:block>
                                        <xsl:value-of select="totalAmountReceived/amount"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>

                    <fo:block border="1pt solid black"
                              padding="6pt"
                              margin="0 0 0 2pt">
                        <fo:inline> Payment received and completed by BACS.</fo:inline>

                        <fo:block space-before="12pt" text-decoration="underline">www.sedexglobal.com</fo:block>
                        <fo:block>Email: <fo:inline text-decoration="underline">helpdesk@sedexglobal.com</fo:inline></fo:block>
                        <fo:block>Registered in England and Wales No.5015443</fo:block>
                        <fo:block>24 Southwark Bridge Road, London SE1 9HF</fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
