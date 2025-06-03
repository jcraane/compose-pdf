# Compose PDF

A Kotlin library that brings the declarative UI paradigm of Jetpack Compose to PDF generation. Create beautiful PDFs using a familiar Compose-like syntax.

## Overview

Compose PDF allows you to generate PDF documents using a declarative, composable API inspired by Jetpack Compose. Instead of working directly with low-level PDF libraries, you can define your PDF content using high-level components like Column, Row, Text, and Spacer.

The library provides:
- A Compose-like DSL for defining PDF content
- Layout primitives (Column, Row)
- Text rendering with styling options
- Simple positioning and spacing

## Installation

Add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    mavenCentral()
    // Add the repository when published
}

dependencies {
    implementation("dev.jamiecraane.vibe:compose-pdf:1.0-SNAPSHOT")
}
```

## Usage

### Basic Example

```kotlin
// Imports omitted for brevity
fun main() {
    val outputFile = File("output.pdf")

    ComposePdf.generatePdf(outputFile) {
        Column {
            Text(
                text = "Hello PDF from Compose!",
                fontSize = 24f,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.None.height(16f))

            Row {
                Text("Description text: This PDF was created with Compose PDF")
            }

            Spacer(modifier = Modifier.None.height(16f))

            Text(
                text = "This demonstrates the Compose-style syntax",
                fontSize = 14f,
                color = Color.Blue
            )
        }
    }

    println("PDF created successfully at: ${outputFile.absolutePath}")
}
```

### Manual Positioning Example

You can also position elements manually:

```kotlin
// Imports omitted for brevity
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
}
```

## Features

- **Declarative API**: Define PDF content using a familiar Compose-like syntax
- **Layout System**: Use Column and Row to arrange elements
- **Text Styling**: Control font size, weight, and color
- **Positioning**: Position elements precisely or use layout components
- **Apache PDFBox Integration**: Built on top of the reliable Apache PDFBox library

## Architecture

Compose PDF consists of several key components:

1. **ComposePdf**: The main entry point for generating PDFs
2. **PdfScope**: Provides the DSL context for defining PDF content
3. **Layout Components**: Column, Row, and other layout primitives
4. **Rendering System**: Translates composable elements to PDF drawing commands

## Current Status

This library is currently in early development (1.0-SNAPSHOT). The following features are implemented:

- Basic layout components (Column, Row)
- Text rendering with styling
- Simple positioning and spacing

Upcoming features:
- Image support
- Tables and grids
- Multi-page document support
- More styling options

## License

[Add license information here]

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
