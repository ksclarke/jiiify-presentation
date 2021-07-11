
package info.freelibrary.iiif.presentation.v3.utils; // NOPMD

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SLASH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.Annotation;
import info.freelibrary.iiif.presentation.v3.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.utils.antlr4.JsonPointerBaseListener;
import info.freelibrary.iiif.presentation.v3.utils.antlr4.JsonPointerLexer;
import info.freelibrary.iiif.presentation.v3.utils.antlr4.JsonPointerParser;
import info.freelibrary.iiif.presentation.v3.utils.antlr4.JsonPointerParser.PointerContext;
import info.freelibrary.iiif.presentation.v3.utils.antlr4.JsonPointerParser.TokenContext;

/**
 * A JSON Pointer that can be used to retrieve parts of a IIIF manifest or collection document.
 */
@SuppressWarnings({ PMD.GOD_CLASS, PMD.EXCESSIVE_IMPORTS })
public class JsonPointer { // NOPMD

    /**
     * The logger used by the JSON Pointer class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPointer.class, MessageCodes.BUNDLE);

    /**
     * The set of JSON keys that are single instance keys (i.e., not associated with arrays).
     */
    private static final Set<String> SINGLE_INSTANCE_KEYS = new HashSet<>(Arrays.asList(JsonKeys.ACCOMPANYING_CANVAS,
            JsonKeys.PLACEHOLDER_CANVAS, JsonKeys.VIEWING_DIRECTION, JsonKeys.START, JsonKeys.SUPPLEMENTARY,
            JsonKeys.LABEL, JsonKeys.SUMMARY, JsonKeys.REQUIRED_STATEMENT, JsonKeys.RIGHTS, JsonKeys.ID, JsonKeys.V2_ID,
            JsonKeys.TYPE, JsonKeys.V2_TYPE, JsonKeys.DURATION, JsonKeys.WIDTH, JsonKeys.HEIGHT, JsonKeys.NAV_DATE,
            JsonKeys.MOTIVATION, JsonKeys.FORMAT, JsonKeys.TARGET, JsonKeys.SELECTOR, JsonKeys.TIMEMODE,
            JsonKeys.SOURCE, JsonKeys.PROFILE, JsonKeys.PHYSICAL_SCALE, JsonKeys.PHYSICAL_UNITS));

    /**
     * The JSON Pointer tokens.
     */
    private final List<String> myTokens = new ArrayList<>();

    /**
     * The list of errors encountered when parsing the JSON Pointer string.
     */
    private List<String> myErrors;

    /**
     * Creates a new JSON Pointer from the supplied string.
     *
     * @param aPointer A string representation of a JSON Pointer
     */
    public JsonPointer(final String aPointer) {
        final JsonPointerErrorListener errorListener = new JsonPointerErrorListener();
        final JsonPointerLexer lexer = new JsonPointerLexer(CharStreams.fromString(aPointer));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final JsonPointerParser parser = new JsonPointerParser(tokens);
        final ParseTreeWalker walker = new ParseTreeWalker();
        final List<String> errors;

        // Catch our lexer errors ourselves
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        // Catch our parser errors ourselves
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        walker.walk(new MyJsonPointerListener(), parser.pointer());
        errors = getErrors();

        if (!errors.isEmpty()) {
            throw new InvalidPointerException(errors);
        }
    }

    @Override
    public String toString() {
        return isEmpty() ? EMPTY : SLASH + myTokens.stream().collect(Collectors.joining(SLASH));
    }

    /**
     * Gets whether the pointer is empty (which is valid) or not.
     *
     * @return True if the pointer is empty; else, false
     */
    public boolean isEmpty() {
        return myTokens.isEmpty();
    }

    /**
     * Gets the last token from the JSON Pointer. If a positive number is supplied it's a left-based index position; if
     * a negative number is supplied it's a right-based index position.
     *
     * @param aTokenIndex The index position of the desired token
     * @return The last token from the JSON Pointer
     */
    public String getToken(final int aTokenIndex) {
        final int tokenCount = tokenCount();

        if (tokenCount == 0) {
            return EMPTY;
        }

        if (aTokenIndex < 0) {
            final int absIndex = Math.abs(aTokenIndex);

            if (absIndex > tokenCount) {
                throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_129, aTokenIndex, toString()));
            }

            return myTokens.get(tokenCount - absIndex);
        } else {
            if (aTokenIndex > myTokens.size() - 1) {
                throw new IndexOutOfBoundsException(LOGGER.getMessage(MessageCodes.JPA_129, aTokenIndex, toString()));
            }

            return myTokens.get(aTokenIndex);
        }
    }

    /**
     * Gets the number of pointer tokens.
     *
     * @return The number of pointer tokens
     */
    public int tokenCount() {
        return myTokens.size();
    }

    /**
     * Whether this JSON Pointer references a list.
     *
     * @return True if the pointer references a list; else, false
     */
    public boolean pointsToList() {
        final String token = getToken(-1);

        if (isInteger(token)) {
            return false;
        } else {
            return !SINGLE_INSTANCE_KEYS.contains(token);
        }
    }

    /**
     * Gets a list of subcomponents from the supplied resource.
     *
     * @param <T> The type of component being retrieved
     * @param aResource The resource that the JSON Pointer is evaluated against
     * @param aClass The component being retrieved
     * @return A possibly empty list with the requested components
     * @throws JsonPointerException If the pointer is empty or if there is difficulty calculating the last token
     */
    public <T> List<T> getList(final Resource<?> aResource, final Class<T> aClass) {
        final String lastToken = getLastToken(aResource);
        final String pointer = toString();

        try {
            // if (!aClass.getSimpleName().toLowerCase(Locale.US).equals(lastToken.toLowerCase(Locale.US))) {
            // // This is kind of a duplicate of below, but it gets swallowed by the JsonPointerException
            // throw new ClassCastException(LOGGER.getMessage(MessageCodes.JPA_127, aClass.getName(), pointer));
            // }

            final JsonNode node = JSON.convertValue(aResource, JsonNode.class).at(pointer);

            // JavaType inner = TypeFactory.constructParametricType(Set.class, Integer.class);
            // return TypeFactory.constructParametricType(List.class, inner);

            if (node.isArray()) {
                final int size = node.size();

                for (int index = 0; index < size; index++) {
                    System.out.println(node.get(index).get(JsonKeys.TYPE));
                }
            } else {
                System.out.println(node.get(JsonKeys.TYPE));
            }

            final JavaType inner =
                    JSON.getTypeFactory().constructParametricType(Annotation.class, PaintingAnnotation.class);
            final JavaType outer = JSON.getTypeFactory().constructParametricType(List.class, inner);
            return JSON.getReader(outer).readValue(node);
        } catch (final ClassCastException details) {
            throw new JsonPointerException(details, MessageCodes.JPA_127, aClass.getName(), pointer);
        } catch (final IOException details) {
            throw new JsonPointerException(details, MessageCodes.JPA_128, details.getMessage());
        }
    }

    /**
     * Gets the last token for either an array or single resource pointer.
     *
     * @param aResource A resource the pointer is being evaluated against
     * @return The JSON Pointer's last token
     * @throws JsonPointerException If the pointer is empty or if there is difficulty calculating the last token
     */
    private String getLastToken(final Resource<?> aResource) {
        String lastToken = getToken(-1);

        if (isEmpty()) {
            // Cannot get an array back from a single resource passed in with an empty JSON Pointer
            throw new JsonPointerException(MessageCodes.JPA_126, aResource.getClass().getSimpleName());
        }

        // We need to be pointing to a list to get back a list
        if (!pointsToList()) {
            if (isInteger(lastToken)) {
                lastToken = getToken(-2);
            }

            throw new JsonPointerException(MessageCodes.JPA_125, toString(), lastToken);
        }

        return lastToken;
    }

    /**
     * Gets a subcomponent from the supplied resource.
     *
     * @param <T> The type of component being retrieved
     * @param aResource The resource that the JSON Pointer is evaluated against
     * @param aClass The component being retrieved
     * @return A possibly empty optional with the requested component
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getValue(final Resource<?> aResource, final Class<T> aClass) {
        final boolean isOutputtingString = aClass.equals(String.class); // This is the check

        if (isEmpty()) {
            if (isOutputtingString) {
                return Optional.of((T) aResource.toString());
            } else {
                return Optional.ofNullable(JSON.convertValue(aResource, aClass));
            }
        }

        try {
            if (isOutputtingString) {
                final String json = JSON.valueToTree(aResource).at(toString()).toPrettyString();
                return StringUtils.trimToNull(json) == null ? Optional.empty() : Optional.of((T) json);
            } else {
                final JsonNode node = JSON.convertValue(aResource, JsonNode.class).at(toString());
                return Optional.ofNullable(JSON.getReader(aClass).readValue(node));
            }
        } catch (final IOException details) {
            final String message = details.getMessage();

            // If we can't find the path, return an empty optional
            if (message.contains("JsonToken.NOT_AVAILABLE")) {
                return Optional.empty();
            }

            if (pointsToList()) {
                throw new JsonPointerException(details, MessageCodes.JPA_124, toString(), getToken(-1));
            }

            return Optional.empty();
        }
    }

    /**
     * Tests if the supplied string is an integer.
     *
     * @param aIntString An integer string
     * @return True if the supplied string is an integer
     */
    private boolean isInteger(final String aIntString) {
        final int dashLength = 1;

        if (StringUtils.trimToNull(aIntString) == null) {
            return false;
        }

        for (int index = 0; index < aIntString.length(); index++) {
            if (index == 0 && aIntString.charAt(index) == '-') {
                if (aIntString.length() == dashLength) {
                    return false;
                } else {
                    continue;
                }
            }

            if (Character.digit(aIntString.charAt(index), 10) < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets pointer errors.
     *
     * @return A list of errors
     */
    private List<String> getErrors() {
        if (myErrors == null) {
            myErrors = new ArrayList<>();
        }

        return myErrors;
    }

    /**
     * An ANTLR error handlers.
     */
    private class JsonPointerErrorListener extends BaseErrorListener {

        @Override
        public void syntaxError(final Recognizer<?, ?> aRecognizer, final Object aOffendingSymbol, final int aLine,
                final int aCharPositionInLine, final String aMessage, final RecognitionException aException) {
            getErrors().add(aMessage);
        }
    }

    /**
     * The JsonPointerListener used by this JsonPointer.
     */
    private class MyJsonPointerListener extends JsonPointerBaseListener {

        @Override
        public void visitErrorNode(final ErrorNode aNode) {
            getErrors().add(aNode.getText());
        }

        @Override
        public void exitPointer(final PointerContext aContext) {
            if (getErrors().isEmpty()) {
                final String text = aContext.getText();
                LOGGER.debug(MessageCodes.JPA_122, text == null || text.equals(EMPTY) ? "[EMPTY]" : text);
            }
        }

        @Override
        public void exitToken(final TokenContext aContext) {
            myTokens.add(aContext.getText().toLowerCase(Locale.US));
        }
    }

}
