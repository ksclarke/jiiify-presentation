
package info.freelibrary.iiif.presentation.v3.utils.cmdline;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

/**
 * A small file type detector that recognizes ZIP archives, CSV files, and other file types.
 */
class JPv3FileTypeDetector extends FileTypeDetector {

    /** The ZIP magic number. */
    private static final int ZIP_MAGIC = 0x504B0304;

    /** The header size. */
    private static final int HEADER_SIZE = 4;

    @Override
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY, Checkstyle.BOOLEAN_EXPR_COMPLEXITY, "BooleanExpressionComplexity" })
    public final String probeContentType(final Path aPath) throws IOException {
        // Check the file at the supplied path for the ZIP magic number
        try (InputStream inStream = Files.newInputStream(aPath)) {
            final byte[] header = new byte[4];
            final int bytesRead = inStream.read(header);

            if (bytesRead == HEADER_SIZE) {
                final int magicNum = ((header[0] & 0xFF) << 24) | ((header[1] & 0xFF) << 16) |
                        ((header[2] & 0xFF) << 8) | (header[3] & 0xFF);

                if (magicNum == ZIP_MAGIC) {
                    return MediaType.APPLICATION_ZIP.toString();
                }
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(aPath, StandardCharsets.UTF_8)) {
            int linesChecked = 0;
            String line;

            while ((line = reader.readLine()) != null && linesChecked < 10) {
                // Skip empty lines at the top of the file
                if ((line = line.trim()).isEmpty()) {
                    continue;
                }

                linesChecked++;

                // Common CSV delimiters
                if (line.contains(",") || line.contains("\t") || line.contains(";") || line.contains("|")) {
                    return MediaType.TEXT_CSV.toString();
                }

                // If we encounter non‑printable characters, bail out -- it's likely binary.
                if (!line.chars().allMatch(ch -> ch >= 32 && ch <= 126 || ch == '\r' || ch == '\n')) {
                    break;
                }
            }
        }

        return null;
    }
}
