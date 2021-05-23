export default function findDefaultRole(roles) {
    if (roles.includes(process.env.REACT_APP_ROLE_ADMINISTRATOR)) {
        return process.env.REACT_APP_ROLE_ADMINISTRATOR;
    } else if (roles.includes(process.env.REACT_APP_ROLE_DOCTOR)) {
        return process.env.REACT_APP_ROLE_DOCTOR;
    } else if (roles.includes(process.env.REACT_APP_ROLE_RECEPTIONIST)) {
        return process.env.REACT_APP_ROLE_RECEPTIONIST;
    } else if (roles.includes(process.env.REACT_APP_ROLE_PATIENT)) {
        return process.env.REACT_APP_ROLE_PATIENT;
    } else return process.env.REACT_APP_ROLE_GUEST

}