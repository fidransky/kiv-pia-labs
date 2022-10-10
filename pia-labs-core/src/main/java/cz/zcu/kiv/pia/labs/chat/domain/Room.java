package cz.zcu.kiv.pia.labs.chat.domain;

import java.util.*;

public class Room {

    private final UUID id;
    private final String name;
    private final User administrator;
    private final List<User> users;
    private final List<Message> messages;

    public Room(String name, User administrator) {
        this(UUID.randomUUID(), name, administrator);
    }

    public Room(UUID id, String name, User administrator) {
        this.id = id;
        this.name = name;
        this.administrator = administrator;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    private Room(Builder builder) {
        id = builder.id;
        name = builder.name;
        administrator = builder.administrator;
        users = builder.users;
        messages = builder.messages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void join(User user) {
        users.add(user);
    }

    public void sendMessage(Message message) {
        messages.add(message);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getAdministrator() {
        return administrator;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", administrator=" + administrator +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String name;
        private User administrator;
        private List<User> users = Collections.emptyList();
        private List<Message> messages = Collections.emptyList();

        private Builder() {
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder administrator(User administrator) {
            this.administrator = administrator;
            return this;
        }

        public Builder users(List<User> users) {
            this.users = users;
            return this;
        }

        public Builder messages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}
