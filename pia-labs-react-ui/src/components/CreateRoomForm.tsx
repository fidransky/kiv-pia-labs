import { ActionFunctionArgs, Form, redirect } from "react-router-dom";
import { BASE_PATH } from "../App";
import { createRoom } from "../services/RoomService";

type Props = {
}

export async function action({ request }: ActionFunctionArgs) {
    const formData = await request.formData();
    const name = formData.get('roomName')?.toString();

    if (name === undefined) return;

    return createRoom(name)
        .then((room) => redirect(BASE_PATH + '/room/' + room.id));
}

export default function CreateRoomForm(props: Props) {
    return (
        <Form method="post" action="">
            <div className="mb-3">
                <label htmlFor="roomName" className="form-label">Chat room name</label>
                <input type="text" name="roomName" className="form-control" id="roomName" required autoFocus/>
            </div>
            <button type="submit" className="btn btn-primary">Create</button>
        </Form>
    );
}