import useDateFormat from "../hooks/useDateFormat";
import { Message } from "../types";

type Props = {
    message: Message,
}

const dateTimeFormatOptions: Intl.DateTimeFormatOptions = { dateStyle: 'short', timeStyle: 'medium' };

export default function MessageListItem({ message }: Props) {
    const dateFormatter = useDateFormat(dateTimeFormatOptions);

    return (
        <div className="d-flex flex-row py-3 border-bottom">
            <strong className="me-3 text-nowrap">{message.author?.username}:</strong>
            <span>{message.text}</span>
            <time className="ms-auto text-muted text-nowrap">{dateFormatter.format(message.timestamp)}</time>
        </div>
    );
}