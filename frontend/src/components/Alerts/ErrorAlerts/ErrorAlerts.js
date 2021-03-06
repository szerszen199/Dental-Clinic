import swal from "sweetalert";
import "../Alerts.css"

export default function errorAlerts(title, message) {
    return (
        swal({
            title: title,
            text: message,
            closeOnClickOutside: false,
            closeOnEsc: false,
            icon: "error",
            dangerMode: true,
            button: {
                text: "OK",
                value: true,
                visible: true,
                closeModal: true,
            },
        }));
}
