import { Configuration, MessagesApi, MessageVO, RoomsApi } from "pia-labs-typescript-client";
import * as GraphQLMappers from "../mappers/GraphQLMappers";
import * as RestMappers from "../mappers/RestMappers";
import { Message, Room } from "../types";
import * as GraphQLClient from "./GraphQLClient";

const basePath = 'http://localhost:8080/pia-labs/spring';
const configuration = new Configuration({ basePath });
const roomsApi = new RoomsApi(configuration);
const messagesApi = new MessagesApi(configuration);

export async function searchRooms(name: string | undefined): Promise<Room[]> {
    return GraphQLClient.searchRooms({ name })
        .then((roomVOs) => roomVOs.map(GraphQLMappers.mapRoom_fromVO));
}

export async function createRoom(name: string): Promise<Room> {
    return roomsApi.createRoom({ roomVO: { name } })
        .then(RestMappers.mapRoom_fromVO);
}

export async function getRoom(roomId: string | undefined): Promise<Room> {
    if (roomId === undefined) throw new Error('roomId is undefined');

    return roomsApi.getRoom({ roomId })
        .then(RestMappers.mapRoom_fromVO);
}

export async function getRoomMessages(roomId: string | undefined): Promise<Message[]> {
    if (roomId === undefined) throw new Error('roomId is undefined');

    return messagesApi.getRoomMessages({ roomId })
        .then((messageVOs) => messageVOs.map(RestMappers.mapMessage_fromVO));
}

export async function sendMessage(roomId: string | undefined, text: string) {
    if (roomId === undefined) throw new Error('roomId is undefined');

    const messageVO: MessageVO = {
        text,
    };

    return messagesApi.sendMessage({ roomId, messageVO });
}
