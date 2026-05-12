import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./Layout";
import { Children } from "react";


function AppRouter(){
    const routes = [
        {
            path: "",
            element: <Layout />,
            children: [
                {
                    path: "",
                    element: <h1> hello world </ h1>
                }
            ]
        }
    ];

    const router = createBrowserRouter(routes);

    return <RouterProvider router={router} />
}

export default AppRouter;