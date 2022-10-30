import { Link, LoaderFunctionArgs, useLoaderData, useSearchParams } from "react-router-dom";
import { searchRooms } from "../services/RoomService";
import { Room } from "../types";
import RoomListItem from "./RoomListItem";

export function loader({ request }: LoaderFunctionArgs): Promise<Room[]> {
    const url = new URL(request.url);
    const query = url.searchParams.get('q') ?? undefined;
    return searchRooms(query);
}

type Props = {
}

export default function Index(props: Props) {
    const rooms = useLoaderData() as Room[];
    const [ searchParams ] = useSearchParams();

    return (
        <section className="my-3">
            <div className="row justify-content-md-center">
                <div className="col-sm-7 col-md-6 col-lg-5 col-xl-4">
                    <h1 className="h3 mb-4">Join a chat room</h1>

                    {rooms.length === 0 && (
                        <div className="alert alert-warning" role="alert">
                            {searchParams.get('q') === null ? <>
                                No chat rooms were found.
                            </> : <>
                                No chat rooms were found for query <q>{searchParams.get('q')}</q>.
                            </>}
                            
                        </div>
                    )}

                    {rooms.map((room) => <RoomListItem key={room.id} room={room} />)}

                    <div className="text-center mt-3">
                        <Link to="/room/create" className="btn btn-primary btn-lg">Create a new chat room</Link>
                    </div>
                </div>
            </div>
        </section>
    );
}