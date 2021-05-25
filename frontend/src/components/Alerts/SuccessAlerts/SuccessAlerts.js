import swal from "sweetalert";
import {useTranslation} from "react-i18next";

const {t} = useTranslation();

export default function successAlerts(title, message) {
    swal({
        title: t(title),
        text: t(message),
        closeOnClickOutside: false,
        closeOnEsc: false,
        icon: "success",
        button: {
            text: "OK",
            value: true,
            visible: true,
            closeModal: true,
        },
    });
}
