import CreateRoomForm from "./CreateRoomForm";

type Props = {
}

export default function CreateRoom(props: Props) {
    return (
        <section className="my-3">
            <div className="row justify-content-sm-center">
                <div className="col-sm-7 col-md-6 col-lg-5 col-xl-4">
                    <h1 className="h3 mb-4">Create a new chat room</h1>

                    <CreateRoomForm/>
                </div>
            </div>
        </section>
    );
}