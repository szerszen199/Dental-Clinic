import axios from "axios";
import successAlertsWithRedirect from "../Alerts/SuccessAlerts/SuccessAlertsWithRedirect";
import errorAlertsWithRedirect from "../Alerts/ErrorAlerts/ErrorAlertsWithRedirect";
import React, {Fragment} from "react";
import {withTranslation} from "react-i18next";

class PasswordChangeConfirm extends React.Component {
    constructor(props) {
        super(props);
        this.token = this.props.match.params.token;
        this.state = {}
        this.t = this.props.t;

    }

    componentDidMount() {
        const {t} = this.props;
        axios.put(process.env.REACT_APP_BACKEND_URL + "account/reset", {
            confirmToken: this.token
        }).then(response => {
            this.setState({
                message: response.data.message
            })
            successAlertsWithRedirect(this.t(response.data.message), response.status.toString(), "/home");
        }).catch(reason => {
            errorAlertsWithRedirect(t(reason.response.data.message), reason.response.status.toString(), "/home");

        })
    }

    render() {
        return <Fragment>
            <div>
            </div>
        </Fragment>
    }

}

export default withTranslation()(PasswordChangeConfirm);