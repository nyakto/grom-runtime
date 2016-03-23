package com.github.nyakto.grom.runtime;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class DynamicTagAttributesWriter {
    private final Map<String, Object> attributes;
    private final Set<String> classNames;

    public DynamicTagAttributesWriter(Map<String, Object> attributes, Set<String> classNames) {
        this.attributes = attributes;
        this.classNames = classNames;
    }

    public void setClassNameAttribute(Object value) {
        Optional.ofNullable(value)
            .map(Object::toString)
            .map(StringUtils::split)
            .ifPresent(values -> Collections.addAll(classNames, values));
    }

    public void setCheckedAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void setAttribute(String name, Object value) {
        if ("class".equals(name)) {
            setClassNameAttribute(value);
            return;
        }
        setCheckedAttribute(name, value);
    }

    public void setAttributes(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
            setAttribute(attribute.getKey(), attribute.getValue());
        }
    }

    public void write(Writer writer, boolean allowShort) throws IOException {
        if (!classNames.isEmpty()) {
            ViewUtils.writeTagAttribute(writer, allowShort, "class", StringUtils.join(classNames, ' '));
        }
        for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
            ViewUtils.writeTagAttribute(writer, allowShort, attribute.getKey(), attribute.getValue());
        }
    }
}
