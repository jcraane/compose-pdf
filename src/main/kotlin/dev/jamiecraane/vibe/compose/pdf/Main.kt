package dev.jamiecraane.vibe.compose.pdf

import dev.jamiecraane.vibe.compose.pdf.components.Color
import dev.jamiecraane.vibe.compose.pdf.components.FontWeight
import dev.jamiecraane.vibe.compose.pdf.components.Modifier
import dev.jamiecraane.vibe.compose.pdf.components.Padding
import dev.jamiecraane.vibe.compose.pdf.examples.SimpleExample
import java.io.File

fun main() {
    // Run the code from Main.kt using the new Compose-style syntax
    val outputFile = File("output.pdf")
    ComposePdf.generatePdf(outputFile) {
        Column(modifier = Modifier.None.padding(Padding(left = 36f, top = 36f, right = 36f, bottom = 36f))) {
            Text(
                text = "Hello PDF from Compose2!",
                fontSize = 24f,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.None.height(16f))

            Row {
                // In a real implementation, we would add an Image here
                Text("Description text: This PDF was created with Compose PDF")
            }

            // More composable content
            Spacer(modifier = Modifier.None.height(16f))

            Text(
                text = "This demonstrates the new Compose-style syntax2",
                fontSize = 14f,
                color = Color.Blue
            )
        }
    }
    println("PDF created successfully at: ${outputFile.absolutePath}")

    // Run the code from SimpleExample.kt
    val simpleExampleOutputFile = File("simple-example.pdf")
    SimpleExample.generateSimplePdf(simpleExampleOutputFile)
    println("SimpleExample PDF created successfully at: ${simpleExampleOutputFile.absolutePath}")
}
