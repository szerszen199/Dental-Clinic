export default function parseAccessLevel(accessLevel) {
    let levelName;
    switch (accessLevel) {
        case process.env.REACT_APP_ROLE_ADMINISTRATOR:
            levelName = "Administrator";
            break;
        case process.env.REACT_APP_ROLE_PATIENT:
            levelName = "Patient";
            break;
        case process.env.REACT_APP_ROLE_DOCTOR:
            levelName = 'Doctor';
            break;
        case process.env.REACT_APP_ROLE_RECEPTIONIST:
            levelName = 'Receptionist';
            break;
        default:
            levelName = accessLevel
            break;
    }

    return levelName;


}