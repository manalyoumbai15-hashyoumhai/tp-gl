
package com.uniaccounts.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class StudentAccount implements Prototype<StudentAccount> {

    private static final Set<String> LEVELS = Set.of("L1", "L2", "L3", "M1", "M2");

    private final String id;
    private final String fullName;
    private final String email;
    private final String phone;
    private final String level;
    private final String major;
    private final boolean scholarship;
    private final List<String> courses;

    private StudentAccount(Builder b) {
        this.id = b.id;
        this.fullName = b.fullName;
        this.email = b.email;
        this.phone = b.phone;
        this.level = b.level;
        this.major = b.major;
        this.scholarship = b.scholarship;
        this.courses = new ArrayList<>(b.courses);
    }

    @Override
    public StudentAccount copy() {
        return toBuilder().build();
    }

    public StudentAccount copyWithIdentity(String newId, String newName, String newEmail) {
        return toBuilder().id(newId).fullName(newName).email(newEmail).build();
    }

    private Builder toBuilder() {
        return new Builder(id, fullName, email)
                .phone(phone).level(level).major(major)
                .scholarship(scholarship).courses(courses);
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLevel() {
        return level;
    }

    public String getMajor() {
        return major;
    }

    public boolean isScholarship() {
        return scholarship;
    }

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    @Override
    public String toString() {
        return "StudentAccount{id=" + id + ", name=" + fullName + ", email=" + email
                + ", phone=" + phone + ", level=" + level + ", major=" + major
                + ", scholarship=" + scholarship + ", courses=" + courses + "}";
    }

    public static class Builder {
        private String id;
        private String fullName;
        private String email;
        private String phone = null;
        private String level = "L1";
        private String major = null;
        private boolean scholarship = false;
        private List<String> courses = new ArrayList<>();

        public Builder(String id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        public Builder id(String v) {
            this.id = v;
            return this;
        }

        public Builder fullName(String v) {
            this.fullName = v;
            return this;
        }

        public Builder email(String v) {
            this.email = v;
            return this;
        }

        public Builder phone(String v) {
            this.phone = v;
            return this;
        }

        public Builder level(String v) {
            this.level = v;
            return this;
        }

        public Builder major(String v) {
            this.major = v;
            return this;
        }

        public Builder scholarship(boolean v) {
            this.scholarship = v;
            return this;
        }

        public Builder courses(List<String> v) {
            this.courses = new ArrayList<>(v);
            return this;
        }

        public Builder addCourse(String c) {
            this.courses.add(c);
            return this;
        }

        public StudentAccount build() {
            if (id == null || id.isBlank())
                throw new IllegalStateException("id is required");
            if (fullName == null || fullName.isBlank())
                throw new IllegalStateException("fullName is required");
            if (email == null || !email.contains("@"))
                throw new IllegalStateException("email must contain @");
            if (level == null || !LEVELS.contains(level))
                throw new IllegalStateException("invalid level: " + level);
            return new StudentAccount(this);
        }
    }
}