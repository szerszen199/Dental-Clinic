import React from "react";
import { Route, Switch } from "react-router-dom";
import Home from "../containers/Home/Home";
import Error404 from "../containers/Error404/Error404"

export default function Routes() {
    return (
        <Switch>
            <Route exact path="/">
                <Home />
            </Route>
            <Route>
                <Error404 />
            </Route>
        </Switch>
    );
}
