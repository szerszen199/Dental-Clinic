import React from 'react';
import {Route, Redirect} from 'react-router-dom';
// import {isLogin} from '../utils';

// const PrivateRoute = ({component: Component, ...rest}) => {
//     return (
//         <Route {...rest} render={props => (
//             isLogin() ?
//                 <Component {...props} />
//                 : <Redirect to="/login" />
//         )} />
//     );
// };

const PrivateRoute = ({component: Component, authed, ...rest}) => {
    return (
        <Route
            {...rest}
            render={(props) => authed === true
                ? <Component {...props} />
                : <Redirect to={{pathname: '/login', state: {from: props.location}}}/>}
        />
    )
}

export default PrivateRoute;
