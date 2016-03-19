package ru.nyakto.grom.runtime;

import java.io.IOException;
import java.io.Writer;

public interface Fragment {
    public void render(Writer writer) throws IOException;
}
