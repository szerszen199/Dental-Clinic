import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import translationEN from "./EN/EN.json";
import translationPL from "./PL/PL.json";
import {initReactI18next} from "react-i18next";


i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        debug: true,
        lng: "en",
        fallbackLng: "en",

        keySeparator: false,

        interpolation: {
            escapeValue: false
        },

        resources: {
            EN: {
                translations: translationEN
            },
            PL: {
                translations: translationPL
            }
        },
        ns: ["translations"],
        defaultNS: "translations"
    });

export default i18n;
