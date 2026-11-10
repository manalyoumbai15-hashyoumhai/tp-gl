package com.uniaccounts.app;

import com.uniaccounts.audit.AuditLogger;
import com.uniaccounts.model.StudentAccount;
import com.uniaccounts.repository.AccountRepository;

public class Main {
    public static void main(String[] args) {
        AccountRepository repo = AccountRepository.getInstance();

        System.out.println("=== 1) Builder: create accounts ===");
        StudentAccount s1 = new StudentAccount.Builder("2024001", "Amine Kaci", "amine@univ.dz")
                .phone("0555123456").level("L2").major("Informatique")
                .scholarship(true).addCourse("POO").addCourse("BDD").build();
        repo.add(s1);
        System.out.println(s1);

        StudentAccount s2 = new StudentAccount.Builder("2024002", "Sara Haddad", "sara@univ.dz").build();
        repo.add(s2);
        System.out.println(s2);

        System.out.println("\n=== 2) Builder: validation errors ===");
        tryRun("email without @", () -> new StudentAccount.Builder("2024003", "X", "xunivdz").build());
        tryRun("blank id", () -> new StudentAccount.Builder(" ", "X", "x@univ.dz").build());
        tryRun("invalid level", () -> new StudentAccount.Builder("2024003", "X", "x@univ.dz").level("L9").build());

        System.out.println("\n=== 3) Singleton ===");
        System.out.println("Same repository? " + (repo == AccountRepository.getInstance()));
        System.out.println("Same logger? " + (AuditLogger.getInstance() == AuditLogger.getInstance()));
        tryRun("duplicate id", () -> repo.add(s1));

        System.out.println("\n=== 4) Prototype: L3 Informatique batch ===");
        StudentAccount template = new StudentAccount.Builder("TEMPLATE", "Template", "template@univ.dz")
                .level("L3").major("Informatique").scholarship(false)
                .addCourse("IA").addCourse("Reseaux").addCourse("Design Patterns").build();

        String[][] intake = {
                { "2024101", "Yacine Ouali", "yacine@univ.dz" },
                { "2024102", "Nadia Brahimi", "nadia@univ.dz" },
                { "2024103", "Walid Messaoud", "walid@univ.dz" } };
        for (String[] r : intake)
            repo.add(template.copyWithIdentity(r[0], r[1], r[2]));

        StudentAccount c1 = repo.findById("2024101").get();
        StudentAccount c2 = repo.findById("2024102").get();
        System.out.println(c1);
        System.out.println("Different objects? " + (c1 != c2));
        System.out.println("Independent course lists? " + (c1.getCourses() != c2.getCourses()));
        System.out.println("Same courses as template? " + c1.getCourses().equals(template.getCourses()));

        System.out.println("\nAccounts in repository: " + repo.count());

        System.out.println("\n=== 5) Audit log ===");
        AuditLogger.getInstance().getEntries().forEach(System.out::println);
    }

    private static void tryRun(String label, Runnable r) {
        try {
            r.run();
            System.out.println("[" + label + "] NOT rejected!");
        } catch (RuntimeException e) {
            System.out.println("[" + label + "] rejected: " + e.getMessage());
        }
    }
}