import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Hello from './Hello';
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
			{
				path: '/hello',
				element: <Hello/>,
			},
		],
	},
]);

export default function Router() {
	return (
		<RouterProvider router={router}/>
	)
}