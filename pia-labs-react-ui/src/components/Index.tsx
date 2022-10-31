import { Configuration, RoomsApi, RoomVO } from "pia-labs-typescript-client";
import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";

type Props = {
}

const basePath = 'http://localhost:8080/pia-labs/spring';
const configuration = new Configuration({ basePath });
const roomsApi = new RoomsApi(configuration);

export default function Index(props: Props) {
    const [ rooms, setRooms ] = useState<RoomVO[]>([]);
    const [ searchParams ] = useSearchParams();

    useEffect(() => {
        const query: string | undefined = searchParams.get('q') ?? undefined;

        roomsApi.listRooms({ name: query })
            .then((rooms) => setRooms(rooms));
    }, [ searchParams ])

    return <>
        {rooms.map((room) => {
            return (
                <div key={room.id} className="d-flex flex-row align-items-center py-3 border-bottom">
                    <strong>{room.name}</strong>
                    <span className="ms-2 badge bg-secondary">N/A</span>
                    <Link to={'/room/' + room.id} className="ms-auto btn btn-outline-primary">Join</Link>
                </div>
            );
        })}
    </>;
}