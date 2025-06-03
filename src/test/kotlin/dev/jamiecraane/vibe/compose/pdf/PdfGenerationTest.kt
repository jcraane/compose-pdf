package dev.jamiecraane.vibe.compose.pdf

import dev.jamiecraane.vibe.compose.pdf.components.Color
import dev.jamiecraane.vibe.compose.pdf.components.FontWeight
import dev.jamiecraane.vibe.compose.pdf.components.text
import dev.jamiecraane.vibe.compose.pdf.layout.Position
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path
import kotlin.test.assertTrue

class PdfGenerationTest {
    @Test
    fun testPdfGeneration(@TempDir tempDir: Path) {
        // Create a temporary file for the PDF
        val outputFile = tempDir.resolve("test-output.pdf").toFile()

        // Generate the PDF
        ComposePdf.generatePdf(outputFile) {
            // Add a simple text element
            add(
                text(
                    text = "Hello, PDF!",
                    fontSize = 24f,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                ),
                Position.fromPoints(72f, 72f) // 1 inch from the top-left corner
            )
        }

        // Verify that the file exists and is not empty
        println("[DEBUG_LOG] PDF file path: ${outputFile.absolutePath}")
        println("[DEBUG_LOG] PDF file exists: ${outputFile.exists()}")
        println("[DEBUG_LOG] PDF file size: ${outputFile.length()} bytes")

        assertTrue(outputFile.exists(), "PDF file should exist")
        assertTrue(outputFile.length() > 0, "PDF file should not be empty")
    }
}
