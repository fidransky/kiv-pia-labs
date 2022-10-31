import { Link } from "react-router-dom";
import { Room } from "../types";

type Props = {
    room: Room,
}

export default function RoomListItem({ room }: Props) {
    return (
        <div className="d-flex flex-row align-items-center py-3 border-bottom">
            <strong>{room.name}</strong>
            <span className="ms-2 badge bg-secondary">{room.messages.length}</span>
            <Link to={'/room/' + room.id} className="ms-auto btn btn-outline-primary">Join</Link>
        </div>
    )
}