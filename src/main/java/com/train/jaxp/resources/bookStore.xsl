<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:b="http://joey.org/bookStore"
	xmlns:a="http://japan.org/book/audlt">
	<xsl:output method="html" version="1.0" encoding="UTF-8"
		indent="yes"></xsl:output>
	<xsl:template match="/">
		<html>
			<body>
				<h2>Book
					Store&lt;&lt;
					<xsl:value-of select="/b:bookStore/@name"></xsl:value-of>&gt;&gt;
				</h2>
				<div>
					There are
					<xsl:value-of select="count(/b:bookStore/b:books/b:book)"></xsl:value-of>
					books.
				</div>
				<div>
					Keeper of this store is
					<xsl:value-of select="/b:bookStore/b:keeper/b:name"></xsl:value-of>
				</div>
				<xsl:for-each select="/b:bookStore/b:books/b:book">
					<div>
						Book:
						<span>
							title=
							<xsl:value-of select="b:title"></xsl:value-of>
						</span>
						;
						<span>
							author=
							<xsl:value-of select="b:author"></xsl:value-of>
						</span>
						<xsl:if test="@a:color">
							<span style="color:yellow">
								H Book, require age
								<xsl:value-of select="a:age"></xsl:value-of>
							</span>
						</xsl:if>
					</div>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>