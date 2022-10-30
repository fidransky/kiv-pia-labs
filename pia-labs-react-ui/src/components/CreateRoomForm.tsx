import { FormEventHandler } from "react"

type Props = {
    onSubmit: FormEventHandler,
}

export default function CreateRoomForm({ onSubmit }: Props) {
    return (
        <form method="post" onSubmit={onSubmit}>
            <div className="mb-3">
                <label htmlFor="roomName" className="form-label">Chat room name</label>
                <input type="text" name="roomName" className="form-control" id="roomName" required autoFocus/>
            </div>
            <button type="submit" className="btn btn-primary">Create</button>
        </form>
    );
}