import React from "react";
import {Redirect, Route, Switch} from "react-router-dom";
import Home from "../components/Home/Home";
import Error404 from "../components/Error404/Error404"
import Registration from "../components/Registration/Registration";
import Login from "../components/Login/Login";
import Reset from "../components/ResetPassword/Reset";
import Dashboard from "../components/Dashboard/Dashboard"
import PatientsAccountList from "../components/Documentation/PatientsAccountLIst"
import PrivateRoute from "./PrivateRoute";
import GuestHomeRoute from "./GuestHomeRoute";
import Account from "../components/Account/OwnAccount/Account"
import AccountsList from "../components/AccountsList/AccountsList";
import PrescriptionsUser from "../components/Prescription/PrescriptionsUser"
import MyAppointment from "../components/Appointment/MyAppointments/MyAppointments";
import PlanReceptionistAppointment from "../components/Appointment/PlanAppointment/Receptionist/PlanReceptionistAppointment";
import PlanPatientAppointment from "../components/Appointment/PlanAppointment/Patient/PlanPatientAppointment";
import ViewDoctorAppointmentSlots from "../components/Appointment/PlanAppointment/Doctor/ViewDoctorAppointmentSlots";
import ListDoctors from "../components/Appointment/ListDoctors/ListDoctors";
import HomeRoute from "./HomeRoute";
import Cookies from "js-cookie";
import OtherAccount from "../components/Account/OtherAccount/OtherAccount";
import SetNewPassword from "../components/ResetPassword/setNewPassword/SetNewPassword";
import SetNewPasswordAdmin from "../components/ResetPassword/setNewPasswordAdmin/SetNewPasswordAdmin";
import UnlockConfirm from "../components/Confirmation/UnlockConfirm";
import AccountActivationConfirm from "../components/Confirmation/AccountActivationConfirm";
import MailChangeConfirm from "../components/Confirmation/MailChangeConfirm";
import PasswordChangeConfirm from "../components/Confirmation/PasswordChangeConfirm";
import ListPatients from "../components/Appointment/ListPatients/ListPatients";
import AddAppointment from "../components/Appointment/AddAppointment/AddAppointment";
import DocumentationList from "../components/Documentation/Documentation";
import EditAppointmentSlot from "../components/Appointment/EditAppointmentSlot/EditAppointmentSlot";

import NewDocumentationEntry from "../components/Documentation/NewDocumentationEntry/NewDocumentationEntry";
import CreatePrescription from "../components/Documentation/CreatePrescription";
import EditDocumentationEntry from "../components/Documentation/EditDocumentationEntry/EditDocumentationEntry";
import EditBookedAppointment from "../components/Appointment/EditBookedAppointment/EditBookedAppointment";
import Prescriptions from "../components/Documentation/Prescriptions";
import EditPrescription from "../components/Prescription/EditPrescription/EditPrescription";
import RateAppointment from "../components/Appointment/RateAppointment";

export default function Routes() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    function isLoggedIn() {
        return token !== undefined;
    }

    function isPatient() {
        return isLoggedIn() && Cookies !== undefined && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_PATIENT;
    }

    function isAdministrator() {
        return isLoggedIn() && Cookies !== undefined && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_ADMINISTRATOR;
    }

    function isReceptionist() {
        return isLoggedIn() && Cookies !== undefined && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_RECEPTIONIST;
    }

    function isDoctor() {
        return isLoggedIn() && Cookies !== undefined && Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) === process.env.REACT_APP_ROLE_DOCTOR;
    }

    return (
        <Switch>
            <Route exact path="/">
                <Redirect to="/home"/>
            </Route>
            <HomeRoute authed={isLoggedIn()} path='/home' component={Dashboard}/>
            <PrivateRoute authed={!isLoggedIn()}  path="/register" component={Registration}/>
            <GuestHomeRoute authed={!isLoggedIn()} path="/guest-home" component={Home}/>
            <PrivateRoute authed={!isLoggedIn()}  path="/login" component={Login}/>
            <Route exact path="/reset-password">
                <Reset/>
            </Route>
            <Route exact path="/new-password-admin/:token">
                <SetNewPasswordAdmin/>
            </Route>
            <Route exact path="/new-password/:token">
                <SetNewPassword/>
            </Route>
            <Route path='/unlock-account/:token' component={UnlockConfirm}/>
            <PrivateRoute authed={isPatient()} path='/prescriptions' component={PrescriptionsUser}/>
            <PrivateRoute authed={isLoggedIn()} path='/account' component={Account}/>
            <PrivateRoute authed={isAdministrator() || isReceptionist()} path='/accounts' component={AccountsList}/>
            <PrivateRoute authed={isAdministrator()} path='/other-account/:accId' component={OtherAccount} />
            <PrivateRoute authed={isDoctor()} path='/account-documentation/:accId' component={DocumentationList} />
            <PrivateRoute authed={isDoctor()} path='/edit-documentation-entry/:accId/:entryId' component={EditDocumentationEntry} />
            <PrivateRoute authed={isDoctor()} path='/create-prescription/:accId' component={CreatePrescription} />
            <PrivateRoute authed={isDoctor()} path='/new-documentation-entry/:accId' component={NewDocumentationEntry} />
            <PrivateRoute authed={isDoctor()} path='/account-prescriptions/:accId' component={Prescriptions} />
            <PrivateRoute authed={isPatient() || isReceptionist() || isDoctor()} path='/my-appointments'
                          component={MyAppointment}/>
            <PrivateRoute authed={isDoctor()} path='/edit-prescription/:prescId' component={EditPrescription}/>
            <PrivateRoute authed={isPatient()} path='/plan-appointment-patient'
                          component={PlanPatientAppointment}/>
            <PrivateRoute authed={isReceptionist()} path='/plan-appointment-receptionist'
                          component={PlanReceptionistAppointment}/>
            <PrivateRoute authed={isReceptionist()} path='/add-appointment'
                          component={AddAppointment}/>
            <PrivateRoute authed={isDoctor()} path='/my-appointments-slots'
                          component={ViewDoctorAppointmentSlots}/>
            <PrivateRoute authed={isReceptionist()} path='/appointment-slot/:appId' component={EditAppointmentSlot} />
            <PrivateRoute authed={isDoctor()} path='/patients_account_list' component={PatientsAccountList}/>
            <PrivateRoute authed={isPatient() || isReceptionist() || isDoctor()} path='/list-doctors'
                          component={ListDoctors}/>
            <PrivateRoute authed={isReceptionist()} path='/edit-appointment/:appId' component={EditBookedAppointment}/>
            <PrivateRoute authed={isReceptionist()} path='/list-patients'
                          component={ListPatients}/>
            <Route authed={isLoggedIn()} path='/activation-confirm/:token' component={AccountActivationConfirm}/>
            <Route authed={isLoggedIn()} path='/mail-change-confirm/:token' component={MailChangeConfirm}/>
            <Route authed={isLoggedIn()} path='/password-change-confirm/:token' component={PasswordChangeConfirm}/>
            <Route path='/rate-appointment/:id/:token' component={RateAppointment}/>
            <Route path='/not-found' component={Error404}/>
            <Route>
                <Error404/>
            </Route>

        </Switch>
    );
}
