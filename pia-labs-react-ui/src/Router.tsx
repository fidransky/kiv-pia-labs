import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App, { BASE_PATH } from "./App";
import CreateRoom from "./components/CreateRoom";
import { action as createRoomAction } from "./components/CreateRoomForm";
import Index, { loader as indexLoader } from "./components/Index";
import { action as sendMessageAction } from "./components/SendMessageForm";
import ViewRoom, { loader as viewRoomLoader } from "./components/ViewRoom";

const router = createBrowserRouter([
    {
        path: '/',
        element: <App/>,
        children: [
            {
                index: true,
                element: <Index/>,
                loader: indexLoader,
            },
            {
                path: 'room',
                children: [
                    {
                        path: 'create',
                        element: <CreateRoom/>,
                        action: createRoomAction,
                    },
                    {
                        path: ':roomId',
                        element: <ViewRoom/>,
                        loader: viewRoomLoader,
                        children: [
                            {
                                path: 'message',
                                action: sendMessageAction,
                            },
                        ],
                    },
                ],
            },
        ],
    },
], {
    basename: BASE_PATH,
});

export default function Router() {
    return (
        <RouterProvider router={router}/>
    )
}