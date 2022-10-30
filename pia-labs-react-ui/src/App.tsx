import { useEffect } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Header from './components/Header';

function App() {
    const location = useLocation();

    useEffect(() => {
        document.title = APP_NAME;
    }, [ location ]);

    return <>
        <Header/>

        <div className="container-fluid">
            <Outlet/>
        </div>
    </>;
}

export default App;

export const APP_NAME = 'KIV/PIA Labs';
export const LOCALE = 'en-GB';
