import { Outlet } from 'react-router-dom';
import Header from './components/Header';

function App() {
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
