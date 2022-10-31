import { FormEvent } from "react";
import { Link, NavLink, useNavigate, useSearchParams } from "react-router-dom";

type Props = {
}

export default function Header(props: Props) {
    const [ searchParams ] = useSearchParams();
    const navigate = useNavigate();

    function searchRooms(e: FormEvent) {
        e.preventDefault();

        const form = e.target as HTMLFormElement;
        const query = form.q.value;

        navigate({
            pathname: '/',
            search: new URLSearchParams({ q: query }).toString(),
        });
    }

    return (
        <header>
            <nav className="navbar navbar-expand-lg bg-light">
                <div className="container-fluid">
                    <Link to="/" className="navbar-brand">Chat rooms</Link>

                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <NavLink to="/" className="nav-link" end>Home</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to="/room/create" className="nav-link">Create chat room</NavLink>
                            </li>
                        </ul>

                        <form method="get" action="/" onSubmit={(e) => searchRooms(e)} className="d-flex" role="search">
                            <input type="search" name="q" defaultValue={searchParams.get('q') ?? ''} placeholder="Search chat room by name" className="form-control me-2" aria-label="Search"/>
                            <button className="btn btn-outline-success" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </nav>
        </header>
    );
}