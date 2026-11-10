package com.uniaccounts.repository;

import com.uniaccounts.audit.AuditLogger;
import com.uniaccounts.model.StudentAccount;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountRepository {

    private static class Holder {
        private static final AccountRepository INSTANCE = new AccountRepository();
    }

    private final Map<String, StudentAccount> accounts = new LinkedHashMap<>();

    private AccountRepository() {
    }

    public static AccountRepository getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void add(StudentAccount account) {
        if (accounts.containsKey(account.getId())) {
            AuditLogger.getInstance().log("REJECTED", "duplicate id: " + account.getId());
            throw new IllegalArgumentException("id already exists: " + account.getId());
        }
        accounts.put(account.getId(), account);
        AuditLogger.getInstance().log("CREATED", account.getId() + " - " + account.getFullName());
    }

    public synchronized Optional<StudentAccount> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized List<StudentAccount> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public synchronized int count() {
        return accounts.size();
    }
}