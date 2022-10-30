import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { createRoom } from "../services/RoomService";
import CreateRoomForm from "./CreateRoomForm";

type Props = {
}

export default function CreateRoom(props: Props) {
    const navigate = useNavigate();

    function submitRoom(e: FormEvent) {
        e.preventDefault();

        const form = e.target as HTMLFormElement;
        const name = form.roomName.value;

        createRoom(name)
            .then((room) => navigate('/room/' + room.id));
    }

    return (
        <section className="my-3">
            <div className="row justify-content-sm-center">
                <div className="col-sm-7 col-md-6 col-lg-5 col-xl-4">
                    <h1 className="h3 mb-4">Create a new chat room</h1>

                    <CreateRoomForm onSubmit={(e) => submitRoom(e)}/>
                </div>
            </div>
        </section>
    );
}