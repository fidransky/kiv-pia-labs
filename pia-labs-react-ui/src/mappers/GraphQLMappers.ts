import { MessageVo, RoomVo, UserVo } from "../generated-types";
import { Message, Room, User } from "../types";

export function mapRoom_fromVO(roomVO: RoomVo): Room {
    return new Room(roomVO.id, roomVO.name, roomVO.messages.map(mapMessage_fromVO));
}

export function mapMessage_fromVO(messageVO: MessageVo): Message {
    return new Message(messageVO.id, mapUser_fromVO(messageVO?.author), messageVO.text, messageVO.timestamp);
}

export function mapUser_fromVO(userVO: UserVo | undefined): User | undefined {
    if (userVO === undefined) return;

    return new User(userVO.username);
}
