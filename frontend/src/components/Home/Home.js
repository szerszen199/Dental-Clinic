import React from "react";
import {useTranslation} from "react-i18next";

export default function Home() {
    const {t} = useTranslation()

    return (
        <div className="Home">
            <div className="lander">
                <h1>{t("Dental Clinic")}</h1>
                <p className="text-muted">{t()} System informatyczny dla kliniki stomatologicznej.</p>
            </div>
        </div>
    );
}
