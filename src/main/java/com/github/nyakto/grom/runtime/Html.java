package com.github.nyakto.grom.runtime;

import java.io.IOException;
import java.io.Writer;

public final class Html implements Fragment {
    private final String content;

    public Html(String content) {
        this.content = content;
    }

    @Override
    public void render(Writer writer) throws IOException {
        if (content != null) {
            writer.write(content);
        }
    }
}
