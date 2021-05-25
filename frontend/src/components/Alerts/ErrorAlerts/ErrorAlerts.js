import swal from "sweetalert";
import "../Alerts.css"

export default function confirmationAlerts(title, message) {
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
    });
}
