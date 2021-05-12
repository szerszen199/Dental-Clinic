import React from "react";
import "./App.css";
import Patient from "./views/Patient/Patient";
import Guest from "./views/Guest/Guest";

function App() {
    if (false === true) {
        return (
            <Guest/>
        );
    } else {
        return (
            <Patient/>
        );
    }
}

export default App;
