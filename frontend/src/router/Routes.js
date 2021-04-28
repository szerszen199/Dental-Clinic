import React from "react";
import { Route, Switch } from "react-router-dom";
import Home from "../containers/Home/Home";
import Error404 from "../containers/Error404/Error404"
import Registration from "../containers/Registration/Registration";
import Login from "../containers/Login/Login";

export default function Routes() {
    return (
        <Switch>
            <Route exact path="/">
                <Home />
            </Route>
            <Route exact path="/register">
                <Registration/>
            </Route>
            <Route exact path="/login">
                <Login />
            </Route>
            <Route>
                <Error404 />
            </Route>
        </Switch>
    );
}
