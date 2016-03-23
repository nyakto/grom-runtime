package com.github.nyakto.grom.runtime;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.Writer;

public final class Text implements Fragment {
    private final String escapedContent;

    public Text(String content) {
        escapedContent = StringEscapeUtils.escapeHtml4(content);
    }

    @Override
    public void render(Writer writer) throws IOException {
        if (escapedContent != null) {
            writer.write(escapedContent);
        }
    }
}
