import React from "react";
import "./Error404.css";
import {useTranslation} from "react-i18next";

export default function NotFound() {
    const {t} = useTranslation()

    return (
        <div className="NotFound text-center">
            <h3>{t("page not found")}</h3>
        </div>
    );
}
