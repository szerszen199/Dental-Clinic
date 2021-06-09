import swal from "sweetalert";
import "../Alerts.css"

export default function successAlertsWithRedirect(title, message,path) {
    return (
        swal({
            title: title,
            text: message,
            closeOnClickOutside: false,
            closeOnEsc: false,
            icon: "success",
            button: {
                text: "OK",
                value: true,
                visible: true,
                closeModal: true,
            },
        }).then(() => {
                window.location.hash = path;
            }
        ));

}
