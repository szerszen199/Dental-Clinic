import React from "react";
import "./Dashboard.css";
import {useTranslation} from "react-i18next";
import dental from "../../assets/hands.svg";


export default function Dashboard() {
    const {t} = useTranslation();
    document.title = t("Dental Clinic");
    return (
        <div className="Dashboard">
            <img src={dental} alt="Edit" className="center" width={400}
                 style={{paddingBottom: "5px", paddingLeft: "3px"}}/>
        </div>
    );
}
