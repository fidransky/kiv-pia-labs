import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './Home';
import Layout from './Layout';

const router = createBrowserRouter([
	{
		path: '/',
		element: <Layout/>,
		children: [
			{
				index: true,
				element: <Home zoomLevel={10}/>,
			},
		],
	},
]);

export default function Router() {
	return (
		<RouterProvider router={router}/>
	)
}