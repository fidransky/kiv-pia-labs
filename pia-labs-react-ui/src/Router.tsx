import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App";
import CreateRoom from "./components/CreateRoom";
import Index from "./components/Index";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App/>,
        children: [
            {
                index: true,
                element: <Index/>,
            },
            {
                path: 'room',
                children: [
                    {
                        path: 'create',
                        element: <CreateRoom/>,
                    },
                ],
            },
        ],
    },
]);

export default function Router() {
    return (
        <RouterProvider router={router}/>
    )
}