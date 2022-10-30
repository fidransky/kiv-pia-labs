import { FormEvent, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { APP_NAME } from "../App";
import { getRoom, getRoomMessages, sendMessage } from "../services/RoomService";
import { Message, Room } from "../types";
import MessageListItem from "./MessageListItem";
import SendMessageForm from "./SendMessageForm";

type Props = {
}

export default function ViewRoom(props: Props) {
    const { roomId } = useParams();
    const [ room, setRoom ] = useState<Room | null>(null);
    const [ messages, setMessages ] = useState<Message[]>([]);

    useEffect(() => {
        getRoom(roomId)
            .then(setRoom)
            .catch((e) => console.warn(e));

    }, [ roomId ]);

    useEffect(() => {
        if (roomId === undefined) return;

        loadMessages(roomId);

    }, [ roomId ]);

    useEffect(() => {
        if (room === null) return;

        document.title = APP_NAME + ' - ' + room.name;
    }, [ room ]);

    function loadMessages(roomId: string) {
        if (roomId === undefined) return;

        getRoomMessages(roomId)
            .then(setMessages);
    }

    function submitMessage(e: FormEvent) {
        e.preventDefault();

        if (roomId === undefined) return;

        const form = e.target as HTMLFormElement;
        const text = form.text.value;

        sendMessage(roomId, text)
            .then(() => {
                form.reset();
                loadMessages(roomId);
            });
    }

    return (
        <section className="my-3">
            <div className="row">
                <main className="col">
                    <div className="row justify-content-md-center">
                        <div className="col-md-10 col-lg-9 col-xl-8">
                            {messages.map((message) => <MessageListItem key={message.id} message={message}/>)}

                            <SendMessageForm onSubmit={(e) => submitMessage(e)}/>
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