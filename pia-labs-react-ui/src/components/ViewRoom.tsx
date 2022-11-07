import { useEffect, useState } from "react";
import { LoaderFunctionArgs, useLoaderData } from "react-router-dom";
import { APP_NAME } from "../App";
import { getRoom, getRoomMessages } from "../services/RoomService";
import { Message, Room } from "../types";
import MessageListItem from "./MessageListItem";
import SendMessageForm from "./SendMessageForm";
import { Client, StompSubscription } from '@stomp/stompjs';

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

const client = new Client();
client.brokerURL = 'ws://localhost:61614/ws';

export default function ViewRoom(props: Props) {
    const { room, messages } = useLoaderData() as LoaderData;
    const [ messageStream, setMessageStream ] = useState(messages);

    useEffect(() => {
        if (room === null) return;

        document.title = APP_NAME + ' - ' + room.name;
    }, [ room ]);

    useEffect(() => {
        let subscription: StompSubscription | undefined;

        client.onConnect = function onConnect(frame) {
            const destination = '/topic/kiv.pia.chat.room.' + room.id;

            subscription = client.subscribe(destination, function onMessageReceived(payload) {
                const message = JSON.parse(payload.body);
                message.timestamp = new Date(message.timestamp);
                setMessageStream((messageStream) => messageStream.concat(message));
            });
        };
        client.activate();

        return () => {
            if (subscription !== undefined) {
                subscription.unsubscribe();
            }
        };

    }, [ room ]);

    return (
        <section className="my-3">
            <div className="row">
                <main className="col">
                    <div className="row justify-content-md-center">
                        <div className="col-md-10 col-lg-9 col-xl-8">
                            {messageStream.map((message) => <MessageListItem key={message.id} message={message}/>)}

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