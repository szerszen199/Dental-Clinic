import React from "react";
import { Route, Switch} from "react-router-dom";
import Home from "../components/Home/Home";
import Error404 from "../components/Error404/Error404"
import Registration from "../components/Registration/Registration";
import Login from "../components/Login/Login";
import Accounts from "../components/AccountsList/Accounts";
import Dashboard from "../components/Dashboard/Dashboard"
import PrivateRoute from "../PrivateRoute";

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
            <PrivateRoute authed={false} path='/dashboard' component={Dashboard} />
            <Route>
                <Error404 />
            </Route>
        </Switch>
    );
}
