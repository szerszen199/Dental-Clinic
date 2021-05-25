import React from "react";
import {Redirect, Route, Switch} from "react-router-dom";
import Home from "../components/Home/Home";
import Error404 from "../components/Error404/Error404"
import Registration from "../components/Registration/Registration";
import Login from "../components/Login/Login";
import Dashboard from "../components/Dashboard/Dashboard"
import PrivateRoute from "./PrivateRoute";
import Account from "../components/Account/OwnAccount/Account"
import AccountsList from "../components/AccountsList/AccountsList";
import Prescription from "../components/Prescription/Prescription"
import MyAppointment from "../components/Appointment/MyAppointment/MyAppointment";
import PlanAppointment from "../components/Appointment/PlanAppointment/PlanAppointment";
import ListDoctors from "../components/Appointment/ListDoctors/ListDoctors";
import HomeRoute from "./HomeRoute";
import OtherAccount from "../components/Account/OtherAccount/OtherAccount";

export default function Routes() {
    return (
        <Switch>
            <Route exact path="/">
                <Redirect to="/home" />
            </Route>
            <HomeRoute authed={true} path='/home' component={Dashboard} />
            <Route exact path="/register">
                <Registration/>
            </Route>
            <Route exact path="/guest-home">
                <Home/>
            </Route>
            <Route exact path="/login">
                <Login />
            </Route>
            {/*<PrivateRoute authed={true} path='/dashboard' component={Dashboard} />*/}
            <PrivateRoute authed={true} path='/prescriptions' component={Prescription} />
            <PrivateRoute authed={true} path='/account' component={Account} />
            <PrivateRoute authed={true} path='/other-account/:accId' component={OtherAccount} />
            <PrivateRoute authed={true} path='/accounts' component={AccountsList} />
            <PrivateRoute authed={true} path='/my-appointments' component={MyAppointment} />
            <PrivateRoute authed={true} path='/plan-appointment' component={PlanAppointment} />
            <PrivateRoute authed={true} path='/list-doctors' component={ListDoctors} />
            <Route>
                <Error404 />
            </Route>

        </Switch>
    );
}
