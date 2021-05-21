// const inMemoryJWTManager = () => {
//     let inMemoryJWT = null;
//     let inMemoryUserRoles = [];
//
//     const getToken = () => inMemoryJWT;
//     const getRoles = () => inMemoryUserRoles;
//
//     const setToken = (token) => {
//         inMemoryJWT = token;
//         console.log(inMemoryJWT)
//         return true;
//     };
//
//     const setRoles = (roles) => {
//         inMemoryUserRoles = roles;
//         return true;
//     };
//
//     const ereaseToken = () => {
//         inMemoryJWT = null;
//         return true;
//     }
//
//     const eraseRoles = () => {
//         inMemoryUserRoles = null;
//         return true;
//     }
//
//     return {
//         ereaseToken,
//         getToken,
//         setToken,
//         setRoles,
//         eraseRoles,
//         getRoles
//     }
// };
//
// export default inMemoryJWTManager();