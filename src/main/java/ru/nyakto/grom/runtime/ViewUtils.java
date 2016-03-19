package ru.nyakto.grom.runtime;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ViewUtils {
    private static final Set<String> selfClosingTags = new HashSet<>();

    static {
        selfClosingTags.add("area");
        selfClosingTags.add("base");
        selfClosingTags.add("br");
        selfClosingTags.add("col");
        selfClosingTags.add("command");
        selfClosingTags.add("embed");
        selfClosingTags.add("hr");
        selfClosingTags.add("img");
        selfClosingTags.add("input");
        selfClosingTags.add("keygen");
        selfClosingTags.add("link");
        selfClosingTags.add("meta");
        selfClosingTags.add("param");
        selfClosingTags.add("source");
        selfClosingTags.add("track");
        selfClosingTags.add("wbr");
    }

    public static void writeExpressionResult(Writer writer, Fragment fragment) throws IOException {
        fragment.render(writer);
    }

    public static void writeExpressionResult(Writer writer, String text) throws IOException {
        new Text(text).render(writer);
    }

    public static void writeExpressionResult(Writer writer, Object obj) throws IOException {
        if (obj instanceof Fragment) {
            writeExpressionResult(writer, (Fragment) obj);
        } else {
            writeExpressionResult(writer, obj.toString());
        }
    }

    public static void writeTagAttribute(Writer writer, boolean allowShort, String name, Boolean value) throws IOException {
        if (Boolean.TRUE.equals(value)) {
            writer.write(' ');
            writer.write(name);
            if (!allowShort) {
                writer.write("=\"");
                writer.write(name);
                writer.write('"');
            }
        }
    }

    public static void writeTagAttribute(Writer writer, boolean allowShort, String name, Fragment fragment) throws IOException {
        if (fragment == null) {
            return;
        }
        writer.write(' ');
        writer.write(name);
        writer.write("=\"");
        if (fragment instanceof Text) {
            fragment.render(writer);
        } else {
            final StringWriter buffer = new StringWriter();
            fragment.render(buffer);
            new Text(buffer.toString()).render(writer);
        }
        writer.write("\"");
    }

    public static void writeTagAttribute(Writer writer, boolean allowShort, String name, String text) throws IOException {
        if (text == null) {
            return;
        }
        writer.write(' ');
        writer.write(name);
        writer.write("=\"");
        new Text(text).render(writer);
        writer.write("\"");
    }

    public static void writeTagAttribute(Writer writer, boolean allowShort, String name, Object value) throws IOException {
        if (value == null) {
            return;
        }
        if (value instanceof Boolean) {
            writeTagAttribute(writer, allowShort, name, (Boolean) value);
        } else if (value instanceof Fragment) {
            writeTagAttribute(writer, allowShort, name, (Fragment) value);
        } else {
            writeTagAttribute(writer, allowShort, name, value.toString());
        }
    }

    public static DynamicTagAttributesWriter dynamicTagAttributesWriter(Map<String, Object> attributes, Set<String> classNames) {
        return new DynamicTagAttributesWriter(attributes, classNames);
    }

    public static boolean isSelfClosingTag(String tagName) {
        return selfClosingTags.contains(tagName);
    }
}
