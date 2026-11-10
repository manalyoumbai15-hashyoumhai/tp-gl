package com.uniaccounts.audit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditLogger {

    private static class Holder {
        private static final AuditLogger INSTANCE = new AuditLogger();
    }

    private final List<String> entries = new ArrayList<>();

    private AuditLogger() {
    }

    public static AuditLogger getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void log(String action, String details) {
        entries.add(LocalDateTime.now().withNano(0) + " | " + action + " | " + details);
    }

    public synchronized List<String> getEntries() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }
}