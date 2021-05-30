import swal from "sweetalert";
import "../Alerts.css"

export default function confirmationAlerts(title, message) {
    return swal({
        title: title,
        text: message,
        closeOnClickOutside: false,
        closeOnEsc: false,
        icon: "warning",
        dangerMode: true,
        buttons: true,
    }).then((confirmed) => {
        return confirmed;
    });
}
