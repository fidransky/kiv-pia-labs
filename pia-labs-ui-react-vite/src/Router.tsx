import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App';
import Home from './pages/Home';
import HelloWorld from './pages/HelloWorld';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App/>,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: 'hello',
        element: <HelloWorld />,
      },
    ],
  },
])

export default function Router() {
  return (
    <RouterProvider router={router}/>
  );
}
