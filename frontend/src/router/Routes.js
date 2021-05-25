import React from "react";
import {Redirect, Route, Switch} from "react-router-dom";
import Home from "../components/Home/Home";
import Error404 from "../components/Error404/Error404"
import Registration from "../components/Registration/Registration";
import Login from "../components/Login/Login";
import Reset from "../components/ResetPassword/Reset";
import Dashboard from "../components/Dashboard/Dashboard"
import PrivateRoute from "./PrivateRoute";
import Account from "../components/Account/Account"
import AccountsList from "../components/AccountsList/AccountsList";
import Prescription from "../components/Prescription/Prescription"
import MyAppointment from "../components/Appointment/MyAppointment/MyAppointment";
import PlanAppointment from "../components/Appointment/PlanAppointment/PlanAppointment";
import ListDoctors from "../components/Appointment/ListDoctors/ListDoctors";
import HomeRoute from "./HomeRoute";
import Cookies from "js-cookie";

export default function Routes() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)

    function isLoggedIn() {
        return token !== undefined;
    }

    function isPatient() {
        return isLoggedIn() && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_PATIENT
    }

    function isAdministrator() {
        return isLoggedIn() && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_ADMINISTRATOR
    }

    function isReceptionist() {
        return isLoggedIn() && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_RECEPTIONIST
    }

    function isDoctor() {
        return isLoggedIn() && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_DOCTOR
    }

    return (
        <Switch>
            <Route exact path="/">
                <Redirect to="/home"/>
            </Route>
            <HomeRoute authed={isLoggedIn()} path='/home' component={Dashboard}/>
            <Route exact path="/register">
                <Registration/>
            </Route>
            <Route exact path="/guest-home">
                <Home/>
            </Route>
            <Route exact path="/login">
                <Login/>
            </Route>
            <Route exact path="/reset-password">
                <Reset/>
            </Route>
            <PrivateRoute authed={isPatient()} path='/prescriptions' component={Prescription}/>
            <PrivateRoute authed={isLoggedIn()} path='/account' component={Account}/>
            <PrivateRoute authed={isAdministrator()} path='/accounts' component={AccountsList}/>
            <PrivateRoute authed={isPatient() || isReceptionist() || isDoctor()} path='/my-appointments'
                          component={MyAppointment}/>
            <PrivateRoute authed={isPatient() || isReceptionist()} path='/plan-appointment'
                          component={PlanAppointment}/>
            <PrivateRoute authed={isPatient() || isReceptionist() || isDoctor()} path='/list-doctors' component={ListDoctors}/>
            <Route>
                <Error404/>
            </Route>

        </Switch>
    );
}
