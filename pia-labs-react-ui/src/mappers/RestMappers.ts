import { MessageVO, RoomVO, UserVO } from "pia-labs-typescript-client";
import { Message, Room, User } from "../types";

export function mapRoom_fromVO(roomVO: RoomVO): Room {
    return new Room(roomVO.id, roomVO.name, []);
}

export function mapMessage_fromVO(messageVO: MessageVO): Message {
    return new Message(messageVO.id, mapUser_fromVO(messageVO?.author), messageVO.text, messageVO.timestamp);
}

export function mapUser_fromVO(userVO: UserVO | undefined): User | undefined {
    if (userVO === undefined) return;

    return new User(userVO.username);
}
