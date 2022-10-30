import { useEffect } from "react";
import { LoaderFunctionArgs, useLoaderData } from "react-router-dom";
import { APP_NAME } from "../App";
import { getRoom, getRoomMessages } from "../services/RoomService";
import { Message, Room } from "../types";
import MessageListItem from "./MessageListItem";
import SendMessageForm from "./SendMessageForm";

export function loader({ params }: LoaderFunctionArgs): Promise<LoaderData> {
    return Promise.all([
        getRoom(params.roomId),
        getRoomMessages(params.roomId),
    ])
        .then((result) => {
            return {
                room: result[0],
                messages: result[1],
            };
        });
}

type LoaderData = {
    room: Room,
    messages: Message[],
}

type Props = {
}

export default function ViewRoom(props: Props) {
    const { room, messages } = useLoaderData() as LoaderData;

    useEffect(() => {
        if (room === null) return;

        document.title = APP_NAME + ' - ' + room.name;
    }, [ room ]);

    return (
        <section className="my-3">
            <div className="row">
                <main className="col">
                    <div className="row justify-content-md-center">
                        <div className="col-md-10 col-lg-9 col-xl-8">
                            {messages.map((message) => <MessageListItem key={message.id} message={message}/>)}

                            <SendMessageForm/>
                        </div>
                    </div>
                </main>

                <div className="col-2">
                    sidebar
                </div>
            </div>
        </section>
    );
}