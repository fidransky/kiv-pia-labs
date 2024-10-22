import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from './App'

const router = createBrowserRouter([
	{
		path: '/',
		element: <App />,
	},
])

function Router() {
	return (
		<RouterProvider router={router}/>
	)
}

export default Router
