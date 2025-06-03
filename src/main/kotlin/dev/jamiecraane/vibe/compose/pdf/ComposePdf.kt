package dev.jamiecraane.vibe.compose.pdf

import dev.jamiecraane.vibe.compose.pdf.components.Composable
import dev.jamiecraane.vibe.compose.pdf.components.Color
import dev.jamiecraane.vibe.compose.pdf.components.ColumnScope
import dev.jamiecraane.vibe.compose.pdf.components.FontWeight
import dev.jamiecraane.vibe.compose.pdf.components.Modifier
import dev.jamiecraane.vibe.compose.pdf.components.RowScope
import dev.jamiecraane.vibe.compose.pdf.components.TextImpl
import dev.jamiecraane.vibe.compose.pdf.layout.BasicLayoutScope
import dev.jamiecraane.vibe.compose.pdf.layout.BasicMeasureScope
import dev.jamiecraane.vibe.compose.pdf.layout.Constraints
import dev.jamiecraane.vibe.compose.pdf.layout.Measurable
import dev.jamiecraane.vibe.compose.pdf.layout.Position
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.awt.Color as AwtColor
import java.io.File
import java.io.OutputStream

/**
 * Main entry point for generating PDFs using Compose-like syntax.
 */
object ComposePdf {
    /**
     * Generates a PDF document with the given content and writes it to the specified file.
     *
     * @param file The file to write the PDF to
     * @param content A lambda that defines the content of the PDF using Compose-like syntax
     */
    fun generatePdf(file: File, content: PdfScope.() -> Unit) {
        val document = PdfDocument.create(file)
        try {
            val scope = PdfScope(document)
            scope.content()
            scope.renderToPdf()
        } finally {
            document.close()
        }
    }

    /**
     * Generates a PDF document with the given content and writes it to the specified output stream.
     *
     * @param outputStream The output stream to write the PDF to
     * @param content A lambda that defines the content of the PDF using Compose-like syntax
     */
    fun generatePdf(outputStream: OutputStream, content: PdfScope.() -> Unit) {
        val document = PdfDocument.create(outputStream)
        try {
            val scope = PdfScope(document)
            scope.content()
            scope.renderToPdf()
        } finally {
            document.close()
        }
    }
}

/**
 * Scope for defining PDF content using Compose-like syntax.
 */
class PdfScope(private val document: PdfDocument) {
    private val measureScope = BasicMeasureScope()
    private val layoutScope = BasicLayoutScope()
    private val rootComponents = mutableListOf<Pair<Measurable, Position>>()

    /**
     * Adds a component to the PDF document.
     *
     * @param measurable The component to add
     * @param position The position to place the component at
     */
    fun add(measurable: Measurable, position: Position = Position.ZERO) {
        rootComponents.add(measurable to position)
    }

    /**
     * Adds a Column component to the PDF document.
     *
     * @param modifier The modifier to be applied to the layout
     * @param content The content of the column
     */
    @Composable
    fun Column(
        modifier: Modifier = Modifier.None,
        content: ColumnScope.() -> Unit
    ) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Column(modifier, content))
    }

    /**
     * Adds a Row component to the PDF document.
     *
     * @param modifier The modifier to be applied to the layout
     * @param content The content of the row
     */
    @Composable
    fun Row(
        modifier: Modifier = Modifier.None,
        content: RowScope.() -> Unit
    ) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Row(modifier, content))
    }

    /**
     * Adds a Text component to the PDF document.
     *
     * @param text The text to display
     * @param fontSize The font size in points
     * @param fontFamily The font family to use
     * @param fontWeight The font weight to use
     * @param color The text color
     */
    @Composable
    fun Text(
        text: String,
        fontSize: Float = 12f,
        fontFamily: String = "Helvetica",
        fontWeight: FontWeight = FontWeight.Bold,
        color: Color = Color.Black
    ) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Text(text, fontSize, fontFamily, fontWeight, color))
    }

    /**
     * Adds a Spacer component to the PDF document.
     *
     * @param modifier The modifier to be applied to the spacer
     */
    @Composable
    fun Spacer(modifier: Modifier = Modifier.None) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Spacer(modifier))
    }

    /**
     * Renders all components to the PDF.
     */
    internal fun renderToPdf() {
        val pdfBoxDoc = document.getPdfBoxDocument()
        val page = pdfBoxDoc.getPage(0) // Get the first page

        // Process all components
        val componentsToRender = mutableListOf<Pair<Measurable, Position>>()

        // Add root components
        componentsToRender.addAll(rootComponents)

        // Process layout components recursively
        val processedComponents = mutableListOf<Pair<Measurable, Position>>()
        while (componentsToRender.isNotEmpty()) {
            val (component, position) = componentsToRender.removeAt(0)
            processedComponents.add(component to position)

            when (component) {
                is dev.jamiecraane.vibe.compose.pdf.components.Column -> {
                    componentsToRender.addAll(component.placeChildren(position))
                }
                is dev.jamiecraane.vibe.compose.pdf.components.Row -> {
                    componentsToRender.addAll(component.placeChildren(position))
                }
            }
        }

        // Render all components
        processedComponents.forEach { (component, position) ->
            renderComponent(component, position, page, pdfBoxDoc)
        }
    }

    /**
     * Renders a single component to the PDF.
     */
    private fun renderComponent(measurable: Measurable, position: Position, page: org.apache.pdfbox.pdmodel.PDPage, pdfBoxDoc: org.apache.pdfbox.pdmodel.PDDocument) {
        val size = measureScope.measure(measurable, Constraints.DEFAULT)
        layoutScope.place(measurable, position)

        // Create a content stream for drawing on the page
        PDPageContentStream(pdfBoxDoc, page, PDPageContentStream.AppendMode.APPEND, true, true).use { contentStream ->
            // Handle different types of components
            when (measurable) {
                is dev.jamiecraane.vibe.compose.pdf.components.TextImpl -> {
                    // Convert our color to AWT color
                    val awtColor = AwtColor(
                        measurable.color.red,
                        measurable.color.green,
                        measurable.color.blue,
                        measurable.color.alpha
                    )

                    // Set text properties
                    contentStream.setNonStrokingColor(awtColor)

                    // Select font based on weight
                    val font = when (measurable.fontWeight) {
                        dev.jamiecraane.vibe.compose.pdf.components.FontWeight.Bold -> PDType1Font.HELVETICA_BOLD
                        dev.jamiecraane.vibe.compose.pdf.components.FontWeight.Light -> PDType1Font.HELVETICA
                        dev.jamiecraane.vibe.compose.pdf.components.FontWeight.Medium -> PDType1Font.HELVETICA
                        else -> PDType1Font.HELVETICA
                    }

                    contentStream.beginText()
                    contentStream.setFont(font, measurable.fontSize)

                    // In PDF, the origin is at the bottom-left corner, but we're using top-left
                    // So we need to adjust the y-coordinate
                    val pageHeight = page.mediaBox.height
                    val adjustedY = pageHeight - position.yPt

                    contentStream.newLineAtOffset(position.xPt, adjustedY)
                    contentStream.showText(measurable.text)
                    contentStream.endText()
                }
                // Spacer and other layout components don't need to be rendered
                is dev.jamiecraane.vibe.compose.pdf.components.SpacerImpl,
                is dev.jamiecraane.vibe.compose.pdf.components.Column,
                is dev.jamiecraane.vibe.compose.pdf.components.Row -> {
                    // No rendering needed
                }
                // Add support for other component types as needed
            }
        }
    }

    /**
     * Gets the underlying PDF document.
     * This is provided for advanced use cases where direct access to the document is needed.
     */
    fun getDocument(): PdfDocument = document
}
