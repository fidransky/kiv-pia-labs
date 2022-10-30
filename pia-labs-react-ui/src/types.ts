export class Room {
    public id: string | undefined;
    public name: string;
    public messages: Message[];

    constructor(id: string | undefined, name: string, messages: Message[]) {
        this.id = id;
        this.name = name;
        this.messages = messages;
    }
}

export class User {
    public username: string | undefined;

    constructor(username: string | undefined) {
        this.username = username;
    }
}

export class Message {
    public id: string | undefined;
    public author: User | undefined;
    public text: string;
    public timestamp: Date | undefined;

    constructor(id: string | undefined, author: User | undefined, text: string, timestamp: Date | undefined) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.timestamp = timestamp;
    }
}