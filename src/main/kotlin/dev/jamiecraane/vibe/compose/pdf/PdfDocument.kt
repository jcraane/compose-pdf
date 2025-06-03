package dev.jamiecraane.vibe.compose.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import java.io.File
import java.io.OutputStream

/**
 * Main class for creating and managing PDF documents.
 * This class wraps the Apache PDFBox library and provides a simpler API for use with Compose.
 */
class PdfDocument private constructor(
    private val document: PDDocument
) {
    companion object {
        /**
         * Creates a new PDF document that will be written to the specified file.
         *
         * @param file The file to write the PDF to
         * @return A new PdfDocument instance
         */
        fun create(file: File): PdfDocument {
            val document = PDDocument()
            document.addPage(PDPage())
            return PdfDocument(document).apply {
                // Set up auto-save to the file when closed
                this.outputFile = file
            }
        }

        /**
         * Creates a new PDF document that will be written to the specified output stream.
         *
         * @param outputStream The output stream to write the PDF to
         * @return A new PdfDocument instance
         */
        fun create(outputStream: OutputStream): PdfDocument {
            val document = PDDocument()
            document.addPage(PDPage())
            return PdfDocument(document).apply {
                // Set up auto-save to the output stream when closed
                this.outputStream = outputStream
            }
        }
    }

    // Store the output destination for saving when closed
    private var outputFile: File? = null
    private var outputStream: OutputStream? = null

    /**
     * Closes the document and releases resources.
     * This must be called when you're done with the document to ensure it's properly saved.
     */
    fun close() {
        try {
            // Save to the appropriate destination
            when {
                outputFile != null -> document.save(outputFile!!)
                outputStream != null -> document.save(outputStream!!)
            }
        } finally {
            document.close()
        }
    }

    /**
     * Gets the underlying PDFBox document.
     * This is provided for advanced use cases where direct access to the PDFBox API is needed.
     */
    fun getPdfBoxDocument(): PDDocument = document
}
