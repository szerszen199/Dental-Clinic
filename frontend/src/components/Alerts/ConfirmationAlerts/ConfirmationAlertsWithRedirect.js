import swal from "sweetalert";
import "../Alerts.css"

export default function confirmationAlertsWithRedirect(title, message, path) {
    return swal({
        title: title,
        text: message,
        closeOnClickOutside: false,
        closeOnEsc: false,
        icon: "warning",
        dangerMode: true,
        buttons: true,
    }).then(() => {
        window.location.hash = path;
    });
}
