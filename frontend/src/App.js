import React from "react";
import "./App.css";
import Patient from "./views/Patient/Patient";
import Guest from "./views/Guest/Guest";
import Admin from "./views/Admin/Admin";
import Receptionist from "./views/Receptionist/Receptionist";
import Doctor from "./views/Doctor/Doctor";

function App() {
    if (false === true) {
        return (
            <Guest/>
        );
    } else {
        return (
            // <Patient/>
            // <Admin/>
            // <Receptionist/>
            <Doctor/>
        );
    }
}

export default App;
