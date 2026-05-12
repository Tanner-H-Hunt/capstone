import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./Layout";
import { Children } from "react";
import ErrorPage from "./pages/ErrorPage";
import LandingPage from "./pages/LandingPage";
import { element } from "three/tsl";
import AccountForm from "./pages/AccountForm";
import { useState } from 'react';
import UserContext from "./contexts/UserContext";

function AppRouter(){

    const [loggedInUser, setLoggedInUser] = useState(localStorage.getItem("user"));
    const contextValue = {loggedInUser, setLoggedInUser};

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
                    path: "/user",
                    children: [
                        {
                            path: "create",
                            element: < AccountForm />
                        },
                        {
                            path: "login",
                            element: < AccountForm />
                        }
                    ]
                },

            ]
        }
    ];

    const router = createBrowserRouter(routes);

    return (
        <UserContext.Provider value={contextValue}>
            <RouterProvider router={router} />
        </UserContext.Provider>
    );
}

export default AppRouter;