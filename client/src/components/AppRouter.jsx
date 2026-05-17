import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./Layout";
import { Children } from "react";
import ErrorPage from "./pages/ErrorPage";
import LandingPage from "./pages/LandingPage";
import { element } from "three/tsl";
import AccountForm from "./pages/AccountForm";
import { useState } from 'react';
import UserContext from "./contexts/UserContext";
import Documents from "./pages/Documents";
import DocumentLayout from "./DocumentLayout";

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
                    element: loggedInUser ? <Documents /> : <LandingPage />
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
                            element: loggedInUser ? <Documents /> : < AccountForm />
                        },
                        {
                            path: "login",
                            element: loggedInUser ? <Documents /> : < AccountForm />
                        }
                    ]
                },
                {
                    path: "/document/:id",
                    element: loggedInUser ? <DocumentLayout /> : <LandingPage />
                }

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