import { RouterProvider, createHashRouter } from 'react-router-dom';
import Home from './pages/home';
import ErrorPage from './pages/ErrorPage';
import Login from './pages/authentication/login';
import Signup from './pages/authentication/signup';
import './styles/globals.scss'

const router = createHashRouter([  {
  path: '/',
  element: <Home />,
  errorElement: <ErrorPage />,
},
{
  path: '/home',
  element: <Home />,
  errorElement: <ErrorPage />,
},
{
  path: '/login',
  element: <Login />,
  errorElement: <ErrorPage />,
},
{
  path: '/signup',
  element: <Signup />,
  errorElement: <ErrorPage />,
}]);
  function App() {
    return <RouterProvider router={router} />
  }
  
  export default App;
