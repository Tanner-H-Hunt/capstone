import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./Layout";
import { Children } from "react";
import ErrorPage from "./pages/ErrorPage";
import LandingPage from "./pages/LandingPage";
import { element } from "three/tsl";
import AccountForm from "./pages/AccountForm";

function AppRouter(){
    const routes = [
        {
            path: "",
            element: <Layout />,
            children: [
                {
                    path: "/",
                    element: <LandingPage />
                },
                {
                    path: "*",
                    element: <ErrorPage />
                },
                {
                    path: "/user/create",
                    element: < AccountForm />
                },
                {
                    path: "/user/login",
                    element: < AccountForm />
                }
            ]
        }
    ];

    const router = createBrowserRouter(routes);

    return <RouterProvider router={router} />
}

export default AppRouter;