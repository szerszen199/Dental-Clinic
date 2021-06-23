import React, {Fragment, Suspense} from "react";
import axios from "axios";
import {withTranslation} from "react-i18next";
import errorAlertsWithRedirect from "../Alerts/ErrorAlerts/ErrorAlertsWithRedirect";
import successAlertsWithRedirect from "../Alerts/SuccessAlerts/SuccessAlertsWithRedirect";
import Rating from '@material-ui/lab/Rating';
import {Button} from "semantic-ui-react";

class RateAppointmentWithoutTr extends React.Component {

    constructor(props) {
        super(props);
        this.token = this.props.token;
        this.id = this.props.id;
        this.state = {}
        this.t = this.props;
        this.rate = 5;

    }


    componentDidMount() {

    }

    sendRate() {
        let self = this;
        const {t} = this.props;
        axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/rate/" + self.token + "/" + self.id + "/" + self.rate, {}).then(response => {
            self.setState({
                message: response.data.message
            })
            successAlertsWithRedirect(t(response.data.message), response.status.toString(), "#/home");
        }).catch(reason => {
            errorAlertsWithRedirect(t(reason.response.data.message), reason.response.status.toString(), "#/home");

        })

    }

    setRateValue(value) {
        this.rate = value;
    }


    render() {
        return <Fragment>
            <div>
                <Rating onChange={(event, newValue) => {
                    this.setRateValue(newValue);
                }} defaultValue={this.rate} precision={0.5}/>
            </div>
            <Button onClick={()=>{this.sendRate()}} >Submit</Button>
        </Fragment>
    }

}

const RateAppointmentWithTr = withTranslation()(RateAppointmentWithoutTr);

export default function RateAppointment(props) {
    return (
        <Suspense fallback="loading">
            <RateAppointmentWithTr token={props.match.params.token} id={props.match.params.id}/>
        </Suspense>
    );
}