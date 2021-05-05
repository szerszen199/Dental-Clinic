import React from "react";
import { Route, Switch } from "react-router-dom";
import Home from "../containers/Home/Home";
import Error404 from "../containers/Error404/Error404"
import Registration from "../containers/Registration/Registration";
import Login from "../containers/Login/Login";
import Accounts from "../containers/AccountsList/Accounts";

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

            <Route exact path="/accounts">
                <Accounts/>
            </Route>
            <Route>
                <Error404 />
            </Route>
        </Switch>
    );
}
