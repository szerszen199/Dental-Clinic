import React from 'react';
import {Route, Redirect} from 'react-router-dom';

const HomeRoute = ({component: Component, authed, ...rest}) => {
    return (
        <Route
            {...rest}
            render={(props) => authed === true
                ? <Component {...props} />
                : <Redirect to={{pathname: '/guest-home', state: {from: props.location}}}/>}
        />
    )
}

export default HomeRoute;
