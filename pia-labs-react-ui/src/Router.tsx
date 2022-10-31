import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App";
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
        ],
    },
]);

export default function Router() {
    return (
        <RouterProvider router={router}/>
    )
}