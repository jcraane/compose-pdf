package dev.jamiecraane.vibe.compose.pdf.examples

import dev.jamiecraane.vibe.compose.pdf.ComposePdf
import dev.jamiecraane.vibe.compose.pdf.components.Color
import dev.jamiecraane.vibe.compose.pdf.components.FontWeight
import dev.jamiecraane.vibe.compose.pdf.components.Text
import dev.jamiecraane.vibe.compose.pdf.components.text
import dev.jamiecraane.vibe.compose.pdf.layout.Position
import dev.jamiecraane.vibe.compose.pdf.units.Point
import java.io.File

/**
 * A simple example that demonstrates how to use the Compose PDF API to generate a PDF with text.
 */
object SimpleExample {
    /**
     * Generates a simple PDF with text and saves it to the specified file.
     *
     * @param outputFile The file to save the PDF to
     */
    fun generateSimplePdf(outputFile: File) {
        ComposePdf.generatePdf(outputFile) {
            // Add a title
            add(
                text(
                    text = "Hello, Compose PDF!",
                    fontSize = 24f,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                ),
                Position.fromPoints(72f, 72f) // 1 inch from the top-left corner
            )

            // Add a subtitle
            add(
                text(
                    text = "A simple example of generating PDFs with Compose-like syntax",
                    fontSize = 14f,
                    color = Color(100, 100, 100) // Gray color
                ),
                Position.fromPoints(72f, 120f) // Below the title
            )

            // Add some body text
            add(
                text(
                    text = "This is a simple example of how to use the Compose PDF API to generate PDFs.",
                    fontSize = 12f
                ),
                Position.fromPoints(72f, 160f) // Below the subtitle
            )

            // Add more body text
            add(
                text(
                    text = "The API is designed to be similar to Jetpack Compose, making it easy to use for developers familiar with Compose.",
                    fontSize = 12f
                ),
                Position.fromPoints(72f, 180f) // Below the previous text
            )
        }
    }
}

/**
 * Main function to run the example.
 */
fun main() {
    val outputFile = File("simple-example.pdf")
    SimpleExample.generateSimplePdf(outputFile)
    println("PDF generated at: ${outputFile.absolutePath}")
}
